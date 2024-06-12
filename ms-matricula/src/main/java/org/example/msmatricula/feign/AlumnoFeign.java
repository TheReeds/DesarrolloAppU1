package org.example.msmatricula.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.example.msmatricula.Dto.AlumnoDto;
import org.example.msmatricula.Dto.CursoDto;
import org.example.msmatricula.Dto.GradoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name= "ms-alumno-service")
public interface AlumnoFeign {
    @GetMapping(value = "/alumno/{id}")
    @CircuitBreaker(name = "alumnoListarPorIdCB", fallbackMethod = "fallbackAlumnoPorId")
    ResponseEntity<AlumnoDto> listById(@PathVariable(required = true) Integer id);
    default ResponseEntity<AlumnoDto> fallbackAlumnoPorId(Integer id, Exception e) {
        return ResponseEntity.ok(new AlumnoDto());
    }
    @GetMapping("/alumno")
    List<AlumnoDto> getAllAlumnos();
    @PutMapping("/alumno/{id}/estado")
    ResponseEntity<AlumnoDto> actualizarEstado(@PathVariable("id") Integer id, @RequestParam boolean estado);
    @GetMapping(value = "/grado/{id}")
    ResponseEntity<GradoDto> listGradoById(@PathVariable(required = true) Integer id);
}
