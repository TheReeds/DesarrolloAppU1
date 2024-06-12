package com.example.crudprofesores.feign;

import com.example.crudprofesores.dto.CursoDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="ms-curso-service",path = "/cursos")
public interface CursoFeign {
        @GetMapping("/{id}")
        @CircuitBreaker(name = "cursoListarPorIdCB", fallbackMethod = "fallbackCursoPorId")
        ResponseEntity<CursoDto> buscarPorId(@PathVariable(required = true) Integer id);
        default ResponseEntity<CursoDto> fallbackCursoPorId(Integer id, Exception e) {
                return ResponseEntity.ok(new CursoDto());
        }

}
