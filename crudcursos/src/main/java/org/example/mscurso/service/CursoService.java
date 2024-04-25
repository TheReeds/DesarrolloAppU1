package org.example.mscurso.service;

import org.example.mscurso.entity.Curso;
import org.example.mscurso.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CursoService {
    public List<Curso> listar();
    public Curso guardar(Curso curso);
    public Curso buscarPorId(Integer id);
    public Curso editar(Curso curso);
    public void eliminar(Integer id);
}
