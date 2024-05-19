package com.example.omar.service.Impl;

import com.example.omar.dto.UserDto;
import com.example.omar.entity.Alumno;
import com.example.omar.feign.UserFeign;
import com.example.omar.repository.AlumnoRepository;
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

    @Override
    public List<Alumno> listar() {
        List<Alumno> alumnos = alumnoRepository.findAll();
        for (Alumno alumno : alumnos){
            ResponseEntity<UserDto> userDtoResponseEntity = userFeign.listById(alumno.getUsuarioId());
            if (userDtoResponseEntity.getStatusCode() == HttpStatus.OK) {
                alumno.setUsuarioDto(userDtoResponseEntity.getBody());
            } else {
                // Manejar el error si la solicitud no fue exitosa
            }
        }
        return alumnos;
    }

    @Override
    public Alumno guardar(Alumno alumno) {
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
}
