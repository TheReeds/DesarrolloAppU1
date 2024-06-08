package com.example.omar.repository;

import com.example.omar.entity.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Integer> {
    List<Horario> findByGradoIdAndDiaSemana(Integer gradoId, String diaSemana);
    List<Horario> findByAulaIdAndDiaSemana(Integer aulaId, String diaSemana);
    List<Horario> findByCursoId(Integer cursoId);
    List<Horario> findByGradoId(Integer gradoId);
    List<Horario> findByAulaId(Integer aulaId);

}
