package com.example.backendProject.service;

import com.example.backendProject.model.*;
import com.example.backendProject.repository.AnswerRepository;
import com.example.backendProject.repository.PollRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PollService {
    private static final Logger logger = LoggerFactory.getLogger(PollService.class);


    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserClient userClient;

    public User fetchUserData(int userId) {
        return userClient.getUserById(userId);
    }

    public List<User> fetchAllUsers() {
        return userClient.getAllUsers();
    }

    public String deleteAnswerByUserId(int userId){
        return answerRepository.deleteAnswer(userId);
    }


    public Question create(Question question){
        return  pollRepository.create(question);
    }

    public Question update(Question question){
        return pollRepository.update(question);
    }
    public String deleteById(int id){
        if (pollRepository.deleteById(id) != null) {
            return pollRepository.deleteById(id);
        }
        return "The poll with id: " + id + " does not exist, so can't be deleted";
    }

    public Question getById(int id){
        return pollRepository.findQuestionById(id);
    }

    public Answer saveAnswer(Answer answer) {
        if (!pollRepository.existsById(answer.getQuestionId())) {
            throw new RuntimeException("Question not found with ID: " + answer.getQuestionId());
        }
        if (userClient.getUserById(answer.getUserId()) == null) {
            throw new RuntimeException("User not found with ID: " + answer.getUserId());
        }
        return answerRepository.saveAnswer(answer);
    }

    public Map<String, Integer> getOptionCountsForQuestion(int questionId){
        return answerRepository.countResponsesByOption(questionId);
    }

    public Integer getTotalAnswersForQuestion(int questionId){
        return answerRepository.countTotalAnswersForQuestion(questionId);
    }

    public Map<String, Integer> getResponsesForQuestion(int questionId) {
        logger.info("Fetching responses for question ID: {}", questionId);
        Map<String, Integer> responses = answerRepository.countResponsesByOption(questionId);
        logger.info("Responses fetched for question ID {}: {}", questionId, responses);
        return responses;
    }

    public List<Map<String, Object>> getUserAnswers(int userId){
        return answerRepository.findUserAnswers(userId);
    }

    public Integer getTotalQuestionsAnsweredByUser(int userId){
        return answerRepository.countQuestionsAnsweredByUser(userId);
    }

    public List<Map<String,Object>> getQuestionsStats(){
        return answerRepository.findQuestionStats();
    }

    public List<Question> getAll(){
        return pollRepository.getAll();
    }
}
