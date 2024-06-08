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
    public ResponseEntity<Alumno> update(@RequestBody Alumno curso) {
        return ResponseEntity.ok(alumnoService.actualizar(curso));
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
