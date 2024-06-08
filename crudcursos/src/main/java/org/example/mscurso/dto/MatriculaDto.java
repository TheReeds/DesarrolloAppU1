package org.example.mscurso.dto;

import lombok.Data;

@Data
public class MatriculaDto {
    private Integer id;
    private Integer alumnoId;
    private CursoDto curso;

    @Data
    public static class CursoDto {
        private Integer id;
        private String nombre;
    }
}
