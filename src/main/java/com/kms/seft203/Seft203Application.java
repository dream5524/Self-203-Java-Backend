package com.kms.seft203;

import com.kms.seft203.entity.AppVersion;
import com.kms.seft203.repository.AppVersionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Seft203Application {

    public static void main(String[] args) {
        SpringApplication.run(Seft203Application.class, args);
    }

    @Bean
    CommandLineRunner runner(AppVersionRepository repo) {
        return args -> repo.save(new AppVersion(1L, "SEFT Program", "1.0.0"));
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS")
                        .allowedOrigins("*");
            }
        };
    }
}
