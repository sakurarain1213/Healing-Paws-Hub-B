package com.example.hou.service;

import com.example.hou.entity.ExamRecord;
import org.springframework.data.domain.Page;


public interface ExamRecordService {
    ExamRecord createExamRecord(ExamRecord req);
    Page<ExamRecord> getExamRecordByPage(Integer pageNum, Integer pageSize);

    Page<ExamRecord> getExamRecordsByUserIdWithPagination(long userId, Integer pageNum, Integer pageSize);
    void deleteExamRecordById(String id);
}
