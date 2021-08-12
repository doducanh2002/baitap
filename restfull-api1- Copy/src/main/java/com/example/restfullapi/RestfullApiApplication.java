package com.example.restfullapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RestfullApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestfullApiApplication.class, args);
    }

}
