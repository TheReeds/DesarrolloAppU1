package org.example.mscurso.feign;

import org.example.mscurso.dto.AlumnoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name= "ms-profesores-service", path = "/Alumno")
public interface AlumnoFeign {
    @GetMapping("/{id}")
    public ResponseEntity<AlumnoDto> listById(@PathVariable(required = true) Integer id);
}