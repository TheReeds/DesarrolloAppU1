package org.example.msmatricula.controller;

import org.example.msmatricula.Dto.AulaConProfesorDto;
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
    public ResponseEntity<List<AulaDto>> getAllAulas() {
        List<AulaDto> aulas = aulaService.getAllAulas();
        return ResponseEntity.ok(aulas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AulaDto> getAulaById(@PathVariable Integer id) {
        AulaDto aula = aulaService.getAulaById(id);
        if (aula != null) {
            return ResponseEntity.ok(aula);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAula(@PathVariable Integer id) {
        aulaService.deleteAulaById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/alumno/{alumnoId}")
    public ResponseEntity<List<AulaConProfesorDto>> getAulasByAlumnoId(@PathVariable Integer alumnoId) {
        List<AulaConProfesorDto> aulas = aulaService.getAulasByAlumnoId(alumnoId);
        return ResponseEntity.ok(aulas);
    }
}