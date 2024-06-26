package com.example.msuserjwt.controller;

import com.example.msuserjwt.config.JwtService;
import com.example.msuserjwt.controller.models.UpdateUserRequest;
import com.example.msuserjwt.dto.AlumnoDto;
import com.example.msuserjwt.dto.ProfesoresDto;
import com.example.msuserjwt.dto.RoleUpdateRequest;
import com.example.msuserjwt.dto.UserDto;
import com.example.msuserjwt.entity.Role;
import com.example.msuserjwt.entity.User;
import com.example.msuserjwt.feign.AlumnoFeign;
import com.example.msuserjwt.feign.ProfesorFeign;
import com.example.msuserjwt.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UserController {
    @Autowired
    private UserService userService;
    @Value("${upload.directory}")
    private String uploadDirectory;
    @Autowired
    private AlumnoFeign alumnoFeign;
    @Autowired
    private ProfesorFeign profesorFeign;
    @Autowired
    private JwtService jwtService;
    @GetMapping
    public ResponseEntity<List<UserDto>> list() {
        List<User> users = userService.listar();

        List<UserDto> userDtos = users.stream().map(user -> {
            UserDto userDto = new UserDto(user);
            if (user.getAlumnoId() != null) {
                AlumnoDto alumnoDto = alumnoFeign.listById(user.getAlumnoId()).getBody();
                userDto.setAlumnoDto(alumnoDto);
            }
            if (user.getProfesorId() != null) {
                ProfesoresDto profesorDto = profesorFeign.listById(user.getProfesorId()).getBody();
                userDto.setProfesorDto(profesorDto);
            }
            return userDto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok().body(userDtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> listById(@PathVariable Integer id) {
        User user = userService.listarPorId(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserDto userDto = new UserDto(user);
        if (user.getAlumnoId() != null) {
            AlumnoDto alumnoDto = alumnoFeign.listById(user.getAlumnoId()).getBody();
            userDto.setAlumnoDto(alumnoDto);
        }
        if (user.getProfesorId() != null) {
            ProfesoresDto profesorDto = profesorFeign.listById(user.getProfesorId()).getBody();
            userDto.setProfesorDto(profesorDto);
        }

        return ResponseEntity.ok().body(userDto);
    }

    @PostMapping("/{id}/profile-image")
    public ResponseEntity<User> uploadProfileImage(@PathVariable Integer id, @RequestParam("image") MultipartFile image) throws IOException {
        User user = userService.listarPorId(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Create the upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDirectory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Delete the old image if it exists
        if (user.getProfileImageUrl() != null && !user.getProfileImageUrl().isEmpty()) {
            Path oldImagePath = Paths.get(user.getProfileImageUrl());
            if (Files.exists(oldImagePath)) {
                Files.delete(oldImagePath);
            }
        }

        // Save the new image to the server
        String fileName = image.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Update the profile image URL of the user
        user.setProfileImageUrl(fileName);
        userService.actualizar(user);

        return ResponseEntity.ok(user);
    }
    @PutMapping("/{id}/asociar")
    public ResponseEntity<User> asociarAlumnoOProfesor(@PathVariable Integer id, @RequestParam(required = false) Integer alumnoId, @RequestParam(required = false) Integer profesorId) {
        if (alumnoId != null && profesorId != null) {
            return ResponseEntity.badRequest().body(null);
        }

        User user = userService.listarPorId(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        //EN CASO SEA UN ALUMNO A ASOCIAR
        if (alumnoId != null) {
            //MODELAR NUEVO ALUMNO
            AlumnoDto alumno = alumnoFeign.listById(alumnoId).getBody();
            //VERIFICAR SI HAY UN ALUMNO YA ASOCIADO Y ELIMINAR RELACIÓN EN CASO HAYA UNO
            if (user.getAlumnoId() != null){
                AlumnoDto alumnoExistente = alumnoFeign.listById(user.getAlumnoId()).getBody();
                alumnoExistente.setUsuarioId(null);
                alumnoFeign.updateAlumno(user.getAlumnoId(), alumnoExistente);
            }
            if (alumno == null) {
                return ResponseEntity.notFound().build();
            }
            //VERIFICAR SI HAY UN PROFESOR ASOCIADO Y ELIMINAR EN CASO HAYA UNO
            if(user.getProfesorId() != null){
                ProfesoresDto profesoresDto = profesorFeign.listById(user.getProfesorId()).getBody();
                profesoresDto.setUsuarioId(null);;
                profesorFeign.updateProfesor(user.getProfesorId(), profesoresDto);
            }
            //ACTUALIZAR NUEVO ALUMNO
            user.setAlumnoId(alumnoId);
            user.setProfesorId(null);

            //ACTUALIZAR RELACIÓN EN EL MICROSERVICIO ALUMNO POR ID
            alumno.setUsuarioId(user.getId());
            alumnoFeign.updateAlumno(alumnoId, alumno);

        }
        //LO MISMO DE ARRIBA PERO PARA PROFESORES
        if (profesorId != null) {
            ProfesoresDto profesor = profesorFeign.listById(profesorId).getBody();
            if (user.getProfesorId() != null){
                ProfesoresDto profesorExistente = profesorFeign.listById(user.getProfesorId()).getBody();
                profesorExistente.setUsuarioId(null);
                profesorFeign.updateProfesor(user.getProfesorId(), profesorExistente);
            }
            if (profesor == null) {
                return ResponseEntity.notFound().build();
            }
            if(user.getAlumnoId() != null){
                AlumnoDto alumno = alumnoFeign.listById(user.getAlumnoId()).getBody();
                alumno.setUsuarioId(null);;
                alumnoFeign.updateAlumno(user.getAlumnoId(), alumno);
            }
            user.setProfesorId(profesorId);
            user.setAlumnoId(null);

            // Update Profesor with User ID
            profesor.setUsuarioId(user.getId());
            profesorFeign.updateProfesor(profesorId, profesor);
        }
        //ACTUALIZAR NUEVOS DATOS DEL USUARIO
        User updatedUser = userService.actualizar(user);
        return ResponseEntity.ok(updatedUser);
    }
    @GetMapping("/info")
    public ResponseEntity<UserDto> getUserInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        Integer userId = jwtService.getUserId(token);
        return getUserDetailsResponse(userId);
    }
    private ResponseEntity<UserDto> getUserDetailsResponse(Integer userId) {
        User user = userService.listarPorId(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserDto userDto = new UserDto(user);
        if (user.getAlumnoId() != null) {
            AlumnoDto alumnoDto = alumnoFeign.listById(user.getAlumnoId()).getBody();
            userDto.setAlumnoDto(alumnoDto);
        }
        if (user.getProfesorId() != null) {
            ProfesoresDto profesorDto = profesorFeign.listById(user.getProfesorId()).getBody();
            userDto.setProfesorDto(profesorDto);
        }

        return ResponseEntity.ok().body(userDto);
    }
    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = Paths.get(uploadDirectory).resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }
    @PutMapping("/{id}/role")
    public ResponseEntity<User> updateRole(@PathVariable Integer id, @RequestBody RoleUpdateRequest roleUpdateRequest) {
        User user = userService.listarPorId(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setRole(roleUpdateRequest.getRole());
        User updatedUser = userService.actualizar(user);
        return ResponseEntity.ok(updatedUser);
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable(required = true) Integer id){
        User user = userService.listarPorId(id).orElse(null);
        if (user.getAlumnoId() != null){
            AlumnoDto alumno = alumnoFeign.listById(user.getAlumnoId()).getBody();
            alumno.setUsuarioId(null);
            alumnoFeign.updateAlumno(user.getAlumnoId(), alumno);

        }else {
            ProfesoresDto profesoresDto = profesorFeign.listById(user.getProfesorId()).getBody();
            profesoresDto.setUsuarioId(null);;
            profesorFeign.updateProfesor(user.getProfesorId(), profesoresDto);
        }

        userService.DeleteByID(id);
        return "Se el Usuario ELIMINO CORRECTAMENTE";
    }
    @GetMapping("/perfil")
    public ResponseEntity<UserDto> getUserProfile(HttpServletRequest request) {
        // Obtener el ID de usuario desde el token
        Integer userId = obtenerIdUsuarioDesdeToken(request);
        // Obtener detalles del usuario y devolverlos como UserDto
        return getUserDetailsResponse(userId);
    }

    @PutMapping("/perfil")
    public ResponseEntity<UserDto> updateUserProfile(@ModelAttribute UpdateUserRequest request, HttpServletRequest request2, @RequestParam("image") MultipartFile image) {
        // Obtener el ID de usuario desde el token
        Integer userId = obtenerIdUsuarioDesdeToken(request2);
        // Obtener detalles del usuario
        User user = userService.getUserById(userId);
        // Actualizar los datos del usuario con los valores del request
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        try {
            // Verificar si se cargó una nueva imagen
            if (!image.isEmpty()) {
                // Crear el directorio de carga si no existe
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
                user.setProfileImageUrl(fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Guardar los cambios en la base de datos
        userService.actualizar(user);
        // Devolver los detalles actualizados del usuario como UserDto
        return getUserDetailsResponse(userId);
    }

    private Integer obtenerIdUsuarioDesdeToken(HttpServletRequest request) {
        // Obtener el token del header
        String token = request.getHeader("Authorization").substring(7);
        // Obtener el ID de usuario desde el token utilizando el servicio JwtService
        Integer userId = jwtService.getUserId(token);
        return userId;
    }

}