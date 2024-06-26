package com.example.omar.dto;

import lombok.Data;

@Data
public class CursoDto {
    private Integer id;

    private String nombre;

    private String descripcion;

    private String duracion;

    private Integer profesorId;

    private ProfesorDto profesoresDto;
}
