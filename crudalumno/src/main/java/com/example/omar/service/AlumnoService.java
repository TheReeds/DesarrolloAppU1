package com.example.omar.service;

import com.example.omar.entity.Alumno;

import java.util.List;
import java.util.Optional;

public interface AlumnoService {
    public List<Alumno> listar();
    public Alumno guardar(Alumno alumno);
    public Alumno actualizar(Alumno alumno);
    public Optional<Alumno> listarPorId(Integer id);
    public void eliminarPorId(Integer id);
}
