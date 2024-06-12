package org.example.mscurso.service.impl;

import org.example.mscurso.dto.*;
import org.example.mscurso.entity.Curso;
import org.example.mscurso.entity.Nota;
import org.example.mscurso.feign.AlumnoFeign;
import org.example.mscurso.feign.MatriculaFeign;
import org.example.mscurso.repository.CursoRepository;
import org.example.mscurso.repository.NotaRepository;
import org.example.mscurso.service.NotaService;
import org.example.mscurso.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class NotaServiceImpl implements NotaService {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private MatriculaFeign matriculaFeign;
    @Autowired
    private PdfService pdfService;

    @Override
    public Nota registrarNota(Nota nota) {
        return notaRepository.save(nota);
    }
    @Override
    public void inicializarNotasParaCurso(Integer cursoId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        List<MatriculaDto> matriculas = matriculaFeign.obtenerMatriculasPorCurso(cursoId);

        List<Nota> notas = matriculas.stream()
                .flatMap(matricula -> {
                    return IntStream.rangeClosed(1, curso.getNumeroDePeriodos())
                            .mapToObj(periodo -> {
                                Nota nota = new Nota();
                                nota.setCurso(curso);
                                nota.setAlumnoId(matricula.getAlumnoId());
                                nota.setPeriodo(periodo);
                                nota.setValor(0);
                                return nota;
                            });
                }).collect(Collectors.toList());

        notaRepository.saveAll(notas);
    }

    @Override
    public List<Nota> registrarVariasNotas(NotasRequestDto request) {
        Curso curso = cursoRepository.findById(request.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        List<Nota> notas = request.getNotas().stream()
                .map(notaDetalle -> {
                    Nota nota = new Nota();
                    nota.setCurso(curso);
                    nota.setAlumnoId(notaDetalle.getAlumnoId());
                    nota.setPeriodo(notaDetalle.getPeriodo());
                    nota.setValor(notaDetalle.getValor());
                    return nota;
                }).collect(Collectors.toList());

        return notaRepository.saveAll(notas);
    }

    @Override
    public List<NotaDto> obtenerNotasPorCursoId(Integer cursoId) {
        List<Nota> notas = notaRepository.findByCursoId(cursoId);
        return notas.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<NotaDto> obtenerNotasPorAlumnoId(Integer alumnoId) {
        List<Nota> notas = notaRepository.findByAlumnoId(alumnoId);
        return notas.stream().map(this::mapToDto).collect(Collectors.toList());
    }
    @Override
    public List<NotaDto> obtenerNotasPorCursoIdYAlumnoId(Integer cursoId, Integer alumnoId) {
        List<Nota> notas = notaRepository.findByCursoIdAndAlumnoId(cursoId, alumnoId);
        return notas.stream().map(this::mapToDto).collect(Collectors.toList());
    }
    @Override
    public void inicializarNotasParaAlumnoEnCurso(Integer cursoId, Integer alumnoId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        List<Nota> notas = IntStream.rangeClosed(1, curso.getNumeroDePeriodos())
                .mapToObj(periodo -> {
                    Nota nota = new Nota();
                    nota.setCurso(curso);
                    nota.setAlumnoId(alumnoId);
                    nota.setPeriodo(periodo);
                    nota.setValor(0);
                    return nota;
                }).collect(Collectors.toList());

        notaRepository.saveAll(notas);
    }
    @Override
    public void bulkUpdateNotas(NotaBulkUpdateDto bulkUpdateDto) {
        Integer cursoId = bulkUpdateDto.getCursoId();
        for (NotaBulkUpdateDto.NotaAlumnoDto notaAlumno : bulkUpdateDto.getNotasAlumnos()) {
            Integer alumnoId = notaAlumno.getAlumnoId();
            for (NotaBulkUpdateDto.NotaPeriodoDto notaPeriodo : notaAlumno.getNotasPeriodos()) {
                Optional<Nota> notaOpt = notaRepository.findByCursoIdAndAlumnoIdAndPeriodo(cursoId, alumnoId, notaPeriodo.getPeriodo());
                if (notaOpt.isPresent()) {
                    Nota nota = notaOpt.get();
                    nota.setValor(notaPeriodo.getValor());
                    notaRepository.save(nota);
                } else {
                    // Lógica opcional en caso de que la nota no exista (puede lanzar una excepción o ignorar)
                }
            }
        }
    }
    @Override
    public void eliminarNotasPorAlumnoId(Integer alumnoId) {
        List<Nota> notas = notaRepository.findByAlumnoId(alumnoId);
        notaRepository.deleteAll(notas);
    }
    @Override
    public AulaNotaDto obtenerDatosPorAulaId(Integer aulaId) {
        AulaDto aulaDto = matriculaFeign.getAulaById(aulaId);
        Curso curso = cursoRepository.findById(aulaDto.getCurso().getId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        List<NotaDto> notas = obtenerNotasPorCursoId(curso.getId());

        AulaNotaDto aulaNotaDto = new AulaNotaDto();
        aulaNotaDto.setAula(aulaDto);
        aulaNotaDto.setCurso(curso);
        aulaNotaDto.setNotas(notas);

        return aulaNotaDto;
    }
    @Override
    public ByteArrayInputStream exportNotasToPdf(Integer aulaId) {
        AulaNotaDto aulaNotaDto = obtenerDatosPorAulaId(aulaId);
        return pdfService.generateNotasPdf(aulaNotaDto);
    }


    private NotaDto mapToDto(Nota nota) {
        NotaDto dto = new NotaDto();
        dto.setId(nota.getId());
        dto.setCursoId(nota.getCurso().getId());
        dto.setAlumnoId(nota.getAlumnoId());
        dto.setPeriodo(nota.getPeriodo());
        dto.setValor(nota.getValor());
        return dto;
    }
}