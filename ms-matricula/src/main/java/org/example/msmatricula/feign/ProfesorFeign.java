package org.example.msmatricula.feign;

import org.example.msmatricula.Dto.ProfesorDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name= "ms-profesores-service")
public interface ProfesorFeign {
    @GetMapping(value = "/profesores/{id}")
    ResponseEntity<ProfesorDto> listById(@PathVariable(required = true) Integer id);
}