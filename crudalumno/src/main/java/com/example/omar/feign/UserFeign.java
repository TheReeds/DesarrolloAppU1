package com.example.omar.feign;

import com.example.omar.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-userjwt-service", path = "/usuarios")
public interface UserFeign {
    @GetMapping(value = "/{id}")
    ResponseEntity<UserDto> listById(@PathVariable(required = true) Integer id);
}
