package com.example.crudprofesores.service.Impl;

import com.example.crudprofesores.dto.CursoDto;
import com.example.crudprofesores.entity.Profesores;
import com.example.crudprofesores.feign.CursoFeign;
import com.example.crudprofesores.repository.ProfesoresRepository;
import com.example.crudprofesores.service.ProfesoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesoresServiceImpl implements ProfesoresService {

    @Autowired
    private ProfesoresRepository profesoresRepository;
    @Autowired
    private CursoFeign cursoFeign;

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
        Profesores profesor = profesoresRepository.findById(id).orElse(null);
        if (profesor != null) {
            // Obtener información del curso asociado al profesor utilizando Feign
            CursoDto cursoDto = obtenerCursoDeProfesor(profesor.getId());
            // Asignar el cursoDto al profesor si existe
            if (cursoDto != null) {
                profesor.setCursoDto(cursoDto);
            }
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

    private CursoDto obtenerCursoDeProfesor(Integer profesorId) {
        try {
            return cursoFeign.buscarPorId(profesorId).getBody();
        } catch (Exception e) {
            // Manejar el caso en el que no se pueda obtener la información del curso
            return null;
        }
    }
}
