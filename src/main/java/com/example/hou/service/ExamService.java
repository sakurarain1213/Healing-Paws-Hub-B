package com.example.hou.service;

import org.springframework.data.domain.Page;
import com.example.hou.entity.Exam;

import java.util.List;

public interface ExamService {
    long totalScore(List<String> questionList);
    Exam createExam(Exam req);
    Long updateExam(Exam req);
    void deleteExamById(String id);
    Exam getExamById(String id);
    Page<Exam> getExamByPage(Integer pageNum, Integer pageSize);
}
