package com.example.crudprofesores.repository;

import com.example.crudprofesores.entity.Profesores;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesoresRepository extends JpaRepository<Profesores, Integer> {
}
