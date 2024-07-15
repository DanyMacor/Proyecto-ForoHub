package com.forohub.ForoHub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConfirmarRequest {
    private String token;

    @JsonProperty("usuario")
    private DatosUsuarioDTO datosUsuarioDTO;
}
