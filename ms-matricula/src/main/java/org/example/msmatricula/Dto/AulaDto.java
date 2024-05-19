package org.example.msmatricula.Dto;

import lombok.Data;

import java.util.List;
@Data
public class AulaDto {
    private String nombre;
    private Integer cursoId;
    private Integer profesorId;
    private List<Integer> alumnoIds;
}
