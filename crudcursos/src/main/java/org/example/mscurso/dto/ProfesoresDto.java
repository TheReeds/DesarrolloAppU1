package org.example.mscurso.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class ProfesoresDto {
    private Integer id;
    private String nombre;
    private String dni;
    private String especialidad;
    private String telefono;

}