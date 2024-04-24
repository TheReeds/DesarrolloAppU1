package com.example.msuserjwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/greeting")
public class GreetingsController {
    @GetMapping("/sayHelloPublic")
    public String sayHello(){
        return "Hello andradeassa";
    }
    @GetMapping("/sayHelloProtected")
    public String sayHelloProtected(){
        return "Hello from api protected";
    }
}
