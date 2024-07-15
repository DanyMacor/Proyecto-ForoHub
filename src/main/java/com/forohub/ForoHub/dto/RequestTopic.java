package com.forohub.ForoHub.dto;

import lombok.Data;

@Data
public class RequestTopic {
    private Integer id;
    private String mensajeRespuesta;
    private Integer usuarioId;
    private String usuarioNombre;
    private String fileDatosRespuesta;
}
