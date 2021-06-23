package com.kms.seft203;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Seft203Application {

	public static void main(String[] args) {
		SpringApplication.run(Seft203Application.class, args);
	}

	@Bean
	CommandLineRunner runner(AppVersionRepository repo) {
		return args -> repo.save(new AppVersion(1L, "SEFT Program", "1.0.0"));
	}
}
