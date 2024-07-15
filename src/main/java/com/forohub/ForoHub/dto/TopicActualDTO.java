package com.forohub.ForoHub.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TopicActualDTO {
    @NotNull
    @Size(min = 3, message = "Minimo Titulo con 3 caracteres!")
    @Size(max = 100, message = "Máximo Titulo son 100 caracteres!")
    private String titulo;

    @NotNull
    @Size(min = 10, message = "Caracteres minimos para el mensaje: 3")
    @Size(max = 900, message = "Caracteres maximos para el mensaje: 900")
    private String mensaje;

    @NotNull
    private Genero genero;
}
