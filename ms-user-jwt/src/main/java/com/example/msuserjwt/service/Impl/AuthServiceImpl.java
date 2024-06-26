package com.example.msuserjwt.service.Impl;

import com.example.msuserjwt.config.JwtService;
import com.example.msuserjwt.controller.models.AuthResponse;
import com.example.msuserjwt.controller.models.AuthenticationRequest;
import com.example.msuserjwt.controller.models.RegisterRequest;
import com.example.msuserjwt.entity.Role;
import com.example.msuserjwt.entity.User;
import com.example.msuserjwt.repository.UserRepository;
import com.example.msuserjwt.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Value("${default.profile.image.url}")
    private String defaultProfileImageUrl;

    @Override
    public AuthResponse register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .profileImageUrl(defaultProfileImageUrl) // Asignar imagen por defecto
                .alumnoId(request.getAlumnoId())
                .profesorId(request.getProfesorId())
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user, user.getId());
        return AuthResponse.builder()
                .token(jwtToken).build();
    }

    @Override
    public AuthResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findUserByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user, user.getId());
        return AuthResponse.builder()
                .token(jwtToken).build();
    }
    @Override
    public boolean validateToken(String token) {
        try {
            String username = jwtService.getUserName(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return jwtService.validateToken(token, userDetails);
        } catch (Exception e) {
            return false;
        }
    }

}
