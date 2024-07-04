package org.example.msmatricula.controller;

import org.example.msmatricula.Dto.SesionDto;
import org.example.msmatricula.service.SesionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sesiones")
public class SesionController {

    @Autowired
    private SesionService sesionService;

    @PostMapping
    public ResponseEntity<SesionDto> createSesion(@RequestBody SesionDto sesionDto) {
        SesionDto newSesion = sesionService.createSesion(sesionDto);
        return ResponseEntity.ok(newSesion);
    }

    @GetMapping("/aula/{aulaId}")
    public ResponseEntity<List<SesionDto>> getAllSesionesByAulaId(@PathVariable Integer aulaId) {
        List<SesionDto> sesiones = sesionService.getAllSesionesByAulaId(aulaId);
        return ResponseEntity.ok(sesiones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SesionDto> getSesionById(@PathVariable Integer id) {
        SesionDto sesion = sesionService.getSesionById(id);
        if (sesion != null) {
            return ResponseEntity.ok(sesion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSesionById(@PathVariable Integer id) {
        sesionService.deleteSesionById(id);
        return ResponseEntity.noContent().build();
    }
}