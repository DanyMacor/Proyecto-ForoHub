package com.forohub.ForoHub.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TopicDTO {
    private Integer id;

    @NotNull
    @Size(min = 1, message = "Escribe por lo menos 1 caracteres")
    @Size(max = 200, message = "Escribe maximo 3 caracteres")
    private String titulo;

    @NotNull
    @Size(min = 1, message = "Escribe por lo menos 1 caracter para el mensaje")
    @Size(max = 500, message = "Escribe maximo 500 caracteres para el mensaje")
    private String mensaje;

    @NotNull
    private Genero genero;

    @NotNull
    private Integer usuarioId;

    private String usuarioNombre;

    private String filePerfil;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean activo;

    private List<RequestTopic> respuestas;

}
