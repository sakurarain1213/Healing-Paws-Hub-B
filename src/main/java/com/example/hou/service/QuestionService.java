package com.example.hou.service;

import com.example.hou.entity.Question;
import org.springframework.data.domain.Page;

public interface QuestionService {
    Question createQuestion(Question req);

    Question updateQuestionById(Question req);

    void deleteQuestionById(String id);

    Question getQuestionById(String id);

    Page<Question> getQuestionByPage(Integer pageNum, Integer pageSize);

    Page<Question> getQuestionByGroup(Integer pageNum, Integer pageSize, String diseases);
}
