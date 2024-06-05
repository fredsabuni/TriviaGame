package com.trivia.FredySabuni.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PlayerSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long amount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    @OneToOne
    @JoinColumn(name = "player_id")
    Player player;

}
