package org.example.msmatricula.repository;

import org.example.msmatricula.entity.Aula;
import org.example.msmatricula.entity.Curso;
import org.example.msmatricula.entity.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Integer> {
    List<Aula> findByCurso(Curso curso);
    List<Aula> findByProfesor(Profesor profesor);
}