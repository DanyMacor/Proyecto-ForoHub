package com.forohub.ForoHub.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestDTO {
    private Integer id;

    @NotNull
    @Size(min = 3, message = "Request por lo menos 3 caracteres")
    @Size(max = 150, message = "500 caracteres máximo")
    private String mensajeRequest;

    @NotNull
    private Integer topicId;

    @NotNull
    private Integer usuarioId;
    private Boolean activo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
