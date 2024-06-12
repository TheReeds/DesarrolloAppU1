package com.example.msuserjwt.dto;

import lombok.Data;

@Data
public class ProfesoresDto {
    private Integer id;
    private String nombre;
    private String dni;
    private String especialidad;
    private String telefono;
    private Integer usuarioId;

}