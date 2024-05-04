package com.example.omar.entity;

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
    private String notas;
    @ManyToOne
    private Grado grado;
    public Alumno() {
        this.grado = new Grado();
        this.grado.setId(1);
    }
}
