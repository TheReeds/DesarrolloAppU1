package org.example.msmatricula.service.impl;
import org.example.msmatricula.Dto.TareaDto;
import org.example.msmatricula.entity.Sesion;
import org.example.msmatricula.entity.Tarea;
import org.example.msmatricula.repository.TareaRepository;
import org.example.msmatricula.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TareaServiceImpl implements TareaService {
    @Autowired
    private TareaRepository tareaRepository;

    @Override
    public TareaDto createTarea(TareaDto tareaDto) {
        Tarea tarea = new Tarea();
        tarea.setNombre(tareaDto.getNombre());
        tarea.setArchivoUrl(tareaDto.getArchivoUrl());
        tarea.setSesion(new Sesion(tareaDto.getSesionId()));
        Tarea savedTarea = tareaRepository.save(tarea);
        return convertToDto(savedTarea);
    }

    @Override
    public TareaDto getTareaById(Integer id) {
        return tareaRepository.findById(id).map(this::convertToDto).orElse(null);
    }

    @Override
    public void deleteTareaById(Integer id) {
        tareaRepository.deleteById(id);
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