package com.example.omar.service;

import com.example.omar.entity.Horario;

import java.util.List;
import java.util.Optional;

public interface HorarioService {
    List<Horario> listar();
    Horario guardar(Horario horario);
    Horario actualizar(Horario horario);
    void eliminarPorId(Integer id);
    Optional<Horario> listarPorId(Integer id);
    boolean isAulaAvailable(Integer aulaId, String diaSemana, String horaInicio, String horaFin);
    boolean isCursoValid(Integer cursoId);
    List<Horario> listarPorCurso(Integer cursoId);
    List<Horario> listarPorGrado(Integer gradoId);
    List<Horario> listarPorAula(Integer aulaId);
}