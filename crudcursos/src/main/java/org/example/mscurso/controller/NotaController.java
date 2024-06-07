package org.example.mscurso.controller;

import org.example.mscurso.dto.NotaDto;
import org.example.mscurso.dto.NotasRequestDto;
import org.example.mscurso.entity.Nota;
import org.example.mscurso.service.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notas")
public class NotaController {

    @Autowired
    private NotaService notaService;

    @PostMapping
    public ResponseEntity<Nota> registrarNota(@RequestBody Nota nota) {
        Nota nuevaNota = notaService.registrarNota(nota);
        return ResponseEntity.ok(nuevaNota);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Nota>> registrarVariasNotas(@RequestBody NotasRequestDto request) {
        List<Nota> nuevasNotas = notaService.registrarVariasNotas(request);
        return ResponseEntity.ok(nuevasNotas);
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<NotaDto>> obtenerNotasPorCursoId(@PathVariable Integer cursoId) {
        List<NotaDto> notas = notaService.obtenerNotasPorCursoId(cursoId);
        return ResponseEntity.ok(notas);
    }

    @GetMapping("/alumno/{alumnoId}")
    public ResponseEntity<List<NotaDto>> obtenerNotasPorAlumnoId(@PathVariable Integer alumnoId) {
        List<NotaDto> notas = notaService.obtenerNotasPorAlumnoId(alumnoId);
        return ResponseEntity.ok(notas);
    }
}