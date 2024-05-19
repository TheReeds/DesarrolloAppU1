package org.example.msmatricula.feign;

import org.example.msmatricula.Dto.AlumnoDto;
import org.example.msmatricula.Dto.CursoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name= "ms-alumno-service", path = "/alumno")
public interface AlumnoFeign {
    @GetMapping(value = "/{id}")
    ResponseEntity<AlumnoDto> listById(@PathVariable(required = true) Integer id);
    @GetMapping()
    List<AlumnoDto> getAllAlumnos();
}
