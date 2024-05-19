package org.example.msmatricula.controller;

import org.example.msmatricula.Dto.AlumnoDto;
import org.example.msmatricula.service.SalonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/salones")
public class SalonController {
    @Autowired
    private SalonService salonService;

    @GetMapping("/{id}")
    public List<AlumnoDto> obtenerEstudiantesPorCurso(@PathVariable int id) {
        return salonService.obtenerEstudiantesPorCurso(id);
    }
}
