package com.trivia.FredySabuni.controller;

import com.trivia.FredySabuni.model.Question;
import com.trivia.FredySabuni.model.QuestionOption;
import com.trivia.FredySabuni.service.QuestionOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions/{questionId}/options")
public class QuestionOptionController {
    @Autowired
    private QuestionOptionService questionOptionService;

    @GetMapping
    public List<QuestionOption> getOptionsByQuestionId(@PathVariable Long questionId) {
        return questionOptionService.getOptionsByQuestionId(questionId);
    }

    @PostMapping
    public QuestionOption addOption(@PathVariable Long questionId, @RequestBody QuestionOption option) {
        option.setQuestion(new Question());
        option.getQuestion().setId(questionId);
        return questionOptionService.addOption(option);
    }
}
