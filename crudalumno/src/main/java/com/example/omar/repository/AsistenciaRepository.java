package com.example.omar.repository;

import com.example.omar.entity.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Integer> {
    List<Asistencia> findByAlumnoId(Integer alumnoId);
    List<Asistencia> findByCursoId(Integer cursoId);
}