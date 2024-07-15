package com.forohub.ForoHub.servicio.iservicio;

import com.forohub.ForoHub.dto.RequestDTO;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface IRespuesta {
    List<RequestDTO> findAll();
    Page<RequestDTO> paginate(Pageable pageable);
    RequestDTO findById(Integer id);
    RequestDTO save(RequestDTO requestDTO);
    RequestDTO update(Integer id, RequestDTO requestDTO);
    Boolean delete(Integer id);
}
