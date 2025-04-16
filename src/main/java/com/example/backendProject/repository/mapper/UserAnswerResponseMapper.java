//package com.example.backendProject.repository.mapper;
//
//import com.example.backendProject.model.UserAnswerResponse;
//import org.springframework.jdbc.core.RowMapper;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class UserAnswerResponseMapper implements RowMapper<UserAnswerResponse> {
//    @Override
//    public UserAnswerResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
//        UserAnswerResponse userAnswerResponse = new UserAnswerResponse();
//        userAnswerResponse.setQuestionTitle(rs.getString("title"));
//        userAnswerResponse.setSelectedOption(rs.getInt("selected_option"));
//        return userAnswerResponse;
//    }
//}
