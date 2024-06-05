package com.trivia.FredySabuni.repository;

import com.trivia.FredySabuni.model.GameSession;
import com.trivia.FredySabuni.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
    Optional<GameSession> findByPlayerAndActive(Player player, boolean active);
    List<GameSession> findByActive(boolean active);
}
