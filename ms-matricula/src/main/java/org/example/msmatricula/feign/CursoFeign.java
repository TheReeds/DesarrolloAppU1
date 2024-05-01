package org.example.msmatricula.feign;

import org.example.msmatricula.Dto.CursoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-curso-service", path = "/curso")
public interface CursoFeign {
@GetMapping(value = "/{id}")
ResponseEntity<CursoDto> listById(@PathVariable(required = true) Integer id);
}
