package org.example.msmatricula.service.impl;

import org.example.msmatricula.Dto.AlumnoDto;
import org.example.msmatricula.entity.Matricula;
import org.example.msmatricula.entity.MatriculaCursos;
import org.example.msmatricula.feign.AlumnoFeign;
import org.example.msmatricula.feign.CursoFeign;
import org.example.msmatricula.repository.MatriculaRepository;
import org.example.msmatricula.service.SalonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalonServiceImpl implements SalonService {
    @Autowired
    private MatriculaRepository matriculaRepository;
    @Autowired
    private AlumnoFeign alumnoFeign;
    @Autowired
    private CursoFeign cursoFeign;
    public List<AlumnoDto> obtenerEstudiantesPorCurso(int idCurso) {
        List<Matricula> matriculas = matriculaRepository.findAll();
        List<AlumnoDto> estudiantesDelCurso = new ArrayList<>();


        for (Matricula matricula : matriculas) {
            // Filtrar las matrículas para incluir solo aquellas que correspondan al curso deseado
            List<MatriculaCursos> cursosDelCurso = matricula.getCursos().stream()
                    .filter(curso -> curso.getCursoId() == idCurso)
                    .collect(Collectors.toList());

            // Si la matrícula tiene cursos correspondientes al curso deseado, obtener la información del alumno
            if (!cursosDelCurso.isEmpty()) {
                ResponseEntity<AlumnoDto> alumnoResponse = alumnoFeign.listById(matricula.getAlumnoId());
                if (alumnoResponse.getStatusCode() == HttpStatus.OK) {
                    estudiantesDelCurso.add(alumnoResponse.getBody());
                } else {
                    // Manejar el error si la solicitud no fue exitosa
                }
                Integer profesorId = cursosDelCurso.get(0).getCurso().getProfesorId();

            }
        }

        return estudiantesDelCurso;
    }
}
