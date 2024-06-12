package org.example.mscurso.controller;

import org.example.mscurso.dto.AulaNotaDto;
import org.example.mscurso.dto.NotaBulkUpdateDto;
import org.example.mscurso.dto.NotaDto;
import org.example.mscurso.dto.NotasRequestDto;
import org.example.mscurso.entity.Nota;
import org.example.mscurso.service.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
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
    @PostMapping("/inicializar/{cursoId}")
    public ResponseEntity<Void> inicializarNotasParaCurso(@PathVariable Integer cursoId) {
        notaService.inicializarNotasParaCurso(cursoId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/inicializar/{cursoId}/alumno/{alumnoId}")
    public ResponseEntity<Void> inicializarNotasParaAlumnoEnCurso(@PathVariable Integer cursoId, @PathVariable Integer alumnoId) {
        notaService.inicializarNotasParaAlumnoEnCurso(cursoId, alumnoId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/bulk-update")
    public ResponseEntity<Void> bulkUpdateNotas(@RequestBody NotaBulkUpdateDto bulkUpdateDto) {
        notaService.bulkUpdateNotas(bulkUpdateDto);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/alumno/{alumnoId}")
    public ResponseEntity<Void> eliminarNotasPorAlumnoId(@PathVariable Integer alumnoId) {
        notaService.eliminarNotasPorAlumnoId(alumnoId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/aula/{aulaId}")
    public ResponseEntity<AulaNotaDto> obtenerDatosPorAulaId(@PathVariable Integer aulaId) {
        AulaNotaDto aulaNotaDto = notaService.obtenerDatosPorAulaId(aulaId);
        return ResponseEntity.ok(aulaNotaDto);
    }
    @GetMapping("/aula/{aulaId}/pdf")
    public ResponseEntity<InputStreamResource> exportNotasToPdf(@PathVariable Integer aulaId) {
        ByteArrayInputStream bis = notaService.exportNotasToPdf(aulaId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=notas_aula_" + aulaId + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}