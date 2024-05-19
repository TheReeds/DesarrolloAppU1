package org.example.msmatricula.service;

import org.example.msmatricula.Dto.AulaDto;
import org.example.msmatricula.Dto.CursoDto;
import org.example.msmatricula.entity.Aula;

import java.util.List;

public interface AulaService {
    public Aula createAula(AulaDto aulaDto);

    public List<Aula> getAllAulas();

    public Aula getAulaById(Integer id);
    void deleteAulaById(Integer id);

}