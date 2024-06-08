package org.example.msmatricula.feign;

import org.example.msmatricula.Dto.CursoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name= "ms-curso-service")
public interface CursoFeign {
    @GetMapping(value = "/cursos/{id}")
    ResponseEntity<CursoDto> listById(@PathVariable(required = true) Integer id);
    @GetMapping("/cursos")
    List<CursoDto> getAllCursos();
    @PostMapping("/notas/inicializar/{cursoId}/alumno/{alumnoId}")
    void inicializarNotasParaAlumnoEnCurso(@PathVariable Integer cursoId, @PathVariable Integer alumnoId);
}
