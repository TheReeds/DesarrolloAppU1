package org.example.mscurso.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.example.mscurso.dto.ProfesoresDto;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    private String descripcion;

    private String duracion;

    private Integer ProfesorId;


    @Transient
    private ProfesoresDto profesoresDto;

    private Integer numeroDePeriodos = 3;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Nota> notas = new ArrayList<>();
}

