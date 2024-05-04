package org.example.msmatricula.service;

import org.example.msmatricula.entity.Matricula;

import java.util.List;
import java.util.Optional;

public interface MatriculaService {
    public List<Matricula> listar();
    public Matricula guardar(Matricula matricula);
    public Matricula buscarPorId(Integer id);
    public Matricula editar(Matricula matricula);
    public void eliminar(Integer id);
}
