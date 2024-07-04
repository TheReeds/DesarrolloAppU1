package org.example.msmatricula.service;

import org.example.msmatricula.Dto.TareaDto;
import org.example.msmatricula.entity.Tarea;

public interface TareaService {
    TareaDto createTarea(TareaDto tareaDto);

    TareaDto getTareaById(Integer id);

    void deleteTareaById(Integer id);
}