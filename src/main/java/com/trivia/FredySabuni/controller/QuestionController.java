package com.trivia.FredySabuni.controller;


import com.trivia.FredySabuni.dto.ApiResponse;
import com.trivia.FredySabuni.dto.QuestionOptionResponseDTO;
import com.trivia.FredySabuni.dto.QuestionResponseDTO;
import com.trivia.FredySabuni.model.Question;
import com.trivia.FredySabuni.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping
    public ResponseEntity<?> getAllQuestions() {
        List<QuestionResponseDTO> getAllQuestions = questionService.getAllQuestions();
        return ApiResponse.ok(getAllQuestions, "All Questions");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable Long id) {
        QuestionResponseDTO getQuestionById = questionService.getQuestionById(id);
        return ApiResponse.ok(getQuestionById, "Question");
    }

    @PostMapping
    public ResponseEntity<?> addQuestion(@RequestBody Question question) {
        QuestionResponseDTO createdQuestion = questionService.addQuestion(question);
        return ApiResponse.ok(createdQuestion, "Question Added");
    }
}
