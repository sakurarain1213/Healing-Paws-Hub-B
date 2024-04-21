package com.example.hou.service;

import com.example.hou.entity.Exam;
import com.example.hou.entity.ExamRecord;
import org.springframework.data.domain.Page;

// TODO
public interface ExamRecordService {
    ExamRecord createExamRecord(ExamRecord req);
    Page<ExamRecord> getExamRecordByTimeOrderWithPagination(Integer pageNum, Integer pageSize);

    Page<ExamRecord> getExamRecordsByUserIdWithPagination(long userId, Integer pageNum, Integer pageSize);
    boolean deleteExamRecordById(String id, Integer userId);

    void addExamRecord(ExamRecord req);
}
