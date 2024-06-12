package com.example.msuserjwt.dto;

import com.example.msuserjwt.entity.Role;
import lombok.Data;

@Data
public class RoleUpdateRequest {
    private Role role;

    // Getters and setters
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}