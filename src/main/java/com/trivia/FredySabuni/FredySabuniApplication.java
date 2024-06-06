package com.trivia.FredySabuni;

import com.trivia.FredySabuni.service.GameSessionService;
import com.trivia.FredySabuni.service.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = "com.trivia.FredySabuni.repository")
@EntityScan(basePackages = "com.trivia.FredySabuni.model")
//implements CommandLineRunner
public class FredySabuniApplication{


	@Autowired
	private GameSessionService gameSessionService;

	public static void main(String[] args) {
		SpringApplication.run(FredySabuniApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		String message = "We are lumberjacks. We sleep all day and code all night";
//		String sender = "255714276333"; // or "ABCDE"
//
//		gameSessionService.startNewGame(sender);
//	}

}
