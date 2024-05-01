package org.example.msmatricula.controller;

import org.example.msmatricula.entity.Matricula;
import org.example.msmatricula.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok().body(matriculaService.buscarPorId(id).get());
    }
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable(required = true)Integer id){
        matriculaService.eliminar(id);
        return "Eliminacion correcta";
    }
}
