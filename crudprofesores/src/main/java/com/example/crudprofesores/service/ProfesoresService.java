package com.example.crudprofesores.service;

import com.example.crudprofesores.entity.Profesores;

import java.util.List;
import java.util.Optional;

public interface ProfesoresService {
    public List<Profesores> listar();
    public Profesores guardar(Profesores profesores);
    public Optional<Profesores> buscarPorId(Integer id);
    public Profesores editar(Profesores profesores);
    public void eliminar(Integer id);
    public List<Profesores> listarSinUsuario();
}
