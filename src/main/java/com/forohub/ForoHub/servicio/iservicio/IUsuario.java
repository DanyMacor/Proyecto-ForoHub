package com.forohub.ForoHub.servicio.iservicio;

import com.forohub.ForoHub.dto.DatosUsuarioDTO;
import com.forohub.ForoHub.dto.UsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.awt.print.Pageable;
import java.util.List;

public interface IUsuario {
    List<UsuarioDTO> findAll();
    Page<UsuarioDTO> paginate(Pageable pageable);
    UsuarioDTO findById(Integer id);
    UsuarioDTO save(DatosUsuarioDTO datosUsuarioDTO);
    UsuarioDTO update(Integer id, DatosUsuarioDTO datosUsuarioDTO);
    //    Boolean delete(Integer id);
    ResponseEntity eliminarUsuario(Integer id);
}
