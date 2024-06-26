package org.example.msmatricula.Dto;

import lombok.Data;

import java.util.List;
@Data
public class AulaDto {
    private Integer id;
    private String nombre;
    private GradoDto grado;
    private CursoDto curso;
    private ProfesorDto profesor;
    private List<AlumnoDto> alumnos;
}
