package org.example.mscurso.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Nota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    private Integer alumnoId;
    private Integer periodo;
    private Integer valor;

    public Nota() {
        this.valor = 0; // Valor por defecto
    }
}
