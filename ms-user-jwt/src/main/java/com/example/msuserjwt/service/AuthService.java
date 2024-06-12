package com.example.msuserjwt.service;

import com.example.msuserjwt.controller.models.AuthResponse;
import com.example.msuserjwt.controller.models.AuthenticationRequest;
import com.example.msuserjwt.controller.models.RegisterRequest;

public interface AuthService {
    AuthResponse register (RegisterRequest request);
    AuthResponse authenticate (AuthenticationRequest request);
    public boolean validateToken(String token);
}
