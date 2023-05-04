package com.example.vacancychecker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VacancyCheckerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VacancyCheckerApplication.class, args);
    }

}
