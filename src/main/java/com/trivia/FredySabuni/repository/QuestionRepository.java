package com.trivia.FredySabuni.repository;


import com.trivia.FredySabuni.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findQuestionById(Long id);

    @Query("SELECT q FROM Question q ORDER BY function('RAND')")
    List<Question> findRandomQuestions(Pageable pageable);
}