package com.example.crudprofesores.controller;

import com.example.crudprofesores.entity.Profesores;
import com.example.crudprofesores.service.ProfesoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profesores")
public class ProfesoresController {
    @Autowired
    private ProfesoresService profesoresService;
    @GetMapping
    public ResponseEntity<List<Profesores>> listar(){
        return ResponseEntity.ok(profesoresService.listar());
    }
    @PostMapping
    public ResponseEntity<Profesores> guardar(@RequestBody Profesores profesores){
        return ResponseEntity.ok(profesoresService.guardar(profesores));
    }
    @GetMapping("/{id}")
    public  ResponseEntity<Profesores> busacarPorId(@PathVariable(required = true) Integer id){
        return ResponseEntity.ok(profesoresService.buscarPorId(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Profesores> editar(@PathVariable(required = true) Integer id, @RequestBody Profesores profesores){
        profesores.setId(id);
        return  ResponseEntity.ok(profesoresService.editar(profesores));
    }
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable(required = true) Integer id){
        profesoresService.eliminar(id);
        return "Eliminacion completa";
    }
}
