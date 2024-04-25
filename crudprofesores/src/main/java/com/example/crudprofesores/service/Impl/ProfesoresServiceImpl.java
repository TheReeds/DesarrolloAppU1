package com.example.crudprofesores.service.Impl;

import com.example.crudprofesores.dto.CursoDto;
import com.example.crudprofesores.entity.Profesores;
import com.example.crudprofesores.feign.CursoFeign;
import com.example.crudprofesores.repository.ProfesoresRepository;
import com.example.crudprofesores.service.ProfesoresService;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Override
    public List<Profesores> listar() {
        List<Profesores> profesores = profesoresRepository.findAll();
        return profesores.stream()
                .map(this::agregarInformacionCurso)
                .collect(Collectors.toList());
    }
    @Override
    public Profesores guardar(Profesores profesores) {
        return profesoresRepository.save(profesores);
    }

    @Override
    public Profesores buscarPorId(Integer id) {
        Optional<Profesores> profesorOptional = profesoresRepository.findById(id);
        if (profesorOptional.isPresent()) {
            Profesores profesor = profesorOptional.get();
            // Obtener información del curso asociado al profesor utilizando Feign
            try {
                CursoDto cursoDto = cursoFeign.buscarPorId(profesor.getId()).getBody();
                if (cursoDto != null) {
                    // Almacenar temporalmente los datos del curso en cursoDto
                    profesor.setCursoDto(cursoDto);
                }
            } catch (Exception e) {
                // Manejar el caso en el que no se pueda obtener la información del curso
            }
            return profesor;
        }
        return null;
    }
    private Profesores agregarInformacionCurso(Profesores profesor) {
        try {
            CursoDto cursoDto = cursoFeign.buscarPorId(profesor.getId()).getBody();
            if (cursoDto != null) {
                profesor.setCursoDto(cursoDto);
            }
        } catch (Exception e) {
            // Manejar el caso en el que no se pueda obtener la información del curso
        }
        return profesor;
    }

    @Override
    public Profesores editar(Profesores profesores) {
        return profesoresRepository.save(profesores);
    }

    @Override
    public void eliminar(Integer id) {
        profesoresRepository.deleteById(id);
    }
}
