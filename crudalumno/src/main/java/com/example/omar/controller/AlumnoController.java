package com.example.omar.controller;

import com.example.omar.entity.Alumno;
import com.example.omar.repository.AlumnoRepository;
import com.example.omar.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alumno")
public class AlumnoController {
    @Autowired
    private AlumnoService alumnoService;
    @Autowired
    private AlumnoRepository alumnoRepository;
    @GetMapping()
    public ResponseEntity<List<Alumno>> list(){
        return ResponseEntity.ok().body(alumnoService.listar());
    }
    @PostMapping()
    public ResponseEntity<Alumno> save(@RequestBody Alumno alumno){
        return  ResponseEntity.ok(alumnoService.guardar(alumno));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Alumno> update(@PathVariable Integer id, @RequestBody Alumno alumno) {
        Alumno existingAlumno = alumnoService.listarPorId(id).orElse(null);
        if (existingAlumno == null) {
            return ResponseEntity.notFound().build();
        }
        existingAlumno.setNombre(alumno.getNombre());
        existingAlumno.setApellidos(alumno.getApellidos());
        existingAlumno.setTelefono(alumno.getTelefono());
        existingAlumno.setDni(alumno.getDni());
        existingAlumno.setEstado(alumno.isEstado());
        existingAlumno.setUsuarioId(alumno.getUsuarioId());
        existingAlumno.setGrado(alumno.getGrado());
        alumnoService.actualizar(existingAlumno);
        return ResponseEntity.ok(existingAlumno);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Alumno> listById(@PathVariable(required = true) Integer id){
        return ResponseEntity.ok().body(alumnoService.listarPorId(id).get());
    }
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable(required = true) Integer id){
        alumnoService.eliminarPorId(id);
        return "SE ELIMINO CORRECTAMENTE";
    }
    @PutMapping("/{id}/estado")
    public ResponseEntity<Alumno> actualizarEstado(@PathVariable Integer id, @RequestParam boolean estado) {
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno == null) {
            return ResponseEntity.notFound().build();
        }
        alumno.setEstado(estado);
        alumnoRepository.save(alumno);
        return ResponseEntity.ok(alumno);
    }
}
