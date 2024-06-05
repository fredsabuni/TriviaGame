package com.trivia.FredySabuni.model;

import jakarta.persistence.*;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phoneNumber;
    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL)
    private PlayerSubscription subscription;


}
