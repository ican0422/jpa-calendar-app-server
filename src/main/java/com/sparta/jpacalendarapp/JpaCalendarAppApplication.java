package com.sparta.jpacalendarapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JpaCalendarAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaCalendarAppApplication.class, args);
	}

}
