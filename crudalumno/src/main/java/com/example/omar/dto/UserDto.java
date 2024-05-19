package com.example.omar.dto;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private RoleDto role;
}
