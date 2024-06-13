package com.example.crudprofesores.feign;

import com.example.crudprofesores.dto.CursoDto;
import com.example.crudprofesores.dto.UserDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-userjwt-service", path = "/usuarios")
public interface UserFeign {
    @GetMapping(value = "/{id}")
    @CircuitBreaker(name = "userListarPorIdCB", fallbackMethod = "fallbackUserPorId")
    ResponseEntity<UserDto> listById(@PathVariable(required = true) Integer id);
    default ResponseEntity<UserDto> fallbackUserPorId(Integer id, Exception e) {
        return ResponseEntity.ok(new UserDto());
    }

}