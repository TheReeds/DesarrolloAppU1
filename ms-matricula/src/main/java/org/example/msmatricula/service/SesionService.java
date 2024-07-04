package org.example.msmatricula.service;

import org.example.msmatricula.Dto.SesionDto;
import org.example.msmatricula.entity.Sesion;

import java.util.List;

public interface SesionService {
    SesionDto createSesion(SesionDto sesionDto) ;

    List<SesionDto> getAllSesionesByAulaId(Integer aulaId);

    SesionDto getSesionById(Integer id);

    void deleteSesionById(Integer id);
}