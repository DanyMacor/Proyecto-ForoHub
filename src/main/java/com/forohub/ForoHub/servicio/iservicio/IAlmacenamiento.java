package com.forohub.ForoHub.servicio.iservicio;

import jakarta.annotation.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface IAlmacenamiento {
    void iniciar();
    String almacenar(MultipartFile archivo);
    Path cargar(String nombreArchivo);
    Resource cargarRecurso(String nombreArchivo);
    void eliminarRecurso(String nombreArchivo);
}
