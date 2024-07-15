package com.forohub.ForoHub.servicio;

import com.forohub.ForoHub.dto.RequestDTO;
import com.forohub.ForoHub.exception.MalaRespuesta;
import com.forohub.ForoHub.exception.NoEncuetraRespueta;
import com.forohub.ForoHub.modelo.Request;
import com.forohub.ForoHub.modelo.Topic;
import com.forohub.ForoHub.modelo.Usuario;
import com.forohub.ForoHub.repository.IRequestRepository;
import com.forohub.ForoHub.repository.ITopicRepository;
import com.forohub.ForoHub.repository.IUsuarioRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RequestServicio {
    private final IRequestRepository requestRepository;
    private final IUsuarioRepository usuarioRepository;
    private final ITopicRepository topicRepository;

    @Override
    public List<RequestDTO> findAll() {
        List<Request> respuesta = requestRepository.findAll();
        return respuesta.stream()
                .map(this::manejoRespuesta)
                .collect(Collectors.toList());
    }

    @Override
    public Page<RequestDTO> paginate(Pageable pageable) {
        Page<Request> respuesta = requestRepository.findAll(pageable);
        return respuesta.map(this::manejoRespuesta);
    }

    @Override
    public RequestDTO findById(Integer id) {
        Request request = requestRepository.findById(id)
                .orElseThrow(()->new NoEncuetraRespueta("ERROR ID: Respuesta no encontrada!"));
        return manejoRespuesta(request);
    }

    @Override
    public RequestDTO save(RequestDTO respuestaDTO) {


        if (respuestaDTO.getUsuarioId() == null || respuestaDTO.getTemaId() == null) {
            throw new IllegalArgumentException("Usuario ID and Tema ID must not be null");
        }

        // Verificar si ya existe una respuesta con el mismo mensaje para el mismo tema
        boolean existeRespuesta = requestRepository.existsByMensajeRespuestaAndTemaIdId(respuestaDTO.getMensajeRespuesta(), respuestaDTO.getTemaId());
        if (existeRespuesta) {
            throw new MalaRespuesta("La respuesta ya existe para este tema");
        }

        Usuario usuario =  usuarioRepository.findById(respuestaDTO.getUsuarioId())
                .orElseThrow(() -> new NoEncuetraRespueta("ERROR ID: usuario id no encontrado!"));

        Topic topic = topicRepository.findById(respuestaDTO.getTemaId())
                .orElseThrow(() -> new NoEncuetraRespueta("ERROR ID: tema id no encontrado!"));

        Request respuesta = new Request();
        respuesta.setMensajeRespuesta(respuestaDTO.getMensajeRespuesta());
        respuesta.setTopicId(topic);
        respuesta.setUsuarioId(usuario);
        respuesta.setActivo(Boolean.TRUE);
        respuesta.setCreatedAt(LocalDateTime.now());

        Request savedRespuesta = requestRepository.save(respuesta);
        return manejoRespuesta(savedRespuesta);
    }


    @Override
    public RequestDTO update(Integer id, RequestDTO respuestaDTO) {
        Request respuesta = requestRepository.findById(id)
                .orElseThrow(() -> new NoEncuetraRespueta("Respuesta no encontrado con ID: " + id));

        boolean existeRespuesta = requestRepository.existsByMensajeRespuestaAndTemaIdIdAndIdNot(respuestaDTO.getMensajeRespuesta(), respuestaDTO.getTemaId(), id);

        if (existeRespuesta) {
            throw new MalaRespuesta("La respuesta ya existe para este tema");
        }

        Usuario usuario =  usuarioRepository.findById(respuestaDTO.getUsuarioId())
                .orElseThrow(() -> new NoEncuetraRespueta("ERROR ID: usuario id no encontrado!"));

        Topic tema = topicRepository.findById(respuestaDTO.getTemaId())
                .orElseThrow(() -> new NoEncuetraRespueta("ERROR ID: tema id no encontrado!"));

        respuesta.setMensajeRespuesta(respuestaDTO.getMensajeRespuesta());
        respuesta.setUpdatedAt(LocalDateTime.now());

        Request savedRespuesta = requestRepository.save(respuesta);
        return manejoRespuesta(savedRespuesta);
    }

    @Override
    public Boolean delete(Integer id) {
        requestRepository.deleteById(id);
        return true;
    }

    public RequestDTO manejoRespuesta(Request respuesta){
        RequestDTO respuestaDTO = new ModelMapper().map(respuesta, RequestDTO.class);
        return  respuestaDTO;
    }

}
