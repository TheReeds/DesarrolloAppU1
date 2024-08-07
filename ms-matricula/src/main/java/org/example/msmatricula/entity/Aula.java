package org.example.msmatricula.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    private Integer gradoId;

    @JoinColumn(name = "curso_id")
    private Integer cursoId;

    @JoinColumn(name = "profesor_id")
    private Integer profesorId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "aula_id")
    private List<AulaAlumnos> alumnos;


    public Aula() {}

    public Aula(Integer id) {
        this.id = id;
    }

}

