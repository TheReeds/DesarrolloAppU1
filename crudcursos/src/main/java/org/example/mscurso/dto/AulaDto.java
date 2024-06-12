package org.example.mscurso.dto;

import lombok.Data;

import java.util.List;

@Data
public class AulaDto {
    private Integer id;
    private String nombre;
    private GradoDto grado;
    private CursoDto curso;
    private ProfesoresDto profesor;
    private List<AlumnoDto> alumnos;
}
