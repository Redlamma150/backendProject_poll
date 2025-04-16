package com.example.backendProject.controller;

import com.example.backendProject.model.*;
import com.example.backendProject.service.PollService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/polls")
public class PollController {

    @Autowired
    private PollService pollService;

    private static final Logger logger = LoggerFactory.getLogger(PollController.class);

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        try {
            User user = pollService.fetchUserData(id);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        try {
            List<User> users = pollService.fetchAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/questions")
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        logger.info("Creating new poll question: {}", question);
        try {
            Question createQuestion = pollService.create(question);
            if (createQuestion != null) {
                return new ResponseEntity<>(createQuestion, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error creating question: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<Question> updatePoll(@RequestBody Question question){
        logger.info("Updating poll question with ID: {}", question.getId());
        try {
            Question updatedQuestion = pollService.update(question);
            if (updatedQuestion != null){
                return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            logger.error("Error updating question: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<String> deletePoll(@PathVariable int id){
        logger.info("Deleting poll question with ID: {}", id);
        try {
            String result = pollService.deleteById(id);
            if (result.contains("successfully")){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            logger.error("Question not found with ID: {}", id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error("Error deleting question: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/questions/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable int id){
        logger.info("Fetching question with ID: {}", id);
        try {
            Question question = pollService.getById(id);
            if (question != null){
                return new ResponseEntity<>(question, HttpStatus.OK);
            }
            logger.error("Question not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            logger.error("Error fetching question: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        try {
            return new ResponseEntity<>(pollService.getAll(),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/answers")
    public ResponseEntity<Answer> saveAnswer(@RequestBody Answer answer) {
        logger.info("Saving answer for user ID: {} and question ID: {}", answer.getUserId(), answer.getQuestionId());
        try {
           Answer saveAnswer = pollService.saveAnswer(answer);
            if (saveAnswer != null) {
                return new ResponseEntity<>(saveAnswer, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            logger.error("Error saving answer: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteAnswersByUserId(@PathVariable int userId){
        try {
            pollService.deleteAnswerByUserId(userId);
            String response = "The answers for the user id: " + userId + "has been deleted successfully!";
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/questions/{id}/option-counts")
    public ResponseEntity<ResponseDTO<Map<String, Integer>>> getOptionCounts(@PathVariable int id){
        try {
            Map<String, Integer> counts = pollService.getOptionCountsForQuestion(id);
            ResponseDTO<Map<String, Integer>> response = new ResponseDTO<>(
                    "This API retrives the count of users who selected each option for specifies question ID.",
                    counts
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/questions/{id}/total-answers")
    public ResponseEntity<ResponseDTO<Integer>> getTotalAnswers(@PathVariable int id){
        try {
            int totalAnswers = pollService.getTotalAnswersForQuestion(id);
           ResponseDTO<Integer> response = new ResponseDTO<>(
                   "This API retrives the number of users who answered the specified question ID.",
                   totalAnswers
           );
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}/answers")
    public ResponseEntity<List<Map<String, Object>>> getUserAnswers(@PathVariable int id){
        try {
            List<Map<String, Object>> answers = pollService.getUserAnswers(id);
            return new ResponseEntity<>(answers, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}/total-questions")
    public ResponseEntity<ResponseDTO<Integer>> getTotalQuestionsAnswered(@PathVariable int id){
        try {
            int total = pollService.getTotalQuestionsAnsweredByUser(id);
            ResponseDTO<Integer> response = new ResponseDTO<>(
                    "This API retrives the total questions answered by a user.",
                    total
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
@GetMapping("/questions/stats")
    public ResponseEntity<List<Map<String, Object>>> getQuestionStats(){
        try {
            List<Map<String, Object>> stats = pollService.getQuestionsStats();
            return new ResponseEntity<>(stats, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
}
}
