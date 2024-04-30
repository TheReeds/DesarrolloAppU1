package org.example.mscurso.service.impl;

import org.example.mscurso.dto.ProfesoresDto;
import org.example.mscurso.entity.Curso;
import org.example.mscurso.feign.ProfesoresFeign;
import org.example.mscurso.repository.CursoRepository;
import org.example.mscurso.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return cursoRepository.findAll();
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
        Curso curso = cursoRepository.findById(id).get();
        curso.setProfesoresDto(profesoresFeign.listById(curso.getProfesorId()).getBody());
        return curso;
    }
    @Override
    public Curso editar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    public void eliminar(Integer id) {
        cursoRepository.deleteById(id);

    }
}
