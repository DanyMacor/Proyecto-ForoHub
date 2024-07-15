package com.forohub.ForoHub.servicio;

import com.forohub.ForoHub.dto.RequestDTO;
import com.forohub.ForoHub.dto.RequestTopic;
import com.forohub.ForoHub.dto.TopicActualDTO;
import com.forohub.ForoHub.dto.TopicDTO;
import com.forohub.ForoHub.exception.MalaRespuesta;
import com.forohub.ForoHub.exception.NoEncuetraRespueta;
import com.forohub.ForoHub.modelo.Request;
import com.forohub.ForoHub.modelo.Topic;
import com.forohub.ForoHub.modelo.Usuario;
import com.forohub.ForoHub.repository.ITopicRepository;
import com.forohub.ForoHub.repository.IUsuarioRepository;
import com.forohub.ForoHub.servicio.iservicio.ITopic;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicServicio implements ITopic {
    @Autowired
    private ITopicRepository topicRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public List<TopicDTO> findAll() {
        List<Topic>temas = topicRepository.findAll();
        return temas.stream()
                .map(tema -> manejoRespuestaCliente(tema, true))
                .collect(Collectors.toList());
    }

    @Override
    public Page<TopicDTO> paginate(Pageable pageable) {
        Page<Topic> temas = topicRepository.findAll(pageable);
        return temas.map(tema -> manejoRespuestaCliente(tema, true));
    }

    public TopicDTO findById(Integer id) {
        Topic tema = topicRepository.findById(id)
                .orElseThrow(() -> new NoEncuetraRespueta("Tema no encontrado con ID: " + id));
        return manejoRespuestaCliente(tema, true);
    }

    public TopicDTO save(TopicDTO temaDto) {
        boolean tituloExiste = topicRepository.existsByTitulo(temaDto.getTitulo());
        boolean mensajeExiste = topicRepository.existsByMensaje(temaDto.getMensaje());

        if (tituloExiste ) {
            throw new MalaRespuesta("El titulo ya existe!");
        }
        if (mensajeExiste) {
            throw new MalaRespuesta("El mensaje ya exisite!");
        }

        Usuario usuario = usuarioRepository.findById(temaDto.getUsuarioId())
                .orElseThrow(() -> new NoEncuetraRespueta("ERROR ID: usuario id no encontrado!"));


        Topic topic = new ModelMapper().map(temaDto, Topic.class);
        try {
            topic.setCreatedAt(LocalDateTime.now());
            topic.setUsuarioId(usuario);
            topic.setActivo(Boolean.TRUE);
            topic = topicRepository.save(topic);
        }catch (DataAccessException e){
            throw new MalaRespuesta("ERROR CREACION TEMA: Falla no es posible realizar el proceso!", e);
        }
        return manejoRespuestaCliente(topic, false);
    }

    public TopicDTO update(Integer id, TopicActualDTO temaActualizarDTO) {
        Topic tema = topicRepository.findById(id)
                .orElseThrow(() -> new NoEncuetraRespueta("Tema no encontrado con ID: " + id));

        boolean tituloExiste = topicRepository.existsByTituloAndIdNot(temaActualizarDTO.getTitulo(), id);
        boolean mensajeExiste = topicRepository.existsByMensajeAndIdNot(temaActualizarDTO.getMensaje(), id);

        if (tituloExiste ) {
            throw new MalaRespuesta("El titulo ya existe!");
        }
        if (mensajeExiste) {
            throw new MalaRespuesta("El mensaje ya exisite!");
        }

        try{
            if (tema != null){
                tema.setTitulo(temaActualizarDTO.getTitulo());
                tema.setMensaje(temaActualizarDTO.getMensaje());
                tema.setGenero(temaActualizarDTO.getGenero());
                tema.setUpdatedAt(LocalDateTime.now());
                tema = topicRepository.save(tema);
            }else {
                throw new MalaRespuesta("ERROR ACTUALIZAR: No se pudo actualizar tema!");
            }
        }catch (DataAccessException e){
            throw new MalaRespuesta("ERROR ACTUALIZACION: Falla no es posible realizar el proceso!" , e);
        }
        return manejoRespuestaCliente(tema, false);
    }



    @Override
    public Boolean delete(Integer id) {
        topicRepository.deleteById(id);
        return true;
    }

    private TopicDTO manejoRespuestaCliente(Tema tema, boolean incluirRespuestas) {
        TopicDTO topicDTO = new ModelMapper().map(tema,TopicDTO.class);

        topicDTO.setFilePerfil(tema.getUsuarioId().getFilePerfil());

        // Mapeo de respuestas
        if (incluirRespuestas && tema.getRequest() != null && !tema.getRequest().isEmpty()) {
            List<RequestDTO> respuestasDto = tema.getRequest().stream()
                    .map(this::manejoRespuesta) // Método para mapear Respuesta a RespuestaDTO
                    .collect(Collectors.toList());
            topicDTO.setRespuestas(respuestasDto);
        }
        return topicDTO;
    }

    // Método para mapear Respuesta a RespuestaDTO
    private RequestTopic manejoRespuesta(Request respuesta) {
        RequestTopic respuestaDto = new ModelMapper().map(respuesta,RequestTopic.class);

        respuestaDto.setFilePerfilRespuesta(respuesta.getUsuarioId().getFilePerfil());
        return respuestaDto;
    }
}
