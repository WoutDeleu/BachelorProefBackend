package com.example.BachelorProefBackend;

import com.example.BachelorProefBackend.UserManagement.FileStorage.FileStorageService;
import com.example.BachelorProefBackend.UserManagement.FileStorage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.example.BachelorProefBackend.UserManagement.FileStorage.StorageService;

import javax.annotation.Resource;

@SpringBootApplication
@RestController
@Configuration

public class BachelorProefBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BachelorProefBackendApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	// CORS configuration
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods("GET", "POST", "PUT", "DELETE")
						.allowedHeaders("*")
						.allowedOrigins("http://localhost:3000", "http://localhost:19000");
			}
		};
	}

	// logging
	@Bean
	public CommonsRequestLoggingFilter logFilter(){
		CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
		filter.setIncludeQueryString(true);
		filter.setIncludePayload(true);
		filter.setMaxPayloadLength(10000);
		filter.setIncludeHeaders(true);
		filter.setAfterMessagePrefix("REQUEST DATA: ");
		return filter;
	}

	// file upload
	@Resource
	FileStorageService storageService;
	@Bean
	public void run() throws Exception {
		storageService.deleteAll();
		storageService.init();
	}

}
