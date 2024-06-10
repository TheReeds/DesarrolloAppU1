package com.example.msuserjwt.controller.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Integer alumnoId; // Nuevo campo
    private Integer profesorId; // Nuevo campo
}
