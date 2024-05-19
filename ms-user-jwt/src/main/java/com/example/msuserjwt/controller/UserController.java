package com.example.msuserjwt.controller;

import com.example.msuserjwt.entity.User;
import com.example.msuserjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<List<User>> list(){
        return ResponseEntity.ok().body(userService.listar());
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> listById(@PathVariable(required = true) Integer id){
        return ResponseEntity.ok().body(userService.listarPorId(id).get());
    }
}
