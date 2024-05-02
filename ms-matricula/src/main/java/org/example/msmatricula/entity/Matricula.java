package org.example.msmatricula.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.msmatricula.Dto.AlumnoDto;
import org.example.msmatricula.Dto.CursoDto;

@Entity
@Data
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int estudianteId;
    private int cursoId;
    private int fechaMatriculacion;
    private int alumnoId;

    @Transient
    private CursoDto cursoDto;
    @Transient
    private AlumnoDto alumnoDto;
}
