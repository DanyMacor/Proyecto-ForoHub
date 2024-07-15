package com.forohub.ForoHub.security;

import com.forohub.ForoHub.modelo.Usuario;
import com.forohub.ForoHub.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {
    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository
                .findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(email));
        return User
                .withUsername(usuario.getEmail())
                .password(usuario.getPassword())
                .roles(usuario.getRole().name())
                .build();
    }
}
