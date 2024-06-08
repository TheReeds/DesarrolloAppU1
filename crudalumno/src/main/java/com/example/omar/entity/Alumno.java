package com.example.omar.entity;

import com.example.omar.dto.UserDto;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String dni;
    @ManyToOne
    private Grado grado;
    private int usuarioId;
    @Transient
    @MapsId
    @JoinColumn(name = "id_estudiante")
    private UserDto usuarioDto;
    public Alumno() {
        this.grado = new Grado();
        this.grado.setId(1);
    }
}
