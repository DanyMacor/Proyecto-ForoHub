package com.forohub.ForoHub.servicio;

import com.forohub.ForoHub.exception.MalaRespuesta;
import com.forohub.ForoHub.exception.NoEncuetraRespueta;
import com.forohub.ForoHub.servicio.iservicio.IAlmacenamiento;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class AlmacenServicio implements IAlmacenamiento {
    @Value("${app.forohub.location}")
    private String almacenamientoLocal;

    @PostConstruct
    @Override
    public void iniciar() {
        try {
            Files.createDirectories(Paths.get(almacenamientoLocal));
        } catch (IOException e) {
            throw new MalaRespuesta("ERROR ALMACENAMIENTO: Falla al inicializar ruta de almacenamiento!", e);
        }
    }

    @Override
    public String almacenar(MultipartFile archivo) {
        String nombreArchivoOriginal = archivo.getOriginalFilename();
        String nombreArchivo = UUID.randomUUID()+"."+ StringUtils.getFilenameExtension(nombreArchivoOriginal);

        if (archivo.isEmpty()){
            throw new NoEncuetraRespueta("ERROR VACIO: No es posible almacenar un archivo vacio!");
        }

        try {
            InputStream entrada = archivo.getInputStream();
            Files.copy(entrada, Paths.get(almacenamientoLocal).resolve(nombreArchivo), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new MalaRespuesta("ERROR:Falla al almacener el archivo ",e);
        }
        return nombreArchivo;
    }

    @Override
    public Path cargar(String nombreArchivo) {
        return Paths.get(almacenamientoLocal).resolve(nombreArchivo);
    }

    @Override
    public Resource cargarRecurso(String nombreArchivo) {
        try {
            Path archivo = cargar(nombreArchivo);
            Resource recurso = new UrlResource(archivo.toUri());

            if (recurso.exists() || recurso.isReadable()){
                return recurso;
            }else {
                throw new NoEncuetraRespueta("ERROR LECTURA: No es posible leer el archivo! " + nombreArchivo);
            }
        }catch (MalformedURLException e){
            throw new MalaRespuesta("ERROR: No se puede leer el archivo! "+nombreArchivo, e);
        }
    }

    @Override
    public void eliminarRecurso(String nombreArchivo) {
        Path archivo = cargar(nombreArchivo);
        try {
            FileSystemUtils.deleteRecursively(archivo);
        } catch (IOException e) {
            throw new MalaRespuesta("ERROR: No se puede eliminar el archivo! " + nombreArchivo);
        }
    }

}
