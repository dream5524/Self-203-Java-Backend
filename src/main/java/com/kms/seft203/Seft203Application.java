package com.kms.seft203;

import com.kms.seft203.entity.AppVersion;
import com.kms.seft203.entity.RoleSecurity;
import com.kms.seft203.entity.UserSecurity;
import com.kms.seft203.repository.AppVersionRepository;
import com.kms.seft203.service.UserSecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;

@SpringBootApplication
public class Seft203Application {

    public static void main(String[] args) {
        SpringApplication.run(Seft203Application.class, args);
    }

    @Bean
    CommandLineRunner runner(AppVersionRepository repo) {

        return args -> {
            repo.save(new AppVersion(1L, "SEFT Program", "1.0.0"));

//            userSecurityService.saveRole(new RoleSecurity(1, "ROLE_ADMIN"));
//            userSecurityService.saveRole(new RoleSecurity(2, "ROLE_USER"));
//
//            userSecurityService.saveUser(new UserSecurity(1, "loc", "loc", "1", new ArrayList<>()));
//            userSecurityService.saveUser(new UserSecurity(1, "mo", "mo", "1", new ArrayList<>()));
//
//            userSecurityService.addRoleToUser("loc", "ROLE_ADMIN");
//            userSecurityService.addRoleToUser("mo", "ROLE_USER");
        };
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

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
