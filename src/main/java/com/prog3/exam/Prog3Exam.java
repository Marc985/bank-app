package com.prog3.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class Prog3Exam {

    public static void main(String[] args) {
        SpringApplication.run(Prog3Exam.class, args);
    }

}
