package com.example.crudprofesores.service.Impl;
import com.example.crudprofesores.entity.Anuncio;
import com.example.crudprofesores.repository.AnuncioRepository;
import com.example.crudprofesores.service.AnuncioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class AnuncioServiceImpl implements AnuncioService {
    private final AnuncioRepository anuncioRepository;
    private final Path rootLocation = Paths.get("uploads");

    @Autowired
    public AnuncioServiceImpl(AnuncioRepository anuncioRepository) {
        this.anuncioRepository = anuncioRepository;
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }

    @Override
    public List<Anuncio> findAll() {
        return anuncioRepository.findAll();
    }

    @Override
    public Anuncio findById(Integer id) {
        return anuncioRepository.findById(id).orElse(null);
    }

    @Override
    public Anuncio save(String titulo, String descripcion, MultipartFile file) {
        String fileName = storeFile(file);
        Anuncio anuncio = new Anuncio();
        anuncio.setTitulo(titulo);
        anuncio.setDescripcion(descripcion);
        anuncio.setNombreImagen(fileName);
        anuncio.setFechaCreacion(LocalDateTime.now());
        return anuncioRepository.save(anuncio);
    }

    @Override
    public void deleteById(Integer id) {
        anuncioRepository.deleteById(id);
    }

    private String storeFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }
            String fileExtension = getFileExtension(file.getOriginalFilename());
            if (!isValidImageFile(fileExtension)) {
                throw new RuntimeException("Invalid file type. Only JPG, PNG and JPEG are allowed.");
            }
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    private boolean isValidImageFile(String fileExtension) {
        return "jpg".equalsIgnoreCase(fileExtension) ||
                "png".equalsIgnoreCase(fileExtension) ||
                "jpeg".equalsIgnoreCase(fileExtension);
    }
}