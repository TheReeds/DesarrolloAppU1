package com.example.msuserjwt;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@CrossOrigin(origins = "*")
public class MsUserJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsUserJwtApplication.class, args);
	}

	@Bean
	public OpenAPI custumOpenAPI(){
		return new OpenAPI().info (new Info()
				.title("OPEN API MICROSERVICIO JWT")
				.version("0.0.1")
				.description("servicio web cursos")
				.termsOfService("http://swagger.io/terms")
				.license (new License().name("Apache 2.0").url("http://springdoc.org"))
		);
	}

}
