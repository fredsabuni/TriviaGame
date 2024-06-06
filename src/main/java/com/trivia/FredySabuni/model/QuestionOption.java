package com.trivia.FredySabuni.model;

import jakarta.persistence.*;

@Entity
public class QuestionOption extends Auditable {
    public enum correctOption {
        CORRECT,
        WRONG
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String optionText;

    @Enumerated(EnumType.STRING)
    @Column(name = "correct_option")
    correctOption correctAnsOption;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

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

    public correctOption getCorrectAnsOption() {
        return correctAnsOption;
    }

    public void setCorrectAnsOption(correctOption correctAnsOption) {
        this.correctAnsOption = correctAnsOption;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return " Options{"+
                "  id=" + id +
                ", optionText='" + optionText + '\'' +
                ", correctAnsOption='" + correctAnsOption + '\'' +
                ", createdDate=" + createdDate +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
