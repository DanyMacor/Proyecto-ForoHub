package com.forohub.ForoHub.repository;

import com.forohub.ForoHub.dto.Genero;
import com.forohub.ForoHub.modelo.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface ITopicRepository {
    boolean existsByTitulo(String titulo);
    boolean existsByTituloAndIdNot(String titulo ,Integer id);
    boolean existsByMensaje(String mensaje);
    boolean existsByMensajeAndIdNot(String mensaje, Integer id);
    List<Topic> findByGenero(Genero genero);
    @Query("SELECT t FROM Tema t WHERE DATE(t.createdAt) = :date")
    List<Topic> findTemasByDate(@Param("date") LocalDate date);


    List<Topic> findTop9ByOrderByCreatedAtDesc();

    Page<Topic> findAll(Pageable pageable);
}
