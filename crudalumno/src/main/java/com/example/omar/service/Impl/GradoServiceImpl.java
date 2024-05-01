package com.example.omar.service.Impl;

import com.example.omar.entity.Grado;
import com.example.omar.repository.GradoRepository;
import com.example.omar.service.GradoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class GradoServiceImpl implements GradoService {
    @Autowired
    private GradoRepository gradoRepository;

    @Override
    public List<Grado> listar() {
        return gradoRepository.findAll();
    }

    @Override
    public Grado guardar(Grado grado) {
        return gradoRepository.save(grado);
    }

    @Override
    public Grado actualizar(Grado grado) {
        return gradoRepository.save(grado);
    }

    @Override
    public Optional<Grado> listarPorId(Integer id) {
        return gradoRepository.findById(id);
    }

    @Override
    public void eliminarPorId(Integer id) {
        gradoRepository.deleteById(id);

    }
}
