package com.example.omar.service.Impl;

import com.example.omar.entity.Asistencia;
import com.example.omar.repository.AsistenciaRepository;
import com.example.omar.service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AsistenciaServiceImpl implements AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Override
    public Asistencia registrarAsistencia(Asistencia asistencia) {
        return asistenciaRepository.save(asistencia);
    }

    @Override
    public List<Asistencia> obtenerAsistenciasPorAlumnoId(Integer alumnoId) {
        return asistenciaRepository.findByAlumnoId(alumnoId);
    }

    @Override
    public List<Asistencia> obtenerTodasLasAsistencias() {
        return asistenciaRepository.findAll();
    }
}
