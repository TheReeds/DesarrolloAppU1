package org.example.msmatricula.service.impl;

import org.example.msmatricula.Dto.AlumnoDto;
import org.example.msmatricula.Dto.CursoDto;
import org.example.msmatricula.entity.Matricula;
import org.example.msmatricula.entity.MatriculaCursos;
import org.example.msmatricula.feign.AlumnoFeign;
import org.example.msmatricula.feign.CursoFeign;
import org.example.msmatricula.repository.MatriculaRepository;
import org.example.msmatricula.service.ExcelService;
import org.example.msmatricula.service.MatriculaService;
import org.example.msmatricula.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatriculaServiceImpl implements MatriculaService {
    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    public CursoFeign cursoFeign;

    @Autowired
    public AlumnoFeign alumnoFeign;
    @Autowired
    private PdfService pdfService;

    @Autowired
    private ExcelService excelService;

    @Override
    public List<Matricula> listar() {
        List<Matricula> matriculas = matriculaRepository.findAll();
        for (Matricula matricula : matriculas) {
            // Obtener información del alumno
            ResponseEntity<AlumnoDto> alumnoResponse = alumnoFeign.listById(matricula.getAlumnoId());
            if (alumnoResponse.getStatusCode() == HttpStatus.OK) {
                matricula.setAlumnoDto(alumnoResponse.getBody());
            } else {
                // Manejar el error si la solicitud no fue exitosa
            }
            List<MatriculaCursos> matriculaCursos = matricula.getCursos().stream().map(matriculaCurso -> {
                matriculaCurso.setCurso(cursoFeign.listById(matriculaCurso.getCursoId()).getBody());
                return matriculaCurso;
            }).toList();
            matricula.setCursos(matriculaCursos);
        }
        return matriculas;
    }

    @Override
    public Matricula guardar(Matricula matricula) {
        ResponseEntity<AlumnoDto> alumnoResponse = alumnoFeign.listById(matricula.getAlumnoId());
        if (alumnoResponse.getStatusCode() == HttpStatus.OK) {
            AlumnoDto alumnoDto = alumnoResponse.getBody();
            if (alumnoDto.isEstado()) {
                // Manejar el caso en que el estudiante ya está matriculado
                throw new IllegalStateException("El estudiante ya está matriculado.");
            } else {
                // Actualizar el estado del alumno a matriculado
                alumnoFeign.actualizarEstado(alumnoDto.getId(), true);
                matricula.setAlumnoDto(alumnoDto);
            }
        } else {
            // Manejar el error si la solicitud no fue exitosa
        }

        Matricula savedMatricula = matriculaRepository.save(matricula);

        for (MatriculaCursos matriculaCurso : savedMatricula.getCursos()) {
            cursoFeign.inicializarNotasParaAlumnoEnCurso(matriculaCurso.getCursoId(), savedMatricula.getAlumnoId());
        }

        ByteArrayInputStream pdfStream = pdfService.generateConstanciaMatriculaPdf(savedMatricula);

        return savedMatricula;
    }



    @Override
    public Matricula buscarPorId(Integer id) {
        Matricula matricula = matriculaRepository.findById(id).get();
        matricula.setAlumnoDto(alumnoFeign.listById(matricula.getAlumnoId()).getBody());
        //
        List<MatriculaCursos> matriculaCursos = matricula.getCursos().stream().map(matriculaCurso -> {
            matriculaCurso.setCurso(cursoFeign.listById(matriculaCurso.getCursoId()).getBody());
            return matriculaCurso;
        }).toList();
        matricula.setCursos(matriculaCursos);

        return matricula;
    }

    @Override
    public Matricula editar(Matricula matricula) {
        return matriculaRepository.save(matricula);
    }



    @Override
    public void eliminar(Integer id) {
        Optional<Matricula> matriculaOptional = matriculaRepository.findById(id);
        if (matriculaOptional.isPresent()) {
            Matricula matricula = matriculaOptional.get();
            Integer alumnoId = matricula.getAlumnoId();

            // Cambiar el estado del alumno a false
            alumnoFeign.actualizarEstado(alumnoId, false);

            // Eliminar las notas del alumno
            cursoFeign.eliminarNotasPorAlumnoId(alumnoId);

            // Eliminar la matrícula
            matriculaRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("No se encontró la matrícula con el ID: " + id);
        }
    }
    @Override
    public List<Matricula> buscarPorCursoId(Integer cursoId) {
        List<Matricula> matriculas = matriculaRepository.findByCursosCursoId(cursoId);
        for (Matricula matricula : matriculas) {
            // Obtener información del alumno
            ResponseEntity<AlumnoDto> alumnoResponse = alumnoFeign.listById(matricula.getAlumnoId());
            if (alumnoResponse.getStatusCode() == HttpStatus.OK) {
                matricula.setAlumnoDto(alumnoResponse.getBody());
            }
            List<MatriculaCursos> matriculaCursos = matricula.getCursos().stream().map(matriculaCurso -> {
                matriculaCurso.setCurso(cursoFeign.listById(matriculaCurso.getCursoId()).getBody());
                return matriculaCurso;
            }).toList();
            matricula.setCursos(matriculaCursos);
        }
        return matriculas;
    }

    public ByteArrayInputStream exportToPdf() {
        List<Matricula> matriculas = listar();
        return pdfService.generateMatriculasPdf(matriculas);
    }

    public ByteArrayInputStream exportToExcel() {
        List<Matricula> matriculas = listar();
        return excelService.generateMatriculasExcel(matriculas);
    }
    @Override
    public ByteArrayInputStream exportConstanciaMatriculaPdf(Matricula matricula) {
        return pdfService.generateConstanciaMatriculaPdf(matricula);
    }
    @Override
    public List<AlumnoDto> listarAlumnosPorCursoId(Integer cursoId) {
        List<Matricula> matriculas = matriculaRepository.findByCursosCursoId(cursoId);
        return matriculas.stream()
                .map(matricula -> {
                    ResponseEntity<AlumnoDto> alumnoResponse = alumnoFeign.listById(matricula.getAlumnoId());
                    if (alumnoResponse.getStatusCode() == HttpStatus.OK) {
                        return alumnoResponse.getBody();
                    } else {
                        throw new RuntimeException("Error al obtener el alumno con ID: " + matricula.getAlumnoId());
                    }
                })
                .collect(Collectors.toList());
    }


}
