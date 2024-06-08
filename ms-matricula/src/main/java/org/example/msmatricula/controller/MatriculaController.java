package org.example.msmatricula.controller;

import org.example.msmatricula.entity.Matricula;
import org.example.msmatricula.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.InputStreamResource;

import java.io.ByteArrayInputStream;
import java.util.List;
@RestController
@RequestMapping("/matriculas")
public class MatriculaController {
    @Autowired
    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService cursoService) {
        this.matriculaService = cursoService;
    }

    @GetMapping
    public ResponseEntity<List<Matricula>> list() {
        return ResponseEntity.ok().body(matriculaService.listar());
    }

    @PostMapping
    public ResponseEntity<Matricula> save(@RequestBody Matricula curso) {
        return ResponseEntity.ok(matriculaService.guardar(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Matricula> update(@RequestBody Matricula curso) {
        return ResponseEntity.ok(matriculaService.editar(curso));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Matricula> ListById(@PathVariable(required = true)Integer id) {
        return ResponseEntity.ok().body(matriculaService.buscarPorId(id));
    }
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable(required = true)Integer id){
        matriculaService.eliminar(id);
        return "Eliminacion correcta";
    }
    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<Matricula>> buscarPorCursoId(@PathVariable Integer cursoId) {
        return ResponseEntity.ok(matriculaService.buscarPorCursoId(cursoId));
    }
    @GetMapping("/pdf")
    public ResponseEntity<InputStreamResource> exportToPdf() {
        ByteArrayInputStream bis = matriculaService.exportToPdf();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=matriculas.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/excel")
    public ResponseEntity<InputStreamResource> exportToExcel() {
        ByteArrayInputStream bis = matriculaService.exportToExcel();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=matriculas.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(bis));
    }
    @GetMapping("/constancia/{id}")
    public ResponseEntity<InputStreamResource> getConstanciaMatricula(@PathVariable Integer id) {
        Matricula matricula = matriculaService.buscarPorId(id);
        ByteArrayInputStream bis = matriculaService.exportConstanciaMatriculaPdf(matricula);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=constancia_matricula.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
