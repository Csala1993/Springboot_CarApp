package com.carapp.carmaintenance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CarMaintenaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarMaintenaceApplication.class, args);
    }

}

