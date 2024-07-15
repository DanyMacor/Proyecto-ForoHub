package com.forohub.ForoHub.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface IRequestRepository {
    boolean existsByMensajeRespuestaAndTemaIdId(String mensajeRespuesta, Integer temaId);

    boolean existsByMensajeRespuestaAndTemaIdIdAndIdNot(String mensajeRespuesta, Integer temaId, Integer id);
}
