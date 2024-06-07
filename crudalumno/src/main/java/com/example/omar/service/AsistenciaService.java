package com.example.omar.service;

import com.example.omar.dto.AsistenciaDto;
import com.example.omar.entity.Asistencia;

import java.util.List;

public interface AsistenciaService {
    Asistencia registrarAsistencia(Asistencia asistencia);
    List<Asistencia> registrarVariasAsistencias(List<AsistenciaDto> asistencias);
    List<Asistencia> obtenerAsistenciasPorAlumnoId(Integer alumnoId);
    List<Asistencia> obtenerAsistenciasPorCursoId(Integer cursoId);
    List<Asistencia> obtenerTodasLasAsistencias();
}
