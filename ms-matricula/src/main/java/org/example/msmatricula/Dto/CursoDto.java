package org.example.msmatricula.Dto;

import lombok.Data;

@Data
public class CursoDto {
    private Long id;
    private int estudianteId;
    private int cursoId;
    private int fechaMatriculacion;
}
