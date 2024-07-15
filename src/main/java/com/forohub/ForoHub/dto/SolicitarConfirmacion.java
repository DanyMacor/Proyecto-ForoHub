package com.forohub.ForoHub.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SolicitarConfirmacion {
    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotEmpty
    @NotBlank
    @Pattern(regexp = "[a-z0-9-]+")
    @Size(min = 5, message = "Escribe al menos 5 caracteres")
    private String password;
}
