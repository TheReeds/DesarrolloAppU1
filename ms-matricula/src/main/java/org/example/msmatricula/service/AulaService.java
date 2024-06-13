package org.example.msmatricula.service;

import org.example.msmatricula.Dto.AulaConProfesorDto;
import org.example.msmatricula.Dto.AulaDto;
import org.example.msmatricula.Dto.CursoDto;
import org.example.msmatricula.entity.Aula;

import java.util.List;

public interface AulaService {
    public Aula createAula(AulaDto aulaDto);

    public List<AulaDto> getAllAulas();

    public AulaDto getAulaById(Integer id);
    public void deleteAulaById(Integer id);
    List<AulaConProfesorDto> getAulasByAlumnoId(Integer alumnoId);

}