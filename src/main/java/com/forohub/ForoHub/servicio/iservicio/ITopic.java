package com.forohub.ForoHub.servicio.iservicio;

import com.forohub.ForoHub.dto.TopicActualDTO;
import com.forohub.ForoHub.dto.TopicDTO;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface ITopic {
    List<TopicDTO> findAll();
    Page<TopicDTO> paginate(Pageable pageable);
    TopicDTO findById(Integer id);
    TopicDTO save(TopicDTO topicDTO);
    TopicDTO update(Integer id, TopicActualDTO temaActualizarDTO);
    Boolean delete(Integer id);
}
