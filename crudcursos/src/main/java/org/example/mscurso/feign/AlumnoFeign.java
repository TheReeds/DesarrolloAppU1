package org.example.mscurso.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.example.mscurso.dto.AlumnoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-alumno-service")
public interface AlumnoFeign {
    @GetMapping("/alumnos/{id}")
    @CircuitBreaker(name = "alumnoListarPorIdCB", fallbackMethod = "fallbackAlumnoPorId")
    AlumnoDto obtenerAlumnoPorId(@PathVariable("id") Integer id);
    default ResponseEntity<AlumnoDto> fallbackAlumnoPorId(Integer id, Exception e) {
        return ResponseEntity.ok(new AlumnoDto());
    }
}