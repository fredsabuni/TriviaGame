package com.trivia.FredySabuni.repository;

import com.trivia.FredySabuni.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByPhoneNumber(String PhoneNumber);
}
