package com.forohub.ForoHub.controller;

import com.forohub.ForoHub.dto.DatosUsuarioDTO;
import com.forohub.ForoHub.dto.RegistrarUsuario;
import com.forohub.ForoHub.dto.Role;
import com.forohub.ForoHub.modelo.Usuario;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api")
@AllArgsConstructor
public class CuentaController {
    private final iUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/registro")
    public DatosUsuarioDTO registro(@RequestBody @Validated RegistrarUsuario usuarioRegistroDTO){
        boolean emailExists = usuarioRepository.existsByEmail(usuarioRegistroDTO.getEmail());
        if (emailExists){
            throw new BadRequestExcepton("ERROR EMAIL DUPLICADO: El email ya existe!");
        }
        //Usuario usuario = new Usuario();
        Usuario usuario = new ModelMapper().map(usuarioRegistroDTO,Usuario.class);
        usuario.setPassword(passwordEncoder.encode(usuarioRegistroDTO.getPassword()));
        usuario.setActivo(Boolean.TRUE);
        usuario.setRole(Role.USER);
        usuario.setCreatedAt(LocalDateTime.now());
        usuarioRepository.save(usuario);

        return new ModelMapper().map(usuario, DatosUsuarioDTO.class);
    }
}
