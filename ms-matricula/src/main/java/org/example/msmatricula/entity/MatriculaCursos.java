package org.example.msmatricula.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.msmatricula.Dto.CursoDto;

@Entity
@Data
public class MatriculaCursos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private Integer cursoId;
    @Transient
    CursoDto curso;

}
