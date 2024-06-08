package com.example.omar.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Entity
@Data
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String  horaInicio;
    private String  horaFin;
    private String diaSemana;

    @ManyToOne
    @JoinColumn(name = "grado_id")
    @JsonBackReference
    private Grado grado;

    private Integer aulaId; // Referencia al ID del Aula en el microservicio de matrícula

    private Integer cursoId;
}