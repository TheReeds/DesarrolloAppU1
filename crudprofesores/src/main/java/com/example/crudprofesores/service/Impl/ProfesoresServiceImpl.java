package com.example.crudprofesores.service.Impl;

import com.example.crudprofesores.dto.CursoDto;
import com.example.crudprofesores.dto.UserDto;
import com.example.crudprofesores.entity.Profesores;
import com.example.crudprofesores.feign.CursoFeign;
import com.example.crudprofesores.feign.UserFeign;
import com.example.crudprofesores.repository.ProfesoresRepository;
import com.example.crudprofesores.service.ProfesoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfesoresServiceImpl implements ProfesoresService {

    @Autowired
    private ProfesoresRepository profesoresRepository;

    @Autowired
    private CursoFeign cursoFeign;
    @Autowired
    public UserFeign userFeign;


    @Override
    public List<Profesores> listar() {
        List<Profesores> profesores = profesoresRepository.findAll();
        for (Profesores profesor : profesores) {
            if (profesor.getUsuarioId() != null) {
                ResponseEntity<UserDto> userDtoResponseEntity = userFeign.listById(profesor.getUsuarioId());
                if (userDtoResponseEntity.getStatusCode() == HttpStatus.OK) {
                    profesor.setUsuarioDto(userDtoResponseEntity.getBody());
                } else {
                    // Manejar el error si la solicitud no fue exitosa
                }
            }
        }
        return profesores;
    }
    @Override
    public Profesores guardar(Profesores profesores) {
        return profesoresRepository.save(profesores);
    }

    @Override
    public Optional<Profesores> buscarPorId(Integer id) {
        return profesoresRepository.findById(id);
    }

    @Override
    public Profesores editar(Profesores profesores) {
        return profesoresRepository.save(profesores);
    }

    @Override
    public void eliminar(Integer id) {
        profesoresRepository.deleteById(id);
    }
    @Override
    public List<Profesores> listarSinUsuario() {
        return profesoresRepository.findByUsuarioIdIsNull();
    }
}
