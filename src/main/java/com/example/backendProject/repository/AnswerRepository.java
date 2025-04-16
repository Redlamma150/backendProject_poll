package com.example.backendProject.repository;

import com.example.backendProject.model.Answer;
import com.example.backendProject.model.Question;
import com.example.backendProject.model.QuestionStatsResponse;
import com.example.backendProject.repository.mapper.AnswerMapper;
import com.example.backendProject.repository.mapper.QuestionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AnswerRepository {

    private static final Logger logger = LoggerFactory.getLogger(AnswerRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Answer saveAnswer(Answer answer){
      try {
          String sql = "INSERT INTO answers (user_id, question_id, selected_option) VALUES (?, ?, ?)";
          jdbcTemplate.update(sql, answer.getUserId(), answer.getQuestionId(), answer.getSelectedOption());
          return getByQuestionIdAndUserId(answer.getQuestionId(), answer.getUserId());
      }
      catch (Exception e){
          logger.error("Error saving answer for user ID {} and question ID {}: {}", answer.getUserId(), answer.getQuestionId(), e.getMessage(), e);
          return null;
      }
    }

    public String deleteAnswer(int userId){
        try {
            String sql = "DELETE answers WHERE user_id = ?";
             jdbcTemplate.update(sql,userId);
             return "The answers for the user with id:" + userId + "has been successfully deleted";
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Map<String,Integer> countResponsesByOption(int questionId){
        String sql = "SELECT selected_option, COUNT(*) as count FROM answers WHERE question_id = ? GROUP BY selected_option";
        List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql, questionId);

        Map<String, Integer> responseCount = new HashMap<>();
        for (Map<String, Object> row : rows) {
            responseCount.put("Option " + row.get("selected_option"), ((Number)row.get("count")).intValue());
        }
        return responseCount;
    }

    public Answer getByQuestionIdAndUserId(int questionId, int userId){
        try {
            String sql = "SELECT * FROM answers WHERE question_id = ? AND user_id = ?";
            return jdbcTemplate.queryForObject(sql, new AnswerMapper(), questionId, userId);
        } catch (Exception e) {
            logger.error("Error retrieving answers for question ID: {}, or answer ID: {}", questionId, userId, e);

            return null;
        }
    }
    public Integer countTotalAnswersForQuestion(int questionId){
        try {
            String sql = "SELECT COUNT(*) FROM answers WHERE question_id = ?";
            return jdbcTemplate.queryForObject(sql, Integer.class, questionId);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Map<String, Object>> findUserAnswers(int userId){
       try {
           String sql = "SELECT q.title AS \"title\", a.selected_option AS \"selected_option\" FROM answers a " +
                   "JOIN question q ON a.question_id = q.id " +
                   "WHERE a.user_id = ?";
           return jdbcTemplate.queryForList(sql, userId);
       } catch (Exception e){
           System.out.println(e.getMessage());
           return null;
       }
    }

    public Integer countQuestionsAnsweredByUser(int userId){
        //DISTINCT USED HER TO REMOVE DUPLICATES ROWS FROM THE RESULT
        try {
            String sql = "SELECT COUNT(DISTINCT question_id) FROM answers WHERE user_id = ?";
            return jdbcTemplate.queryForObject(sql, Integer.class, userId);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }



    public List<Map<String, Object>> findQuestionStats(){
       try {
           String sql = "SELECT q.id AS \"question_id\", q.title AS \"title\", a.selected_option AS \"selected_option\", COUNT(*) AS \"user_count\" " +
                   "FROM answers a JOIN question q ON a.question_id = q.id " +
                   "GROUP BY q.id, q.title, a.selected_option " +
                   "ORDER BY q.id, a.selected_option";

           return jdbcTemplate.queryForList(sql);

       } catch (Exception e){
           System.out.println(e.getMessage());
           return null;
       }
    }
}
