package com.example.webshopspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.webshopspring.config.RsaProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaProperties.class)
public class WebshopspringApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebshopspringApplication.class, args);
    }
}

