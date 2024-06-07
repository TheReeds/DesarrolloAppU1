package org.example.mscurso.service;

import org.example.mscurso.dto.NotaDto;
import org.example.mscurso.dto.NotasRequestDto;
import org.example.mscurso.entity.Nota;

import java.util.List;

public interface NotaService {
    Nota registrarNota(Nota nota);
    List<Nota> registrarVariasNotas(NotasRequestDto request);
    List<NotaDto> obtenerNotasPorCursoId(Integer cursoId);
    List<NotaDto> obtenerNotasPorAlumnoId(Integer alumnoId);
}