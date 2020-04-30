package com.webAvalon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@SpringBootApplication
public class AvalonBackendApplication {
    public static void main(String[] args) {
        System.out.println("app starts!!!!!");
        SpringApplication.run(AvalonBackendApplication.class, args);
    }
}
