package org.example.msmatricula.Dto;

import lombok.Data;

@Data
public class AulaConProfesorDto {
    private Integer aulaId;
    private String aulaNombre;
    private ProfesorDto profesor;
}
