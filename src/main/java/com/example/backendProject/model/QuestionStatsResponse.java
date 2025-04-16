package com.example.backendProject.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class QuestionStatsResponse {
    @JsonProperty("title")
    private String questionTitle;
    @JsonProperty("option_counts")
    private Map<String, Integer> optionCounts;

    public QuestionStatsResponse(String questionTitle, Map<String, Integer> optionCounts) {
        this.questionTitle = questionTitle;
        this.optionCounts = optionCounts;
    }

    public QuestionStatsResponse() {
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public Map<String, Integer> getOptionCounts() {
        return optionCounts;
    }

    public void setOptionCounts(Map<String, Integer> optionCounts) {
        this.optionCounts = optionCounts;
    }

    @Override
    public String toString() {
        return "QuestionStatsResponse{" +
                "questionTitle='" + questionTitle + '\'' +
                ", optionCounts=" + optionCounts +
                '}';
    }
}
