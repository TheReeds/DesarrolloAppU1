package com.example.crudprofesores.service;

import com.example.crudprofesores.entity.Anuncio;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;



public interface AnuncioService {
    List<Anuncio> findAll();
    Anuncio findById(Integer id);
    Anuncio save(String titulo, String descripcion, MultipartFile file);
    void deleteById(Integer id);
    Anuncio update(Integer id, String titulo, String descripcion, MultipartFile file);
}