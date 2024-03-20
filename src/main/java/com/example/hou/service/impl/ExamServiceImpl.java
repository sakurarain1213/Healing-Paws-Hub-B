package com.example.hou.service.impl;

import com.example.hou.entity.Exam;
import com.example.hou.mapper.ExamRepository;
import com.example.hou.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamRepository examRepository;
    private MongoTemplate template;

    @Override
    public Exam createExam(Exam req) {
        return examRepository.insert(req);
    }

    @Override
    public Exam updateExamById(Exam req) {
        return examRepository.save(req);
    }

    @Override
    public void deleteExamById(String id) {
        examRepository.deleteById(id);
    }

    @Override
    public Exam getExamById(String id) {
        Optional<Exam> res = examRepository.findById(id);
        return res.orElse(null);
    }

    @Override
    public Page<Exam> getExamByPage(Integer pageNum, Integer pageSize) {
        return examRepository.findAll(PageRequest.of(pageNum - 1, pageSize));
    }
}
