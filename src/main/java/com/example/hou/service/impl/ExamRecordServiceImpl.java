package com.example.hou.service.impl;

import com.example.hou.entity.ExamRecord;
import com.example.hou.mapper.ExamRecordRepository;
import com.example.hou.service.ExamRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class ExamRecordServiceImpl implements ExamRecordService {
    @Autowired
    private ExamRecordRepository examRecordRepository;
    @Autowired
    private MongoTemplate template;

    @Override
    public ExamRecord createExamRecord(ExamRecord req) {
        return examRecordRepository.insert(req);
    }

    @Override
    public void deleteExamRecordById(String id) {
        examRecordRepository.deleteById(id);
    }

    @Override
    public Page<ExamRecord> getExamRecordByPage(Integer pageNum, Integer pageSize) {
        return examRecordRepository.findAll(PageRequest.of(pageNum - 1, pageSize));
    }

    @Override
    public Page<ExamRecord> getExamRecordsByUserIdWithPagination(long userId, Integer pageNum, Integer pageSize) {
        return examRecordRepository.findByUserId(userId, PageRequest.of(pageNum - 1, pageSize));
    }
}
