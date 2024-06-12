package org.example.mscurso.service;

import org.example.mscurso.dto.AulaNotaDto;
import org.example.mscurso.dto.NotaBulkUpdateDto;
import org.example.mscurso.dto.NotaDto;
import org.example.mscurso.dto.NotasRequestDto;
import org.example.mscurso.entity.Nota;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface NotaService {
    Nota registrarNota(Nota nota);
    List<Nota> registrarVariasNotas(NotasRequestDto request);
    void inicializarNotasParaCurso(Integer cursoId);
    void inicializarNotasParaAlumnoEnCurso(Integer cursoId, Integer alumnoId);

    List<NotaDto> obtenerNotasPorCursoId(Integer cursoId);
    List<NotaDto> obtenerNotasPorAlumnoId(Integer alumnoId);
    List<NotaDto> obtenerNotasPorCursoIdYAlumnoId(Integer cursoId, Integer alumnoId);
    public void eliminarNotasPorAlumnoId(Integer alumnoId);
    void bulkUpdateNotas(NotaBulkUpdateDto bulkUpdateDto);
    public AulaNotaDto obtenerDatosPorAulaId(Integer aulaId);
    ByteArrayInputStream exportNotasToPdf(Integer aulaId);

}