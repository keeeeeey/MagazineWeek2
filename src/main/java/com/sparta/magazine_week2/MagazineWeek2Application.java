package com.sparta.magazine_week2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableJpaAuditing
@SpringBootApplication
public class MagazineWeek2Application {

    public static void main(String[] args) {
        SpringApplication.run(MagazineWeek2Application.class, args);
    }

}
