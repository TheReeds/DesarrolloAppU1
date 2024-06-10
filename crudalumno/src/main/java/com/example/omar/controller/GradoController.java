package com.example.omar.controller;

import com.example.omar.entity.Alumno;
import com.example.omar.entity.Grado;
import com.example.omar.service.AlumnoService;
import com.example.omar.service.GradoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/grado")
public class GradoController {
    @Autowired
    private GradoService gradoService;
    @GetMapping()
    public ResponseEntity<List<Grado>> list(){
        return ResponseEntity.ok().body(gradoService.listar());
    }
    @PostMapping()
    public ResponseEntity<Grado> save(@RequestBody Grado grado){
        return  ResponseEntity.ok(gradoService.guardar(grado));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Grado> update(@PathVariable Integer id, @RequestBody Grado grado) {
        Optional<Grado> optionalGrado = gradoService.listarPorId(id);
        if (!optionalGrado.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        grado.setId(id);  // Establecer el ID del grado a actualizar
        Grado gradoActualizado = gradoService.actualizar(grado);
        return ResponseEntity.ok(gradoActualizado);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Grado> listById(@PathVariable(required = true) Integer id){
        return ResponseEntity.ok().body(gradoService.listarPorId(id).get());
    }
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable(required = true) Integer id){
        gradoService.eliminarPorId(id);
        return "SE ELIMINO CORRECTAMENTE";
    }
}
