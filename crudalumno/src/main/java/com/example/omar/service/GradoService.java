package com.example.omar.service;

import com.example.omar.entity.Grado;

import java.util.List;
import java.util.Optional;

public interface GradoService {
    public List<Grado> listar();
    public Grado guardar(Grado grado);
    public Grado actualizar(Grado grado);
    public Optional<Grado> listarPorId(Integer id);
    public void eliminarPorId(Integer id);
}
