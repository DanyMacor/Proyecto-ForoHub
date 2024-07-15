package com.forohub.ForoHub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatosUsuarioDTO {
    private Integer id;
    private String nombre;
    private String email;
    private String password;
    private Role role;
    private String filePerfil;
}
