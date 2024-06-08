package org.example.msmatricula.repository;

import org.example.msmatricula.entity.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Integer>{
    List<Matricula> findByCursosCursoId(Integer cursoId);
}
