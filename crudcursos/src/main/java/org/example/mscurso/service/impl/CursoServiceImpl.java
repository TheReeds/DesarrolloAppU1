package org.example.mscurso.service.impl;

import org.example.mscurso.dto.ProfesoresDto;
import org.example.mscurso.entity.Curso;
import org.example.mscurso.feign.ProfesoresFeign;
import org.example.mscurso.repository.CursoRepository;
import org.example.mscurso.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ProfesoresFeign profesoresFeign;

    @Override
    public List<Curso> listar() {
        List<Curso> cursos = cursoRepository.findAll();
        for (Curso curso : cursos) {
            ResponseEntity<ProfesoresDto> response = profesoresFeign.listById(curso.getProfesorId());
            if (response.getStatusCode() == HttpStatus.OK) {
                curso.setProfesoresDto(response.getBody());
            } else {
                // Manejar el error si la solicitud no fue exitosa
                // Puedes lanzar una excepción, registrar un mensaje de error, etc.
            }
        }

        return cursos;
    }

    @Override
    public Curso guardar(Curso curso) {
        curso.setProfesoresDto(profesoresFeign.listById(curso.getProfesorId()).getBody());
        if (curso == null){
            throw new RuntimeException("El curso existe");
        }else{
        return cursoRepository.save(curso);}
    }


    @Override
    public Curso buscarPorId(Integer id) {
        Curso curso = cursoRepository.findById(id).orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        curso.setProfesoresDto(profesoresFeign.listById(curso.getProfesorId()).getBody());
        return curso;
    }
    @Override
    public Curso editar(Curso curso) {
        ProfesoresDto profesorDto = profesoresFeign.listById(curso.getProfesorId()).getBody();
        curso.setProfesoresDto(profesorDto);
        return cursoRepository.save(curso);
    }

    @Override
    public void eliminar(Integer id) {
        cursoRepository.deleteById(id);
    }
}
