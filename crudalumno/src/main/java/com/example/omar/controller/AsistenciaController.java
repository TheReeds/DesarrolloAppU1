package com.example.omar.controller;

import com.example.omar.entity.Asistencia;
import com.example.omar.service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asistencias")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @PostMapping
    public ResponseEntity<Asistencia> registrarAsistencia(@RequestBody Asistencia asistencia) {
        Asistencia nuevaAsistencia = asistenciaService.registrarAsistencia(asistencia);
        return ResponseEntity.ok(nuevaAsistencia);
    }

    @GetMapping("/alumno/{alumnoId}")
    public ResponseEntity<List<Asistencia>> obtenerAsistenciasPorAlumnoId(@PathVariable Integer alumnoId) {
        List<Asistencia> asistencias = asistenciaService.obtenerAsistenciasPorAlumnoId(alumnoId);
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping
    public ResponseEntity<List<Asistencia>> obtenerTodasLasAsistencias() {
        List<Asistencia> asistencias = asistenciaService.obtenerTodasLasAsistencias();
        return ResponseEntity.ok(asistencias);
    }
}