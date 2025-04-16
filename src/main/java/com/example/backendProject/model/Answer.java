package com.example.backendProject.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Answer {
    private int id;
    @JsonProperty(value = "user_id")
    private int userId;
    @JsonProperty(value = "question_id")
    private int questionId;
    @JsonProperty(value = "selected_option")
    private int selectedOption;

    public Answer(int id, int userId, int questionId, int selectedOption) {
        this.id = id;
        this.userId = userId;
        this.questionId = questionId;
        this.selectedOption = selectedOption;
    }

    public Answer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(int selectedOption) {
        this.selectedOption = selectedOption;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", userId=" + userId +
                ", questionId=" + questionId +
                ", selectedOption=" + selectedOption +
                '}';
    }
}
