package com.example.omar.feign;

import com.example.omar.dto.AulaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name= "ms-matricula-service", path = "/aulas")
public interface AulaFeign {
    @GetMapping("/{id}")
    AulaDto getAulaById(@PathVariable("id") Integer id);
}
