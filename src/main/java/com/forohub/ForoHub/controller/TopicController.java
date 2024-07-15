package com.forohub.ForoHub.controller;

import com.forohub.ForoHub.dto.TopicActualDTO;
import com.forohub.ForoHub.dto.TopicDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class TopicController {
    @Autowired
    private iTemaService temaService;

    @GetMapping(value = "/admin/topicos/list")
    private List<TopicDTO> findAll(){
        return temaService.findAll();
    }

    @GetMapping(value = "/admin/topicos")
    private Page<TopicDTO> paginate(@PageableDefault(sort = "createdAt",direction = Sort.Direction.ASC ,size = 10 ) Pageable pageable){
        return temaService.paginate(pageable);
    }

    @GetMapping(value = "/topicos/{id}")
    private TopicDTO findById(@PathVariable(value = "id") Integer id){
        return temaService.findById(id);
    }

    @PostMapping(value = "/topicos")
    private TopicDTO save(@RequestBody @Valid TopicDTO temaDto){
        return temaService.save(temaDto);
    }

    @PutMapping(value = "/topicos/{id}")
    private TopicDTO update(@PathVariable(value = "id") Integer id, @RequestBody @Valid TopicActualDTO temaActualizarDTO){
        return temaService.update(id,temaActualizarDTO);
    }

    @DeleteMapping(value = "/topicos/{id}")
    private Boolean delete(@PathVariable(value = "id") Integer id){
        return temaService.delete(id);
    }
}
