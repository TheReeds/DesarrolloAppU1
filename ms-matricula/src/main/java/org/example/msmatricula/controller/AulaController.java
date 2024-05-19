package org.example.msmatricula.controller;

import org.example.msmatricula.Dto.AulaDto;
import org.example.msmatricula.entity.Aula;
import org.example.msmatricula.service.AulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aulas")
public class AulaController {

    @Autowired
    private AulaService aulaService;

    @PostMapping
    public ResponseEntity<Aula> createAula(@RequestBody AulaDto aulaDto) {
        Aula aula = aulaService.createAula(aulaDto);
        return ResponseEntity.ok(aula);
    }

    @GetMapping
    public ResponseEntity<List<Aula>> getAllAulas() {
        List<Aula> aulas = aulaService.getAllAulas();
        return ResponseEntity.ok(aulas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aula> getAulaById(@PathVariable Integer id) {
        Aula aula = aulaService.getAulaById(id);
        if (aula != null) {
            return ResponseEntity.ok(aula);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}