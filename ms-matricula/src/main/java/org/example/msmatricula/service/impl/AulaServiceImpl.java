package org.example.msmatricula.service.impl;

import org.example.msmatricula.Dto.*;
import org.example.msmatricula.entity.Aula;
import org.example.msmatricula.entity.AulaAlumnos;
import org.example.msmatricula.feign.AlumnoFeign;
import org.example.msmatricula.feign.CursoFeign;
import org.example.msmatricula.feign.ProfesorFeign;
import org.example.msmatricula.repository.AulaRepository;
import org.example.msmatricula.service.AulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AulaServiceImpl implements AulaService {
    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private CursoFeign cursoFeign;

    @Autowired
    private AlumnoFeign alumnoFeign;
    @Autowired
    private ProfesorFeign profesorFeign;

    @Override
    public Aula createAula(AulaDto aulaDto) {
        // Create AulaAlumnos entities from the given alumnoIds
        List<AulaAlumnos> alumnos = aulaDto.getAlumnos().stream()
                .map(alumnoDto -> {
                    AulaAlumnos aulaAlumnos = new AulaAlumnos();
                    aulaAlumnos.setAlumnoId(alumnoDto.getId());
                    return aulaAlumnos;
                })
                .collect(Collectors.toList());

        // Create and save the Aula entity
        Aula aula = new Aula();
        aula.setNombre(aulaDto.getNombre());
        aula.setGradoId(aulaDto.getGrado().getId());
        aula.setCursoId(aulaDto.getCurso().getId());
        aula.setProfesorId(aulaDto.getProfesor().getId());
        aula.setAlumnos(alumnos);

        return aulaRepository.save(aula);
    }

    @Override
    public List<AulaDto> getAllAulas() {
        // Obtener todas las aulas de la base de datos
        List<Aula> aulas = aulaRepository.findAll();

        // Mapear la información adicional del curso, el profesor y los estudiantes
        return aulas.stream()
                .map(aula -> {
                    AulaDto aulaDto = new AulaDto();
                    aulaDto.setId(aula.getId());
                    aulaDto.setNombre(aula.getNombre());

                    CursoDto cursoDto = cursoFeign.listById(aula.getCursoId()).getBody();
                    ProfesorDto profesorDto = profesorFeign.listById(aula.getProfesorId()).getBody();
                    GradoDto gradoDto = alumnoFeign.listGradoById(aula.getGradoId()).getBody();

                    List<AlumnoDto> alumnosDto = aula.getAlumnos().stream()
                            .map(aulaAlumno -> {
                                AlumnoDto alumnoDto = alumnoFeign.listById(aulaAlumno.getAlumnoId()).getBody();
                                return alumnoDto;
                            })
                            .collect(Collectors.toList());

                    aulaDto.setCurso(cursoDto);
                    aulaDto.setProfesor(profesorDto);
                    aulaDto.setGrado(gradoDto);
                    aulaDto.setAlumnos(alumnosDto);

                    return aulaDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public AulaDto getAulaById(Integer id) {
        Optional<Aula> optionalAula = aulaRepository.findById(id);
        if (optionalAula.isPresent()) {
            Aula aula = optionalAula.get();

            AulaDto aulaDto = new AulaDto();
            aulaDto.setId(aula.getId());
            aulaDto.setNombre(aula.getNombre());

            CursoDto cursoDto = cursoFeign.listById(aula.getCursoId()).getBody();
            ProfesorDto profesorDto = profesorFeign.listById(aula.getProfesorId()).getBody();
            GradoDto gradoDto = alumnoFeign.listGradoById(aula.getGradoId()).getBody();

            List<AlumnoDto> alumnosDto = aula.getAlumnos().stream()
                    .map(aulaAlumno -> {
                        AlumnoDto alumnoDto = alumnoFeign.listById(aulaAlumno.getAlumnoId()).getBody();
                        return alumnoDto;
                    })
                    .collect(Collectors.toList());

            aulaDto.setCurso(cursoDto);
            aulaDto.setProfesor(profesorDto);
            aulaDto.setGrado(gradoDto);
            aulaDto.setAlumnos(alumnosDto);

            return aulaDto;
        } else {
            return null;
        }
    }

    @Override
    public void deleteAulaById(Integer id) {
        Optional<Aula> optionalAula = aulaRepository.findById(id);
        if (optionalAula.isPresent()) {
            Aula aula = optionalAula.get();

            // Remove the association of the curso and profesor
            aula.setCursoId(null);
            aula.setProfesorId(null);

            // Save the aula before deleting
            aulaRepository.save(aula);

            aulaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Aula no encontrada con el id: " + id);
        }
    }
    @Override
    public List<AulaConProfesorDto> getAulasByAlumnoId(Integer alumnoId) {
        List<Aula> aulas = aulaRepository.findAll();

        return aulas.stream()
                .filter(aula -> aula.getAlumnos().stream()
                        .anyMatch(aulaAlumno -> aulaAlumno.getAlumnoId().equals(alumnoId)))
                .map(aula -> {
                    AulaConProfesorDto dto = new AulaConProfesorDto();
                    dto.setAulaId(aula.getId());
                    dto.setAulaNombre(aula.getNombre());
                    dto.setProfesor(profesorFeign.listById(aula.getProfesorId()).getBody());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
