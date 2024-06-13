package com.example.omar.service.Impl;

import com.example.omar.dto.UserDto;
import com.example.omar.entity.Alumno;
import com.example.omar.entity.Grado;
import com.example.omar.feign.UserFeign;
import com.example.omar.repository.AlumnoRepository;
import com.example.omar.repository.GradoRepository;
import com.example.omar.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class AlumnoServiceImpl implements AlumnoService {
    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    public UserFeign userFeign;
    @Autowired
    public GradoRepository gradoRepository;

    @Override
    public List<Alumno> listar() {
        List<Alumno> alumnos = alumnoRepository.findAll();
        for (Alumno alumno : alumnos) {
            if (alumno.getUsuarioId() != null) {
                ResponseEntity<UserDto> userDtoResponseEntity = userFeign.listById(alumno.getUsuarioId());
                if (userDtoResponseEntity.getStatusCode() == HttpStatus.OK) {
                    alumno.setUsuarioDto(userDtoResponseEntity.getBody());
                } else {
                    // Manejar el error si la solicitud no fue exitosa
                }
            }
        }
        return alumnos;
    }

    @Override
    public Alumno guardar(Alumno alumno) {
        // Validar el grado asociado
        if (alumno.getGrado() != null && alumno.getGrado().getId() != null) {
            Grado grado = gradoRepository.findById(alumno.getGrado().getId()).orElse(null);
            if (grado != null) {
                alumno.setGrado(grado);
            } else {
                // Manejar el caso en que el grado no existe
                throw new RuntimeException("El grado especificado no existe");
            }
        }
        return alumnoRepository.save(alumno);
    }

    @Override
    public Alumno actualizar(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    @Override
    public Optional<Alumno> listarPorId(Integer id) {
        return alumnoRepository.findById(id);
    }

    @Override
    public void eliminarPorId(Integer id) {
        alumnoRepository.deleteById(id);

    }
    @Override
    public List<Alumno> listarSinUsuario() {
        return alumnoRepository.findByUsuarioIdIsNull();
    }

}
