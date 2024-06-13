package com.example.omar.service.Impl;

import com.example.omar.dto.AsistenciaDto;
import com.example.omar.dto.CursoDto;
import com.example.omar.entity.Asistencia;
import com.example.omar.feign.CursoFeign;
import com.example.omar.repository.AlumnoRepository;
import com.example.omar.repository.AsistenciaRepository;
import com.example.omar.service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsistenciaServiceImpl implements AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private CursoFeign cursoFeign;

    @Override
    public Asistencia registrarAsistencia(Asistencia asistencia) {
        return asistenciaRepository.save(asistencia);
    }

    @Override
    public List<Asistencia> registrarVariasAsistencias(List<AsistenciaDto> asistencias) {
        List<Asistencia> entidadesAsistencia = asistencias.stream().map(asistenciaDto -> {
            // Traer el curso usando Feign
            CursoDto cursoDto = cursoFeign.getCursoById(asistenciaDto.getCursoId()).getBody();

            // Mapear el DTO a la entidad
            Asistencia asistencia = new Asistencia();
            asistencia.setAlumnoId(asistenciaDto.getAlumnoId());
            asistencia.setCursoId(cursoDto.getId());
            asistencia.setFecha(asistenciaDto.getFecha());
            asistencia.setPresente(asistenciaDto.getPresente());

            return asistencia;
        }).collect(Collectors.toList());

        return asistenciaRepository.saveAll(entidadesAsistencia);
    }

    @Override
    public List<Asistencia> obtenerAsistenciasPorAlumnoId(Integer alumnoId) {
        return asistenciaRepository.findByAlumnoId(alumnoId);
    }

    @Override
    public List<Asistencia> obtenerAsistenciasPorCursoId(Integer cursoId) {
        return asistenciaRepository.findByCursoId(cursoId);
    }

    @Override
    public List<Asistencia> obtenerTodasLasAsistencias() {
        return asistenciaRepository.findAll();
    }
}