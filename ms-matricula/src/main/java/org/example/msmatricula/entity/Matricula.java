package org.example.msmatricula.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.msmatricula.Dto.CursoDto;

@Entity
@Data
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int estudianteId;
    private int cursoId;
    private int fechaMatriculacion;

    @Transient
    private CursoDto cursoDto;
}
