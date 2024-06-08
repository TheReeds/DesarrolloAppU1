package com.example.omar.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Grado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String turno = "mañana";
    private String nivel = "primaria";
    @OneToMany(mappedBy = "grado")
    @JsonManagedReference
    private List<Horario> horarios;
}
