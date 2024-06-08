package com.example.msuserjwt.controller;

import com.example.msuserjwt.entity.User;
import com.example.msuserjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UserController {
    @Autowired
    private UserService userService;
    @Value("${upload.directory}")
    private String uploadDirectory;

    @GetMapping
    public ResponseEntity<List<User>> list() {
        return ResponseEntity.ok().body(userService.listar());
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> listById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(userService.listarPorId(id).orElse(null));
    }

    @PostMapping("/{id}/profile-image")
    public ResponseEntity<User> uploadProfileImage(@PathVariable Integer id, @RequestParam("image") MultipartFile image) throws IOException {
        User user = userService.listarPorId(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Crear el directorio si no existe
        Path uploadPath = Paths.get(uploadDirectory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Eliminar la imagen anterior si existe
        if (user.getProfileImageUrl() != null && !user.getProfileImageUrl().isEmpty()) {
            Path oldImagePath = Paths.get(user.getProfileImageUrl());
            if (Files.exists(oldImagePath)) {
                Files.delete(oldImagePath);
            }
        }

        // Guardar la nueva imagen en el servidor
        String fileName = image.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Actualizar la URL de la imagen de perfil del usuario
        user.setProfileImageUrl(filePath.toString());
        userService.actualizar(user);

        return ResponseEntity.ok(user);
    }
}