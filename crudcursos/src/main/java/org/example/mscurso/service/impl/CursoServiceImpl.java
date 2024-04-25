package org.example.mscurso.service.impl;

import org.example.mscurso.dto.ProfesoresDto;
import org.example.mscurso.entity.Curso;
import org.example.mscurso.feign.ProfesoresFeign;
import org.example.mscurso.repository.CursoRepository;
import org.example.mscurso.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ProfesoresFeign profesoresFeign;

    @Override
    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    @Override
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    public Curso buscarPorId(Integer id) {
        Optional<Curso> profesorOptional = cursoRepository.findById(id);
        if (profesorOptional.isPresent()) {
            Curso curso = profesorOptional.get();
            // Obtener información del curso asociado al profesor utilizando Feign
            try {
                ProfesoresDto profesorDto = profesoresFeign.listById(curso.getId()).getBody();
                if (profesorDto != null) {
                    // Almacenar temporalmente los datos del curso en cursoDto
                    curso.setProfesoresDto(profesorDto);
                }
            } catch (Exception e) {
                // Manejar el caso en el que no se pueda obtener la información del curso
            }
            return curso;
        }
        return null;
    }
    @Override
    public Curso editar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    public void eliminar(Integer id) {
        cursoRepository.deleteById(id);

    }
}
