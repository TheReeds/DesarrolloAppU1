package org.example.mscurso.dto;

import lombok.Data;

import java.util.List;

@Data
public class NotasRequestDto {
    private Integer cursoId;
    private List<NotaDetalleDto> notas;
    @Data
    public static class NotaDetalleDto {
        private Integer alumnoId;
        private Integer periodo;
        private Integer valor;
    }

}