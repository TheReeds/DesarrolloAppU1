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
        // Obtener información del alumno
        ResponseEntity<AlumnoDto> alumnoResponse = alumnoFeign.listById(matricula.getAlumnoId());
        if (alumnoResponse.getStatusCode() == HttpStatus.OK) {
            matricula.setAlumnoDto(alumnoResponse.getBody());
        } else {
            // Manejar el error si la solicitud no fue exitosa
        }

        Matricula savedMatricula = matriculaRepository.save(matricula);

        // Inicializar notas para cada curso en la nueva matrícula
        for (MatriculaCursos matriculaCurso : savedMatricula.getCursos()) {
            cursoFeign.inicializarNotasParaAlumnoEnCurso(matriculaCurso.getCursoId(), savedMatricula.getAlumnoId());
        }
        // Generar PDF de constancia de matrícula
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
        matriculaRepository.deleteById(id);

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
}
