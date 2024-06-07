package org.example.mscurso.dto;

import lombok.Data;

@Data
public class NotaDto {
    private Integer id;
    private Integer cursoId;
    private Integer alumnoId;
    private Integer periodo;
    private Integer valor;
}