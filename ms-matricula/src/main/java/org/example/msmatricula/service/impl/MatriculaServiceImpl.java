package org.example.msmatricula.service.impl;

import org.example.msmatricula.Dto.AlumnoDto;
import org.example.msmatricula.Dto.CursoDto;
import org.example.msmatricula.entity.Matricula;
import org.example.msmatricula.feign.AlumnoFeign;
import org.example.msmatricula.feign.CursoFeign;
import org.example.msmatricula.repository.MatriculaRepository;
import org.example.msmatricula.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    @Override
    public List<Matricula> listar() {
        List<Matricula> matriculas = matriculaRepository.findAll();
        for (Matricula matricula : matriculas) {
            // Obtener información del curso
            ResponseEntity<CursoDto> cursoResponse = cursoFeign.listById(matricula.getCursoId());
            if (cursoResponse.getStatusCode() == HttpStatus.OK) {
                matricula.setCursoDto(cursoResponse.getBody());
            } else {
                // Manejar el error si la solicitud no fue exitosa
            }

            // Obtener información del alumno
            ResponseEntity<AlumnoDto> alumnoResponse = alumnoFeign.listById(matricula.getAlumnoId());
            if (alumnoResponse.getStatusCode() == HttpStatus.OK) {
                matricula.setAlumnoDto(alumnoResponse.getBody());
            } else {
                // Manejar el error si la solicitud no fue exitosa
            }
        }
        return matriculas;
    }
    @Override
    public Matricula guardar(Matricula matricula) {
        matricula.setCursoDto(cursoFeign.listById(matricula.getCursoId()).getBody());
        if (matricula == null){
            throw new RuntimeException("La matrícula no puede ser nula");
        }
        return matriculaRepository.save(matricula);
    }


    @Override
    public Optional<Matricula> buscarPorId(Integer id) {
        Matricula matricula = matriculaRepository.findById(id).orElse(null);
        if (matricula != null) {
            ResponseEntity<CursoDto> response = cursoFeign.listById(matricula.getCursoId());
            if (response.getStatusCode() == HttpStatus.OK) {
                matricula.setCursoDto(response.getBody());
            } else {
                // Manejar el error si la solicitud no fue exitosa
                // Puedes lanzar una excepción, registrar un mensaje de error, etc.
            }
        }
        return Optional.ofNullable(matricula);
    }

    @Override
    public Matricula editar(Matricula matricula) {
        return matriculaRepository.save(matricula);
    }

    @Override
    public void eliminar(Integer id) {
        matriculaRepository.deleteById(id);

    }
}
