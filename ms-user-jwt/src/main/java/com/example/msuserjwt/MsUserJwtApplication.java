package com.example.msuserjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "*")
public class MsUserJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsUserJwtApplication.class, args);
	}

}
