package com.trivia.FredySabuni.repository;

import com.trivia.FredySabuni.model.IncomingMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomingMessageRepository extends JpaRepository<IncomingMessage, Long> {

}
