package com.example.hou.service;

import org.springframework.data.domain.Page;
import com.example.hou.entity.Exam;

public interface ExamService {
    Exam createExam(Exam req);
    Exam updateExamById(Exam req);
    void deleteExamById(String id);
    Exam getExamById(String id);
    Page<Exam> getExamByPage(Integer pageNum, Integer pageSize);

}
