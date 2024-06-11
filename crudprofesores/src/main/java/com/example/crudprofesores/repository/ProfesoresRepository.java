package com.example.crudprofesores.repository;

import com.example.crudprofesores.entity.Profesores;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfesoresRepository extends JpaRepository<Profesores, Integer> {
    List<Profesores> findByUsuarioIdIsNull();
}
