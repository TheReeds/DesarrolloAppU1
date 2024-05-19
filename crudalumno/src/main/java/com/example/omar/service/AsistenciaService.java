package com.example.omar.service;

import com.example.omar.entity.Asistencia;

import java.util.List;

public interface AsistenciaService {
    Asistencia registrarAsistencia(Asistencia asistencia);
    List<Asistencia> obtenerAsistenciasPorAlumnoId(Integer alumnoId);
    List<Asistencia> obtenerTodasLasAsistencias();
}
