package com.example.omar.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AsistenciaDto {
    private Integer id;
    private Integer alumnoId;
    private LocalDateTime fecha;
    private Boolean presente;
}