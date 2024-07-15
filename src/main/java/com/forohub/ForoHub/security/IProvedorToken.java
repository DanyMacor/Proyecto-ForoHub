package com.forohub.ForoHub.security;

import org.springframework.security.core.Authentication;

public interface IProvedorToken {
    String crearToken(Authentication authentication);
    Authentication obtenerAuthenticacion(String token);

    boolean validacionToken(String token);
}
