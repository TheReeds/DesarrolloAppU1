package org.example.mscurso.repository;

import org.example.mscurso.entity.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotaRepository extends JpaRepository<Nota, Integer> {
    List<Nota> findByCursoId(Integer cursoId);
    List<Nota> findByAlumnoId(Integer alumnoId);
    List<Nota> findByCursoIdAndAlumnoId(Integer cursoId, Integer alumnoId);
    Optional<Nota> findByCursoIdAndAlumnoIdAndPeriodo(Integer cursoId, Integer alumnoId, Integer periodo);
}