package com.example.msuserjwt.service.Impl;

import com.example.msuserjwt.entity.User;
import com.example.msuserjwt.repository.UserRepository;
import com.example.msuserjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> listar() {
        return userRepository.findAll();
    }

    @Override
    public User actualizar(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> listarPorId(Integer id) {
        return userRepository.findById(id);
    }
}
