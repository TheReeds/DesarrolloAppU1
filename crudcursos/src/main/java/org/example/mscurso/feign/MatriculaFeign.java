package org.example.mscurso.feign;

import org.example.mscurso.dto.AlumnoDto;
import org.example.mscurso.dto.MatriculaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-matricula-service", path = "/matriculas")
public interface MatriculaFeign {
    @GetMapping("/curso/{cursoId}")
    List<MatriculaDto> obtenerMatriculasPorCurso(@PathVariable Integer cursoId);
}
