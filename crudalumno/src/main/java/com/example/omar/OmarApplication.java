package com.example.omar;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class OmarApplication {

    public static void main(String[] args) {
        SpringApplication.run(OmarApplication.class, args);
    }
    @Bean
    public OpenAPI custumOpenAPI(){
        return new OpenAPI().info (new Info()
                .title("OPEN API MICROSERVICIO ALUMNOS")
                .version("0.0.1")
                .description("servicio web alumnos")
                .termsOfService("http://swagger.io/terms")
                .license (new License().name("Apache 2.0").url("http://springdoc.org"))
        );
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200");
            }
        };
    }

}
