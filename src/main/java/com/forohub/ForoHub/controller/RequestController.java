package com.forohub.ForoHub.controller;

import com.forohub.ForoHub.dto.RequestDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping(value = "/api/request")
public class RequestController {
    @Autowired
    private iRespuestaService respuestaService;

    @Autowired
    public RespuestaController(RespuestaService respuestaService) {
        this.respuestaService = respuestaService;
    }

    @GetMapping(value = "/list")
    List<RequestDTO> findAll(){
        return respuestaService.findAll();
    }

    @GetMapping("/{id}")
    public RequestDTO findById(@PathVariable(value = "id") Integer id){
        return respuestaService.findById(id);
    }

    @GetMapping
    Page<RequestDTO paginate(@PageableDefault(sort ="createdAt",direction = Sort.Direction.DESC, size = 10) Pageable pageable){
        return respuestaService.paginate(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RequestDTO save(@RequestBody @Valid RequestDTO respuestaDTO){
        return respuestaService.save(respuestaDTO);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(value = "/{id}")
    RequestDTO update(@PathVariable(value = "id") Integer id, @RequestBody @Valid RequestDTO respuestaDTO){
        return respuestaService.update(id,respuestaDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    Boolean delete(@PathVariable(value = "id") Integer id){
        return respuestaService.delete(id);
    }

}
