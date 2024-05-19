package org.example.msmatricula.service.impl;

import org.example.msmatricula.Dto.AlumnoDto;
import org.example.msmatricula.Dto.AulaDto;
import org.example.msmatricula.Dto.CursoDto;
import org.example.msmatricula.Dto.ProfesorDto;
import org.example.msmatricula.entity.Aula;
import org.example.msmatricula.entity.AulaAlumnos;
import org.example.msmatricula.entity.Curso;
import org.example.msmatricula.entity.Profesor;
import org.example.msmatricula.feign.AlumnoFeign;
import org.example.msmatricula.feign.CursoFeign;
import org.example.msmatricula.repository.AulaRepository;
import org.example.msmatricula.repository.CursoRepository;
import org.example.msmatricula.repository.ProfesorRepository;
import org.example.msmatricula.service.AulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
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
    private CursoRepository cursoRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Override
    public Aula createAula(AulaDto aulaDto) {
        CursoDto cursoDto = cursoFeign.listById(aulaDto.getCursoId()).getBody();
        ProfesorDto profesorDto = cursoDto.getProfesoresDto();

        Curso curso = new Curso();
        curso.setId(cursoDto.getId());
        curso.setNombre(cursoDto.getNombre());
        curso.setDescripcion(cursoDto.getDescripcion());
        curso.setDuracion(cursoDto.getDuracion());

        Profesor profesor = new Profesor();
        profesor.setId(profesorDto.getId());
        profesor.setNombre(profesorDto.getNombre());
        profesor.setDni(profesorDto.getDni());
        profesor.setEspecialidad(profesorDto.getEspecialidad());
        profesor.setTelefono(profesorDto.getTelefono());

        curso.setProfesor(profesor);

        // Guardar las entidades curso y profesor en la base de datos
        profesor = profesorRepository.save(profesor);
        curso = cursoRepository.save(curso);

        List<AulaAlumnos> alumnos = aulaDto.getAlumnoIds().stream()
                .map(alumnoId -> {
                    AlumnoDto alumnoDto = alumnoFeign.listById(alumnoId).getBody();
                    AulaAlumnos aulaAlumnos = new AulaAlumnos();
                    aulaAlumnos.setAlumnoId(alumnoId);
                    aulaAlumnos.setAlumno(alumnoDto);
                    return aulaAlumnos;
                })
                .collect(Collectors.toList());

        Aula aula = new Aula();
        aula.setNombre(aulaDto.getNombre());
        aula.setCurso(curso);
        aula.setProfesor(profesor);
        aula.setAlumnos(alumnos);

        return aulaRepository.save(aula);
    }

    @Override
    public List<Aula> getAllAulas() {
        return aulaRepository.findAll();
    }

    @Override
    public Aula getAulaById(Integer id) {
        return aulaRepository.findById(id).orElse(null);
    }
}
