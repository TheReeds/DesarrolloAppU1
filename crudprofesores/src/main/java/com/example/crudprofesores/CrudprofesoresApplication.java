package com.example.crudprofesores;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpMethod;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.http.HttpHeaders;

@SpringBootApplication
@EnableFeignClients
public class 	CrudprofesoresApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudprofesoresApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info()
				.title("OPEN API MICROSERVICIO PROFESORES")
				.version("0.0.1")
				.description("servicios web catálogo")
				.termsOfService("http:// swagger.io/terms")
				.license(new License().name("Apache 2.0").url("http://springdoc.org"))
		);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:4200")
						.allowedMethods(HttpMethod.GET.name(),
								HttpMethod.POST.name(),
								HttpMethod.DELETE.name())
						.allowedHeaders(HttpHeaders.CONTENT_TYPE,
								HttpHeaders.AUTHORIZATION);
			}
		};
	}
}

