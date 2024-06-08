package com.trivia.FredySabuni.controller;

import com.trivia.FredySabuni.dto.PlayerSubscriptionDTO;
import com.trivia.FredySabuni.service.GameSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController {
    private final GameSessionService gameService;

    public GameController(GameSessionService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public ResponseEntity<Void> startGame(@RequestParam String phoneNumber) {
        gameService.startNewGame(phoneNumber);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/answer")
    public ResponseEntity<Void> answerQuestion(@RequestParam String phoneNumber, @RequestParam String answer) {
        gameService.handlePlayerResponse(phoneNumber, answer);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset")
    public ResponseEntity<Void> resetGameSession(@RequestParam Long sessionId) {
        gameService.resetGameSession(sessionId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addPlayerSubscription")
    public ResponseEntity<Void> addUserAndSubscription(@RequestBody PlayerSubscriptionDTO playerSubscriptionDTO) {
        gameService.addPlayerAndSubscription(playerSubscriptionDTO);
        return ResponseEntity.ok().build();
    }
}
