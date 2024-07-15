package com.forohub.ForoHub.servicio.iservicio;

import com.forohub.ForoHub.dto.Genero;
import com.forohub.ForoHub.dto.TopicDTO;

import java.time.LocalDate;
import java.util.List;

public interface IInicio {
    List<TopicDTO> findByGenero(Genero genero);
    List<TopicDTO> getTemasByDate(LocalDate localDate);
}
