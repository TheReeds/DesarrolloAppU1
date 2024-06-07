package org.example.mscurso.feign;

import org.example.mscurso.dto.AlumnoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-alumno-service")
public interface AlumnoFeign {
    @GetMapping("/alumnos/{id}")
    AlumnoDto obtenerAlumnoPorId(@PathVariable("id") Integer id);
}