package org.example.mscurso.repository;

import org.example.mscurso.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudianteRepository extends JpaRepository<Curso, Integer> {
}
