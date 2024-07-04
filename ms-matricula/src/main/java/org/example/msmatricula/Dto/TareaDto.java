package org.example.msmatricula.Dto;

import lombok.Data;

@Data
public class TareaDto {
    private Integer id;
    private String nombre;
    private String archivoUrl;
    private Integer sesionId;
}