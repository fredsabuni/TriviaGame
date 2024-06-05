package com.trivia.FredySabuni.dto;



import java.util.List;


public class QuestionResponseDTO {
    private Long id;
    private String questionText;
    private List<QuestionOptionResponseDTO> options;

    public  QuestionResponseDTO(){}

    public QuestionResponseDTO(Long id, String questionText, List<QuestionOptionResponseDTO> options) {
        this.id = id;
        this.questionText = questionText;
        this.options = options;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<QuestionOptionResponseDTO> getOptions() {
        return options;
    }

    public void setOptions(List<QuestionOptionResponseDTO> options) {
        this.options = options;
    }
}
