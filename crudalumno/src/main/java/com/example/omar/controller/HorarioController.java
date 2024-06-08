package com.example.omar.controller;

import com.example.omar.entity.Horario;
import com.example.omar.service.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/horarios")
public class HorarioController {
    @Autowired
    private HorarioService horarioService;

    @GetMapping
    public List<Horario> listar() {
        return horarioService.listar();
    }

    @PostMapping
    public Horario guardar(@RequestBody Horario horario) {
        return horarioService.guardar(horario);
    }

    @PutMapping
    public Horario actualizar(@RequestBody Horario horario) {
        return horarioService.actualizar(horario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Integer id) {
        horarioService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Horario> listarPorId(@PathVariable Integer id) {
        Optional<Horario> horario = horarioService.listarPorId(id);
        if (horario.isPresent()) {
            return ResponseEntity.ok(horario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/curso/{cursoId}")
    public List<Horario> listarPorCurso(@PathVariable Integer cursoId) {
        return horarioService.listarPorCurso(cursoId);
    }

    @GetMapping("/grado/{gradoId}")
    public List<Horario> listarPorGrado(@PathVariable Integer gradoId) {
        return horarioService.listarPorGrado(gradoId);
    }
    @GetMapping("/aula/{aulaId}")
    public List<Horario> listarPorAula(@PathVariable Integer aulaId) {
        return horarioService.listarPorAula(aulaId);
    }


}