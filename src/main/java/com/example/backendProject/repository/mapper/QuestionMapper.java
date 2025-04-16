package com.example.backendProject.repository.mapper;

import com.example.backendProject.model.Question;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionMapper implements RowMapper<Question> {

    @Override
    public Question mapRow(ResultSet rs, int rowNum) throws SQLException{
        Question question = new Question();
          question.setId(rs.getInt("id"));
          question.setQuestionTitle(rs.getString("title"));
          question.setOption1(rs.getString("option_1"));
          question.setOption2(rs.getString("option_2"));
          question.setOption3(rs.getString("option_3"));
          question.setOption4(rs.getString("option_4"));
          return question;

    }

}
