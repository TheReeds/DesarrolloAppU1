package com.example.msuserjwt.feign;

import com.example.msuserjwt.dto.AlumnoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name= "ms-alumno-service", path = "/alumno")
public interface AlumnoFeign {
    @GetMapping(value = "/{id}")
    ResponseEntity<AlumnoDto> listById(@PathVariable(required = true) Integer id);
    @PutMapping(value = "/{id}")
    ResponseEntity<AlumnoDto> updateAlumno(@PathVariable(required = true) Integer id, @RequestBody AlumnoDto alumnoDto);
}