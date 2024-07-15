package com.forohub.ForoHub.dto;

import lombok.Data;

import javax.management.relation.Role;
import java.time.LocalDateTime;

@Data
public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String email;
    private String password;
    private Role role;
    private String filePerfil;
    private Boolean activo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
