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

import java.util.ArrayList;
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
        // Obtener todas las aulas de la base de datos
        List<Aula> aulas = aulaRepository.findAll();

        // Para cada aula, obtener la información adicional del curso, el profesor y los estudiantes
        for (Aula aula : aulas) {
            // Obtener información del curso y del profesor
            CursoDto cursoDto = cursoFeign.listById(aula.getCurso().getId()).getBody();
            ProfesorDto profesorDto = cursoDto.getProfesoresDto();

            Curso curso = new Curso();
            curso.setId(cursoDto.getId());
            curso.setNombre(cursoDto.getNombre());
            curso.setDescripcion(cursoDto.getDescripcion());
            curso.setDuracion(cursoDto.getDuracion());

            // Crear el objeto Profesor y asignarlo al curso
            Profesor profesor = new Profesor();
            profesor.setId(profesorDto.getId());
            profesor.setNombre(profesorDto.getNombre());
            profesor.setDni(profesorDto.getDni());
            profesor.setEspecialidad(profesorDto.getEspecialidad());
            profesor.setTelefono(profesorDto.getTelefono());

            curso.setProfesor(profesor);

            // Obtener la información de los estudiantes y asignarla al objeto Aula
            List<AulaAlumnos> alumnos = new ArrayList<>();
            for (AulaAlumnos aulaAlumno : aula.getAlumnos()) {
                AlumnoDto alumnoDto = alumnoFeign.listById(aulaAlumno.getAlumnoId()).getBody();
                AulaAlumnos aulaAlumnos = new AulaAlumnos();
                aulaAlumnos.setId(aulaAlumno.getId());  // Asignar el ID correctamente
                aulaAlumnos.setAlumnoId(alumnoDto.getId());
                aulaAlumnos.setAlumno(alumnoDto);
                alumnos.add(aulaAlumnos);
            }

            aula.setCurso(curso);
            aula.setProfesor(profesor);
            aula.setAlumnos(alumnos);
        }

        return aulas;
    }

    @Override
    public Aula getAulaById(Integer id) {
        Optional<Aula> optionalAula = aulaRepository.findById(id);
        if (optionalAula.isPresent()) {
            Aula aula = optionalAula.get();

            CursoDto cursoDto = cursoFeign.listById(aula.getCurso().getId()).getBody();
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

            List<AulaAlumnos> alumnos = new ArrayList<>();
            for (AulaAlumnos aulaAlumno : aula.getAlumnos()) {
                AlumnoDto alumnoDto = alumnoFeign.listById(aulaAlumno.getAlumnoId()).getBody();
                AulaAlumnos aulaAlumnos = new AulaAlumnos();
                aulaAlumnos.setId(aulaAlumno.getId());  // Asignar el ID correctamente
                aulaAlumnos.setAlumnoId(alumnoDto.getId());
                aulaAlumnos.setAlumno(alumnoDto);
                alumnos.add(aulaAlumnos);
            }

            aula.setCurso(curso);
            aula.setProfesor(profesor);
            aula.setAlumnos(alumnos);

            return aula;
        } else {
            return null; // Manejar el caso en que no se encuentre el aula con el ID especificado
        }
    }
    @Override
    public void deleteAulaById(Integer id) {
        Optional<Aula> optionalAula = aulaRepository.findById(id);
        if (optionalAula.isPresent()) {
            Aula aula = optionalAula.get();

            // Desvincular las relaciones del aula con alumnos, curso y profesor
            aula.getAlumnos().clear();
            aula.setCurso(null);
            aula.setProfesor(null);
            aulaRepository.save(aula); // Guardar cambios para reflejar la desvinculación

            aulaRepository.deleteById(id); // Ahora eliminar el aula

            // Eliminar curso y profesor si ya no están referenciados por ninguna otra entidad
            Curso curso = aula.getCurso();
            if (curso != null) {
                List<Aula> aulasConElCurso = aulaRepository.findByCurso(curso);
                if (aulasConElCurso.isEmpty()) {
                    cursoRepository.deleteById(curso.getId());
                }
            }

            Profesor profesor = aula.getProfesor();
            if (profesor != null) {
                List<Aula> aulasConElProfesor = aulaRepository.findByProfesor(profesor);
                if (aulasConElProfesor.isEmpty()) {
                    profesorRepository.deleteById(profesor.getId());
                }
            }
        } else {
            throw new RuntimeException("Aula no encontrada con el id: " + id);
        }
    }
}
