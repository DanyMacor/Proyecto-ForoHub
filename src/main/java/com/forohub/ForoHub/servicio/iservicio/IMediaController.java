package com.forohub.ForoHub.servicio.iservicio;

import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface IMediaController {
    Map<String, String> upload(MultipartFile multipartFile);
    ResponseEntity<Resource> getResource(String nombreArchivo) throws IOException;
}
