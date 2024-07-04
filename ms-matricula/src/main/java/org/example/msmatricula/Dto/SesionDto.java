package org.example.msmatricula.Dto;

import lombok.Data;

import java.util.List;

@Data
public class SesionDto {
    private Integer id;
    private String nombre;
    private String titulo;
    private String descripcion;
    private Integer aulaId;
    private List<TareaDto> tareas;
}