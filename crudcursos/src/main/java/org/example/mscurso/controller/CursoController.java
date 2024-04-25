package org.example.mscurso.controller;

import org.example.mscurso.entity.Curso;
import org.example.mscurso.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cursos")
public class CursoController {
    @Autowired
    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public ResponseEntity<List<Curso>> list() {
        return ResponseEntity.ok().body(cursoService.listar());
    }

    @PostMapping
    public ResponseEntity<Curso> save(@RequestBody Curso curso) {
        return ResponseEntity.ok(cursoService.guardar(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> update(@RequestBody Curso curso) {
        return ResponseEntity.ok(cursoService.editar(curso));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> ListById(@PathVariable(required = true)Integer id) {
        return ResponseEntity.ok().body(cursoService.buscarPorId(id));
    }
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable(required = true)Integer id){
        cursoService.eliminar(id);
        return "Eliminacion correcta";
    }
}
