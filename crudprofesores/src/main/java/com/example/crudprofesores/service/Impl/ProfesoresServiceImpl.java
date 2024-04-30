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
        return profesoresRepository.findAll();
    }
    @Override
    public Profesores guardar(Profesores profesores) {
        return profesoresRepository.save(profesores);
    }

    @Override
    public Profesores buscarPorId(Integer id) {
        Profesores profesores = profesoresRepository.findById(id).get();
        return profesores;
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
