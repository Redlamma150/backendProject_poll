package com.example.backendProject.repository;

import com.example.backendProject.model.Question;
import com.example.backendProject.repository.mapper.QuestionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PollRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(PollRepository.class);

    public Question create(Question question){
        try {
            String sql = "INSERT INTO question (title, option_1, option_2, option_3, option_4) VALUES(?, ?, ? ,?, ?)";
            jdbcTemplate.update(sql, question.getQuestionTitle(), question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4());
            return getByQuestionHelper(question.getQuestionTitle());
        }catch (Exception e){
            logger.error("Error creating question: {}", e.getMessage(), e);
            return null;
        }
    }

    public Question update(Question question){
        try {
            String sql ="UPDATE poll SET question = ?, option_1, option_2, option_3, option_4 WHERE id = ?";
            jdbcTemplate.update(sql, question.getQuestionTitle(), question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4(), question.getId());
            return findQuestionById(question.getId());
        }catch (Exception e){
            logger.error("Error updating question: {}", e.getMessage(), e);

            return null;
        }
    }

    public String deleteById(int id){
        try {
            String sql = "DELETE FROM poll WHERE id = ?";
            jdbcTemplate.update(sql);
            return "The poll with id: " + id + "has been deleted successfully";
        }catch (Exception e){
            logger.error("Error deleting question with id {}: {}", id, e.getMessage(), e);
            return null;
        }

    }

    public Question findQuestionById(int id) {
        try {
            String sql = "SELECT * FROM question WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, new QuestionMapper(), id);
        } catch (Exception e){
            logger.error("Error creating question: {}", e.getMessage(), e);
            return null;
        }
    }

    public List<Question> getAll() {
        try {
            String sql = "SELECT * FROM question";
            return jdbcTemplate.query(sql, new QuestionMapper());
        }catch (Exception e){
            logger.error("Error fetching all questions: {}", e.getMessage(), e);
            return null;
        }
    }

    public Question getByQuestionHelper(String question){
        try {
            String sql = "SELECT * FROM question WHERE title = ?";
            return jdbcTemplate.queryForObject(sql, new QuestionMapper(), question);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean existsById(int questionId) {
        try {
            String sql = "SELECT COUNT(*) FROM question WHERE id = ?";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, questionId);
            return count != null && count > 0;
        } catch (Exception e) {
            logger.error("Error checking if question exists by id {}: {}", questionId, e.getMessage(), e);
            return false;

        }
    }
}
