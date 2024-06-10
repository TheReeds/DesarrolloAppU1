package com.example.msuserjwt.feign;

import com.example.msuserjwt.dto.ProfesoresDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name= "ms-profesores-service", path = "/profesores")
public interface ProfesorFeign {
    @GetMapping(value = "/{id}")
    ResponseEntity<ProfesoresDto> listById(@PathVariable(required = true) Integer id);
    @PutMapping(value = "/{id}")
    ResponseEntity<ProfesoresDto> updateProfesor(@PathVariable Integer id, @RequestBody ProfesoresDto profesoresDto);
}
