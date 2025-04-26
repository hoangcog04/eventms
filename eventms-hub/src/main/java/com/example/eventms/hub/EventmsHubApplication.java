package com.example.eventms.hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(com.example.eventms.security.config.SecurityConfig.class)
public class EventmsHubApplication {
    public static void main(String[] args) {
        SpringApplication.run(EventmsHubApplication.class, args);
    }
}