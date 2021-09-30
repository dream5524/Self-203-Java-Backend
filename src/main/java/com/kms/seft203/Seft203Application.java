package com.kms.seft203;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Seft203Application {
    public static void main(String[] args) {
        SpringApplication.run(Seft203Application.class, args);
    }
}
