package org.example.msmatricula.repository;

import org.example.msmatricula.entity.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Integer> {
    List<Sesion> findByAulaId(Integer aulaId);
}