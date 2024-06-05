package com.trivia.FredySabuni.service;

import com.trivia.FredySabuni.dto.QuestionOptionResponseDTO;
import com.trivia.FredySabuni.dto.QuestionResponseDTO;
import com.trivia.FredySabuni.model.Question;
import com.trivia.FredySabuni.model.QuestionOption;
import com.trivia.FredySabuni.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public List<QuestionResponseDTO> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();

        return questions.stream().map(question -> {
            // Create QuestionResponseDTO
            List<QuestionOptionResponseDTO> optionDTOs = question.getOptions().stream()
                    .map(option -> new QuestionOptionResponseDTO(option.getId(), option.getOptionText(), option.getCorrectAnsOption().name()))
                    .collect(Collectors.toList());

            return new QuestionResponseDTO(question.getId(), question.getQuestionText(), optionDTOs);
        }).collect(Collectors.toList());
    }

    public QuestionResponseDTO getQuestionById(Long id) {

        Question responseQuestion = questionRepository.findById(id).orElse(null);

        // Create a response DTO
        List<QuestionOptionResponseDTO> options = responseQuestion.getOptions().stream()
                .map(option -> new QuestionOptionResponseDTO(option.getId(), option.getOptionText(), option.getCorrectAnsOption().name()))
                .collect(Collectors.toList());

        return new QuestionResponseDTO(responseQuestion.getId(), responseQuestion.getQuestionText(), options);
    }

    public QuestionResponseDTO addQuestion(Question question) {

        if (question.getOptions() != null) {
            for (QuestionOption option : question.getOptions()) {
                option.setQuestion(question);
            }
        }
        Question savedQuestion = questionRepository.save(question);
        // Create a response DTO
        List<QuestionOptionResponseDTO> options = savedQuestion.getOptions().stream()
                .map(option -> new QuestionOptionResponseDTO(option.getId(), option.getOptionText(), option.getCorrectAnsOption().name()))
                .collect(Collectors.toList());

        return new QuestionResponseDTO(savedQuestion.getId(), savedQuestion.getQuestionText(), options);
    }


}
