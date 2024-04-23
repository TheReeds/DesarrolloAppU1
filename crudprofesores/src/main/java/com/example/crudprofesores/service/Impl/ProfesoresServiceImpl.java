package com.example.crudprofesores.service.Impl;

import com.example.crudprofesores.entity.Profesores;
import com.example.crudprofesores.repository.ProfesoresRepository;
import com.example.crudprofesores.service.ProfesoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesoresServiceImpl implements ProfesoresService {

    @Autowired
    private ProfesoresRepository profesoresRepository;

    @Override
    public List<Profesores> listar(){
        return profesoresRepository.findAll();
    }
    @Override
    public Profesores guardar(Profesores profesores) {
        return profesoresRepository.save(profesores);
    }

    @Override
    public Profesores buscarPorId(Integer id) {
        return profesoresRepository.findById(id).get();
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
