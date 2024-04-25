package org.example.mscurso.feign;

import org.example.mscurso.dto.ProfesoresDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name= "ms-curso-service", path = "/profesores")
public interface ProfesoresFeign {
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProfesoresDto> listById(@PathVariable(required = true) Integer id);
}
