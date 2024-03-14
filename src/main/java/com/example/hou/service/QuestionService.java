package com.example.hou.service;

import com.example.hou.entity.Case;
import com.example.hou.entity.Question;
import org.springframework.data.domain.Page;

public interface QuestionService {
    Question createQuestion(Question req);

    Question updateCaseById(Question req);

    void deleteCaseById(String id);

    Question getCaseById(String id);

    Page<Question> getCaseByPage(Integer pageNum, Integer pageSize);
}
