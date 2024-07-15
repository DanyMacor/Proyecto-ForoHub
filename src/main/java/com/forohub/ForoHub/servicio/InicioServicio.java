package com.forohub.ForoHub.servicio;

import com.forohub.ForoHub.dto.Genero;
import com.forohub.ForoHub.dto.TopicDTO;

import java.time.LocalDate;
import java.util.List;

public class InicioServicio {
    @Override
    public List<TopicDTO> findByGenero(Genero genero) {
        return null;
    }

    @Override
    public List<TopicDTO> getTemasByDate(LocalDate localDate) {
        return null;
    }
}
