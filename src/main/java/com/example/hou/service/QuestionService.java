package com.example.hou.service;

import com.example.hou.entity.Disease;
import com.example.hou.entity.Question;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QuestionService {
    boolean existErrorDisease(List<String> diseaseList);
    Question createQuestion(Question req);

    Long updateQuestion(Question req);

    boolean deleteQuestionById(String id);

    Question getQuestionById(String id);

    Page<Question> getQuestionByPage(Integer pageNum, Integer pageSize);

    Page<Question> getQuestionByGroup(Integer pageNum, Integer pageSize, List<String> diseases);
}
