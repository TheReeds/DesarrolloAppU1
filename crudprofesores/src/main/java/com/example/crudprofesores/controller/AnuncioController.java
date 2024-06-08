package com.example.crudprofesores.controller;

import com.example.crudprofesores.entity.Anuncio;
import com.example.crudprofesores.service.AnuncioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/anuncios")
public class AnuncioController {
    private final AnuncioService anuncioService;

    @Autowired
    public AnuncioController(AnuncioService anuncioService) {
        this.anuncioService = anuncioService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Anuncio> createAnuncio(
            @RequestParam("titulo") String titulo,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("file") MultipartFile file) {

        Anuncio savedAnuncio = anuncioService.save(titulo, descripcion, file);
        return ResponseEntity.ok(savedAnuncio);
    }

    @GetMapping
    public ResponseEntity<List<Anuncio>> getAllAnuncios() {
        List<Anuncio> anuncios = anuncioService.findAll();
        return ResponseEntity.ok(anuncios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anuncio> getAnuncioById(@PathVariable Integer id) {
        Anuncio anuncio = anuncioService.findById(id);
        if (anuncio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(anuncio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnuncio(@PathVariable Integer id) {
        anuncioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
