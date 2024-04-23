package com.example.crudprofesores.service;

import com.example.crudprofesores.entity.Profesores;

import java.util.List;

public interface ProfesoresService {
    public List<Profesores> listar();
    public Profesores guardar(Profesores profesores);
    public Profesores buscarPorId(Integer id);
    public Profesores editar(Profesores profesores);
    public void eliminar(Integer id);
}
