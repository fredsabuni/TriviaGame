package com.trivia.FredySabuni.repository;

import com.trivia.FredySabuni.model.GameSession;
import com.trivia.FredySabuni.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long> {

    @Modifying
    @Query("UPDATE GameSession gs SET gs.currentQuestionIndex = 0, gs.score = 0, gs.active = true, gs.startTime = :startTime WHERE gs.id = :id")
    void resetGameSession(@Param("id") Long id, @Param("startTime") LocalDateTime startTime);

    Optional<GameSession> findByPlayerAndActive(Player player, boolean active);
    List<GameSession> findByActive(boolean active);
}
