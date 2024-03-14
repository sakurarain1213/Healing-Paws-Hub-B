package com.example.hou.service.impl;

import com.example.hou.entity.Case;
import com.example.hou.entity.Question;
import com.example.hou.mapper.CaseRepository;
import com.example.hou.mapper.QuestionRepository;
import com.example.hou.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private MongoTemplate template;
    @Override
    public Question createQuestion(Question req) {
        Question saved = questionRepository.insert(req);
        return saved;
    }

    @Override
    public Question updateCaseById(Question req) {
        return null;
    }

    @Override
    public void deleteCaseById(String id) {

    }

    @Override
    public Question getCaseById(String id) {
        return null;
    }

    @Override
    public Page<Question> getCaseByPage(Integer pageNum, Integer pageSize) {
        return null;
    }
}
