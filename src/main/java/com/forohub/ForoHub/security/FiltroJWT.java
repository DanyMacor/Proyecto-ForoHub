package com.forohub.ForoHub.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class FiltroJWT extends GenericFilterBean {
    private final ProveedorDeToken proveedorDeToken;
    public FiltroJWT(ProveedorDeToken proveedorDeToken){
        this.proveedorDeToken = proveedorDeToken;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String token = resolveToken(httpServletRequest);

        if (StringUtils.hasText(token) && proveedorDeToken.validacionToken(token)){
            Authentication autenticacion = proveedorDeToken.obtenerAuthenticacion(token);
            SecurityContextHolder.getContext().setAuthentication(autenticacion);
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    private String resolveToken(HttpServletRequest request){
        String bearerToken  = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return  bearerToken.substring(7);
        }
        return null;
    }
}