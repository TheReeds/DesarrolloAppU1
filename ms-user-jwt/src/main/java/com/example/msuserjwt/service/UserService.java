package com.example.msuserjwt.service;

import com.example.msuserjwt.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<User> listar();
    public User actualizar(User user);
    public Optional<User> listarPorId(Integer id);
    public User getUserById(Integer userId);

}
