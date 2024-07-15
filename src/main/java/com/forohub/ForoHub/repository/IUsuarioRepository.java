package com.forohub.ForoHub.repository;

import com.forohub.ForoHub.modelo.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Integer id);
    Optional<Usuario> findByEmail(String email);
}
