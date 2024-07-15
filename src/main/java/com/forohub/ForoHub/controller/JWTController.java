package com.forohub.ForoHub.controller;

import com.forohub.ForoHub.dto.ConfirmarRequest;
import com.forohub.ForoHub.dto.DatosUsuarioDTO;
import com.forohub.ForoHub.dto.SolicitarConfirmacion;
import com.forohub.ForoHub.modelo.Usuario;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
@AllArgsConstructor
public class JWTController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final ProveedorDeToken proveedorDeToken;
    private final iUsuarioRepository usuarioRepository;

    @PostMapping(value = "/autenticacion")
    public ResponseEntity<?> autenticacion(@RequestBody @Valid SolicitarConfirmacion solicitudAutenticacion){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                solicitudAutenticacion.getEmail(),
                solicitudAutenticacion.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = proveedorDeToken.crearToken(authentication);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer "+ token);

        Usuario usuario = usuarioRepository
                .findByEmail(solicitudAutenticacion.getEmail())
                .orElseThrow(ResourceNotFoundException::new);

        ConfirmarRequest confirmarRequest = new ConfirmarRequest(token, new DatosUsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.getRole(),
                usuario.getFilePerfil()
        ));

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(confirmarRequest);

    }
}
