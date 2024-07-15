package com.forohub.ForoHub.controller;

import com.forohub.ForoHub.dto.Genero;
import com.forohub.ForoHub.dto.TopicDTO;
import com.forohub.ForoHub.modelo.Topic;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/inicio")
@AllArgsConstructor
public class InicioController {
    private final iTemaRepository temaRepository;

    @Override
    @GetMapping("/categoria/{genero}")
    public List<TopicDTO> findByGenero(@PathVariable(value = "genero") Genero genero) {

        List<Topic> temas = temaRepository.findByGenero(genero);

        if (temas == null || temas.isEmpty()){
            throw new ResourceNotFoundException("Genero no encontrado!");
        }else {
            return temas.stream()
                    .map(this::manejorRespuestaClienteCorta)// Transforma cada Tema a TemaDto reducido
                    .collect(Collectors.toList());
        }
    }

    @GetMapping("/fecha/{date}")
    public List<TopicDTO> getTemasByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Topic> temasPorFecha = temaRepository.findTemasByDate(date);

        if (temasPorFecha == null || temasPorFecha.isEmpty()){
            throw new ResourceNotFoundException("No hay Temas en la fecha ingresada!");
        }else {
            return temasPorFecha.stream()
                    .map(this::manejorRespuestaClienteCorta)
                    .collect(Collectors.toList());
        }
    }

    @GetMapping("/last-temas")
    List<TopicDTO> getLastTemas(){
        List<Topic> temas =temaRepository.findTop9ByOrderByCreatedAtDesc();

        if (temas == null || temas.isEmpty()){
            throw new ResourceNotFoundException("Temas no encontrados!");
        }else {
            return temas.stream()
                    .map(this::manejorRespuestaClienteCorta)
                    .collect(Collectors.toList());
        }
    }

    @GetMapping("/topicos")
    private Page<TopicDTO> paginate(@PageableDefault(sort = "createdAt",direction = Sort.Direction.ASC ,size = 10 ) Pageable pageable){
        Page<Topic> temas =temaRepository.findAll(pageable);
        return temas.map(this::manejorRespuestaClienteCorta);

    }

    private TopicDTO manejorRespuestaClienteCorta(Topic topic) {
        TopicDTO temaDto = new ModelMapper().map(topic, TopicDTO.class);
        temaDto.setFilePerfil(topic.getUsuarioId().getFilePerfil());
        return temaDto;
    }
}
