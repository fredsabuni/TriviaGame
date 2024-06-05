package com.trivia.FredySabuni.repository;

import com.trivia.FredySabuni.model.Player;
import com.trivia.FredySabuni.model.PlayerSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerSubscriptionRepository extends JpaRepository<PlayerSubscription, Long> {
    Optional<PlayerSubscription> findByPlayer(Player player);
}
