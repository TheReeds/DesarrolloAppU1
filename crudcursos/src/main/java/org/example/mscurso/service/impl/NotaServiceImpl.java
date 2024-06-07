package org.example.mscurso.service.impl;

import org.example.mscurso.dto.NotaDto;
import org.example.mscurso.dto.NotasRequestDto;
import org.example.mscurso.entity.Curso;
import org.example.mscurso.entity.Nota;
import org.example.mscurso.feign.AlumnoFeign;
import org.example.mscurso.repository.CursoRepository;
import org.example.mscurso.repository.NotaRepository;
import org.example.mscurso.service.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotaServiceImpl implements NotaService {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private AlumnoFeign alumnoFeign;

    @Override
    public Nota registrarNota(Nota nota) {
        return notaRepository.save(nota);
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