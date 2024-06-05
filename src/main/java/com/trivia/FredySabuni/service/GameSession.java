package com.trivia.FredySabuni.service;

import com.trivia.FredySabuni.config.GameProperties;
import com.trivia.FredySabuni.repository.GameSessionRepository;
import com.trivia.FredySabuni.repository.PlayerRepository;
import com.trivia.FredySabuni.repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class GameSession {
    private final PlayerRepository playerRepository;
    private final QuestionRepository questionRepository;
    private final GameSessionRepository gameSessionRepository;
    private final SMSService smsService;
    private final GameProperties gameProperties;


}
