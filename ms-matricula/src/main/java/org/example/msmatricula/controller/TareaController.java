package org.example.msmatricula.controller;

import org.example.msmatricula.Dto.TareaDto;
import org.example.msmatricula.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @PostMapping
    public ResponseEntity<TareaDto> createTarea(@RequestBody TareaDto tareaDto) {
        TareaDto newTarea = tareaService.createTarea(tareaDto);
        return ResponseEntity.ok(newTarea);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareaDto> getTareaById(@PathVariable Integer id) {
        TareaDto tarea = tareaService.getTareaById(id);
        if (tarea != null) {
            return ResponseEntity.ok(tarea);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTareaById(@PathVariable Integer id) {
        tareaService.deleteTareaById(id);
        return ResponseEntity.noContent().build();
    }
}