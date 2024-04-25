package com.example.crudprofesores.feign;

import com.example.crudprofesores.dto.CursoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-curso-service", path = "/cursos")
public interface CursoFeign {
    @GetMapping("{id}")
    public ResponseEntity<CursoDto> buscarPorId(@PathVariable(required = true) Integer id);
}
