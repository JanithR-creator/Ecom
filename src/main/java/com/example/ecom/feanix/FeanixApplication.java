package com.example.ecom.feanix;

import com.example.ecom.feanix.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class FeanixApplication implements CommandLineRunner {
	private final UserRoleService userRoleService;

	public static void main(String[] args) {
		SpringApplication.run(FeanixApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userRoleService.initializeRoles();
	}
}
