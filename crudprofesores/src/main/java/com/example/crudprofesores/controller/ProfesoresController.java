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
        return ResponseEntity.ok(profesoresService.buscarPorId(id).get());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Profesores> editar(@PathVariable Integer id, @RequestBody Profesores profesores) {
        Profesores existingProfesor = profesoresService.buscarPorId(id).orElse(null);
        if (existingProfesor == null) {
            return ResponseEntity.notFound().build();
        }
        existingProfesor.setNombre(profesores.getNombre());
        existingProfesor.setDni(profesores.getDni());
        existingProfesor.setEspecialidad(profesores.getEspecialidad());
        existingProfesor.setTelefono(profesores.getTelefono());
        existingProfesor.setUsuarioId(profesores.getUsuarioId());
        profesoresService.editar(existingProfesor);
        return ResponseEntity.ok(existingProfesor);
    }
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable(required = true) Integer id){
        profesoresService.eliminar(id);
        return "Eliminacion completa";
    }

}
