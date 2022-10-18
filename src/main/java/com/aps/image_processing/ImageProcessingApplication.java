package com.aps.image_processing;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.aps.image_processing.domain.Role;
import com.aps.image_processing.domain.User;
import com.aps.image_processing.services.UserService;

@SpringBootApplication
public class ImageProcessingApplication {
	public static void main(String[] args) {
		SpringApplication.run(ImageProcessingApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new User(null, "Eder Lima", "eder", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Gustavo Marinho", "gustavo", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Matheus Farali", "matheus", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Marcus Vinícius", "marcus", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Jeferson Santos", "jeferson", "1234", new ArrayList<>()));

			userService.addRoleToUser("eder", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("eder", "ROLE_ADMIN");

			userService.addRoleToUser("gustavo", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("gustavo", "ROLE_ADMIN");

			userService.addRoleToUser("matheus", "ROLE_ADMIN");
			userService.addRoleToUser("matheus", "ROLE_MANAGER");

			userService.addRoleToUser("marcus", "ROLE_USER");
			userService.addRoleToUser("jeferson", "ROLE_USER");
		};
	}

}
