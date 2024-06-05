package com.trivia.FredySabuni.service;


import com.trivia.FredySabuni.model.QuestionOption;
import com.trivia.FredySabuni.repository.QuestionOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionOptionService {

    @Autowired
    private QuestionOptionRepository questionOptionRepository;

    public List<QuestionOption> getOptionsByQuestionId(Long questionId) {
        return questionOptionRepository.findAll().stream()
                .filter(option -> option.getQuestion().getId().equals(questionId))
                .toList();
    }

    public QuestionOption addOption(QuestionOption option) {
        return questionOptionRepository.save(option);
    }
}
