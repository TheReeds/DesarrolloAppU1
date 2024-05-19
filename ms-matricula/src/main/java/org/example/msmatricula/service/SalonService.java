package org.example.msmatricula.service;

import org.example.msmatricula.Dto.AlumnoDto;

import java.util.List;

public interface SalonService {
    List<AlumnoDto> obtenerEstudiantesPorCurso(int idCurso);
}
