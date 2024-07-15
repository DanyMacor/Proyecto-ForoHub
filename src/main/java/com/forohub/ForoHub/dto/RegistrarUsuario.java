package com.forohub.ForoHub.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import javax.management.relation.Role;

@Data
public class RegistrarUsuario {
    @NotNull
    @Size(min = 1, message = "Por lo 1 caracter")
    @Size(max = 20, message = "Maximo 20 caracteres")
    private String nombre;

    @NotNull
    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = "[a-z0-9-]+")
    @Size(min = 8, message = "Por lo menos 8 caracteres para la contraseña")
    private String password;

    private Role role;

    @NotEmpty
    private String filePerfil;
}
