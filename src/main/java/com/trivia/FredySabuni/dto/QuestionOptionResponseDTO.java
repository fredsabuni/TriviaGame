package com.trivia.FredySabuni.dto;



public class QuestionOptionResponseDTO {
    private Long id;
    private String optionText;
    private String correctAnsOption;

    public QuestionOptionResponseDTO (){}

    public QuestionOptionResponseDTO(Long id, String optionText, String correctAnsOption) {
        this.id = id;
        this.optionText = optionText;
        this.correctAnsOption = correctAnsOption;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public String getCorrectAnsOption() {
        return correctAnsOption;
    }

    public void setCorrectAnsOption(String correctAnsOption) {
        this.correctAnsOption = correctAnsOption;
    }
}
