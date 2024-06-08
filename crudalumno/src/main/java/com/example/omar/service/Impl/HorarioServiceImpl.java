package com.example.omar.service.Impl;

import com.example.omar.entity.Horario;
import com.example.omar.feign.CursoFeign;
import com.example.omar.repository.HorarioRepository;
import com.example.omar.service.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class HorarioServiceImpl implements HorarioService {
    @Autowired
    private HorarioRepository horarioRepository;
    @Autowired
    private CursoFeign cursoFeign;


    @Override
    public List<Horario> listar() {
        return horarioRepository.findAll();
    }

    @Override
    public Horario guardar(Horario horario) {
        if (isAulaAvailable(horario.getAulaId(), horario.getDiaSemana(), horario.getHoraInicio(), horario.getHoraFin()) &&
                isCursoValid(horario.getCursoId())) {
            return horarioRepository.save(horario);
        } else {
            throw new RuntimeException("El aula no está disponible en el horario seleccionado o el curso no es válido");
        }
    }

    @Override
    public Horario actualizar(Horario horario) {
        if (isAulaAvailable(horario.getAulaId(), horario.getDiaSemana(), horario.getHoraInicio(), horario.getHoraFin()) &&
                isCursoValid(horario.getCursoId())) {
            return horarioRepository.save(horario);
        } else {
            throw new RuntimeException("El aula no está disponible en el horario seleccionado o el curso no es válido");
        }
    }

    @Override
    public Optional<Horario> listarPorId(Integer id) {
        return horarioRepository.findById(id);
    }

    @Override
    public void eliminarPorId(Integer id) {
        horarioRepository.deleteById(id);
    }

    @Override
    public boolean isAulaAvailable(Integer aulaId, String diaSemana, String horaInicio, String horaFin) {
        List<Horario> horarios = horarioRepository.findByAulaIdAndDiaSemana(aulaId, diaSemana);
        for (Horario h : horarios) {
            if ((horaInicio.compareTo(h.getHoraInicio()) >= 0 && horaInicio.compareTo(h.getHoraFin()) < 0) ||
                    (horaFin.compareTo(h.getHoraInicio()) > 0 && horaFin.compareTo(h.getHoraFin()) <= 0)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isCursoValid(Integer cursoId) {
        try {
            cursoFeign.getCursoById(cursoId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Horario> listarPorCurso(Integer cursoId) {
        return horarioRepository.findByCursoId(cursoId);
    }

    @Override
    public List<Horario> listarPorGrado(Integer gradoId) {
        return horarioRepository.findByGradoId(gradoId);
    }
    @Override
    public List<Horario> listarPorAula(Integer aulaId) {
        return horarioRepository.findByAulaId(aulaId);
    }


}
