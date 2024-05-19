package org.example.msmatricula.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.msmatricula.Dto.AlumnoDto;
import org.example.msmatricula.Dto.CursoDto;
@Data
@Entity
public class AulaAlumnos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private Integer alumnoId;
    @Transient
    AlumnoDto alumno;
}
