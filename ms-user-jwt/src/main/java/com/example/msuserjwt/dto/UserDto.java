package com.example.msuserjwt.dto;

import com.example.msuserjwt.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String name;
    private String email;
    private String role;
    private String profileImageUrl;
    private Integer alumnoId;
    private Integer profesorId;
    private AlumnoDto alumnoDto;
    private ProfesoresDto profesorDto;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole().name();
        this.profileImageUrl = user.getProfileImageUrl();
        this.alumnoId = user.getAlumnoId();
        this.profesorId = user.getProfesorId();
    }
}