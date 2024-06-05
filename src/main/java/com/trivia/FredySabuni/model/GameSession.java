package com.trivia.FredySabuni.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class GameSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Player player;
    @OneToMany
    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;
    private boolean active;
    private LocalDateTime startTime;
}
