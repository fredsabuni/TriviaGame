package com.trivia.FredySabuni.service;

import com.trivia.FredySabuni.config.GameProperties;
import com.trivia.FredySabuni.model.*;
import com.trivia.FredySabuni.repository.GameSessionRepository;
import com.trivia.FredySabuni.repository.PlayerRepository;
import com.trivia.FredySabuni.repository.PlayerSubscriptionRepository;
import com.trivia.FredySabuni.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GameSessionService {
    private static final Logger log = LoggerFactory.getLogger(GameSessionService.class);
    private final PlayerRepository playerRepository;
    private final PlayerSubscriptionRepository playerSubscriptionRepository;
    private final QuestionRepository questionRepository;
    private final GameSessionRepository gameSessionRepository;
    private final SMSService smsService;
    private final GameProperties gameProperties;

    public GameSessionService(PlayerRepository playerRepository, PlayerSubscriptionRepository playerSubscriptionRepository, QuestionRepository questionRepository,
                              GameSessionRepository gameSessionRepository, SMSService smsService, GameProperties gameProperties) {

        this.playerRepository = playerRepository;
        this.playerSubscriptionRepository = playerSubscriptionRepository;
        this.questionRepository = questionRepository;
        this.gameSessionRepository = gameSessionRepository;
        this.smsService = smsService;
        this.gameProperties = gameProperties;

    }

    public void startNewGame(String phoneNumber) {

        Player player = playerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        PlayerSubscription playerSubscription = playerSubscriptionRepository.findByPlayer(player)
                .orElseThrow(() -> new RuntimeException("Subscription not found for player"));

        if (!"active".equals(playerSubscription.getStatus())) {
            throw new RuntimeException("Player does not have an active subscription");
        }

        Optional<GameSession> activeSessionOpt = gameSessionRepository.findByPlayerAndActive(player, true);
        if (activeSessionOpt.isPresent()) {
            GameSession activeSession = activeSessionOpt.get();
            sendQuestionToPlayer(activeSession);
            return;
        }

        List<Question> questions = questionRepository.findRandomQuestions(PageRequest.of(0, 3));
        GameSession gameSession = new GameSession();
        gameSession.setPlayer(player);
        gameSession.setQuestions(questions);
        gameSession.setCurrentQuestionIndex(0);
        gameSession.setScore(0);
        gameSession.setActive(true);
        gameSession.setStartTime(LocalDateTime.now());
        gameSession.setLastQuestionTime(LocalDateTime.now());

        gameSessionRepository.save(gameSession);

        sendQuestionToPlayer(gameSession);

    }

    public void handlePlayerResponse(String phoneNumber, String playerResponse) {
        Player player = playerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        GameSession activeSession = gameSessionRepository.findByPlayerAndActive(player, true)
                .orElseThrow(() -> new RuntimeException("No active game found for player"));

        Question currentQuestion = activeSession.getQuestions().get(activeSession.getCurrentQuestionIndex());
//        boolean correctAnswer = currentQuestion.getOptions().stream()
//                .anyMatch(option -> option.getOptionText().equalsIgnoreCase(playerResponse) && option.getCorrectAnsOption() == QuestionOption.correctOption.CORRECT);
        List<QuestionOption> options = currentQuestion.getOptions();
        int optionIndex = playerResponse.toUpperCase().charAt(0) - 'A';
        if (optionIndex < 0 || optionIndex >= options.size()) {
            smsService.sendSMS(phoneNumber, "Invalid response. Please enter A, B, C, etc.");
            return;
        }

        QuestionOption selectedOption = options.get(optionIndex);
        boolean correctAnswer = selectedOption.getCorrectAnsOption() == QuestionOption.correctOption.CORRECT;

        if (correctAnswer) {
            activeSession.setScore(activeSession.getScore() + 1);
            if (activeSession.getScore() >= gameProperties.getScoreToWin()) {
                activeSession.setActive(false);
                gameSessionRepository.save(activeSession);
                log.info("Congratulations! You won the game with a score of: " + activeSession.getScore());
                smsService.sendSMS(phoneNumber, "Congratulations! You won the game with a score of: " + activeSession.getScore());
                return;
            }
        }

        if (activeSession.getCurrentQuestionIndex() + 1 < activeSession.getQuestions().size()) {
            activeSession.setCurrentQuestionIndex(activeSession.getCurrentQuestionIndex() + 1);
            activeSession.setLastQuestionTime(LocalDateTime.now());
            gameSessionRepository.save(activeSession);
            sendQuestionToPlayer(activeSession);
        }
        else {
            activeSession.setActive(false);
            gameSessionRepository.save(activeSession);
            log.info("Game over! Your score: " + activeSession.getScore());
            smsService.sendSMS(phoneNumber, "Game over! Your score: " + activeSession.getScore());
        }

    }

    @Transactional
    public void resetGameSession(Long sessionId) {
        LocalDateTime newStartTime = LocalDateTime.now();
        gameSessionRepository.resetGameSession(sessionId, newStartTime);
    }


    @Scheduled(fixedDelayString = "${game.responseTimeout}000")
    public void checkGameTimeout() {
        List<GameSession> activeSessions = gameSessionRepository.findByActive(true);
        for (GameSession session : activeSessions) {
            if (session.getLastQuestionTime() == null) {
                session.setLastQuestionTime(session.getStartTime());
            }

            if (session.getLastQuestionTime() != null &&
                    Duration.between(session.getLastQuestionTime(), LocalDateTime.now()).getSeconds() > gameProperties.getResponseTimeout())   {
                session.setActive(false);
                gameSessionRepository.save(session);
                log.info("Time's up! Game over. Your score: " + session.getScore());
                smsService.sendSMS(session.getPlayer().getPhoneNumber(), "Time's up! Game over. Your score: " + session.getScore());
            }
        }

    }


    private void sendQuestionToPlayer(GameSession gameSession) {
        Question question = gameSession.getQuestions().get(gameSession.getCurrentQuestionIndex());
        List<QuestionOption> options = question.getOptions();
        StringBuilder questionText = new StringBuilder(String.format("Q%d: %s", gameSession.getCurrentQuestionIndex() + 1, question.getQuestionText()));
        char optionLabel = 'A';
//        for (int i = 0; i < options.size(); i++) {
//            questionText.append(String.format("\n%s. %s", (char) ('A' + i), options.get(i).getOptionText()));
//        }
        for (QuestionOption option : options) {
            questionText.append(String.format("\n%c. %s", optionLabel++, option.getOptionText()));
        }
        log.info(questionText.toString());
        smsService.sendSMS(gameSession.getPlayer().getPhoneNumber(), questionText.toString());
    }


}
