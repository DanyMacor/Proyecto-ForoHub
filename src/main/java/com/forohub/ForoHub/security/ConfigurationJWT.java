package com.forohub.ForoHub.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class ConfigurationJWT extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final ProvedorToken proveedorToken;

    public ConfiguracionJWT(ProveedorToken proveedorToken){
        this.proveedorToken = proveedorToken;
    }

    public void configure(HttpSecurity http){
        FiltroJWT filtroPersonalizado = new FiltroJWT(proveedorToken);
        http.addFilterBefore(filtroPersonalizado, UsernamePasswordAuthenticationFilter.class);
    }
}
