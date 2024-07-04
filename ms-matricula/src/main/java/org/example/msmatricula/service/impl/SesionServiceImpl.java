package org.example.msmatricula.service.impl;

import org.example.msmatricula.Dto.SesionDto;
import org.example.msmatricula.Dto.TareaDto;
import org.example.msmatricula.entity.Aula;
import org.example.msmatricula.entity.Sesion;
import org.example.msmatricula.entity.Tarea;
import org.example.msmatricula.repository.AulaRepository;
import org.example.msmatricula.repository.SesionRepository;
import org.example.msmatricula.service.SesionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SesionServiceImpl implements SesionService {
    @Autowired
    private SesionRepository sesionRepository;
    @Autowired
    private AulaRepository aulaRepository;


    @Override
    public SesionDto createSesion(SesionDto sesionDto) {
        // Verificar si el aulaId existe en la base de datos
        Optional<Aula> aulaOptional = aulaRepository.findById(sesionDto.getAulaId());
        if (!aulaOptional.isPresent()) {
            throw new RuntimeException("Aula con id " + sesionDto.getAulaId() + " no existe.");
        }

        // Crear una nueva entidad Sesion
        Sesion sesion = new Sesion();
        sesion.setNombre(sesionDto.getNombre());
        sesion.setTitulo(sesionDto.getTitulo());
        sesion.setDescripcion(sesionDto.getDescripcion());
        sesion.setAula(aulaOptional.get());

        // Guardar la entidad Sesion
        Sesion savedSesion = sesionRepository.save(sesion);

        // Convertir la entidad guardada a DTO y devolverla
        return convertToDto(savedSesion);
    }

    @Override
    public List<SesionDto> getAllSesionesByAulaId(Integer aulaId) {
        return sesionRepository.findByAulaId(aulaId).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public SesionDto getSesionById(Integer id) {
        return sesionRepository.findById(id).map(this::convertToDto).orElse(null);
    }

    @Override
    public void deleteSesionById(Integer id) {
        sesionRepository.deleteById(id);
    }

    private SesionDto convertToDto(Sesion sesion) {
        SesionDto dto = new SesionDto();
        dto.setId(sesion.getId());
        dto.setNombre(sesion.getNombre());
        dto.setTitulo(sesion.getTitulo());
        dto.setDescripcion(sesion.getDescripcion());
        dto.setAulaId(sesion.getAula().getId());
        dto.setTareas(sesion.getTareas().stream().map(this::convertToDto).collect(Collectors.toList()));
        return dto;
    }

    private TareaDto convertToDto(Tarea tarea) {
        TareaDto dto = new TareaDto();
        dto.setId(tarea.getId());
        dto.setNombre(tarea.getNombre());
        dto.setArchivoUrl(tarea.getArchivoUrl());
        dto.setSesionId(tarea.getSesion().getId());
        return dto;
    }
}