package com.example.backendProject.repository.mapper;

import com.example.backendProject.model.Answer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnswerMapper implements RowMapper<Answer> {
    @Override
    public Answer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Answer answer = new Answer();
        answer.setId(rs.getInt("id"));
        answer.setUserId(rs.getInt("user_id"));
        answer.setQuestionId(rs.getInt("question_id"));
        answer.setSelectedOption(rs.getInt("selected_option"));
        return answer;
    }
}
