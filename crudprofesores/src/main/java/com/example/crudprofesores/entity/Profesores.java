package com.example.crudprofesores.entity;

import com.example.crudprofesores.dto.CursoDto;
import com.example.crudprofesores.dto.UserDto;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Profesores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String dni;
    private String especialidad;
    private String telefono;
    @Column(nullable = true)
    private Integer usuarioId;

    @Transient
    @MapsId
    @JoinColumn(name = "id_profesor")
    private UserDto usuarioDto;
}
