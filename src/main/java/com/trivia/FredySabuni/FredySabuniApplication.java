package com.trivia.FredySabuni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FredySabuniApplication {

	public static void main(String[] args) {
		SpringApplication.run(FredySabuniApplication.class, args);
	}

}
