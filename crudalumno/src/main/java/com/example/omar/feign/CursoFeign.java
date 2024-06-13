package com.example.omar.feign;

import com.example.omar.dto.CursoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name= "ms-curso-service", path = "/cursos")
public interface CursoFeign {

    @GetMapping("/{id}")
    ResponseEntity<CursoDto> getCursoById(@PathVariable("id") Integer id);
}