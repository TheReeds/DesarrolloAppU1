package com.example.omar.dto;

import lombok.Data;

import java.util.List;

@Data
public class AsistenciasRequestDto {
    private List<AsistenciaDto> asistencias;
}