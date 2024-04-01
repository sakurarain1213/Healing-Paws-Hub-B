package com.example.hou.service.impl;

import co.elastic.clients.util.VisibleForTesting;
import com.example.hou.entity.ExamRecord;
import com.example.hou.mapper.ExamRecordRepository;
import com.example.hou.service.ExamRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// TODO
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
    public Page<ExamRecord> getExamRecordByTimeOrderWithPagination(Integer pageNum, Integer pageSize) {
//        Sort.by("time").descending()
        /*List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "time"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "userId"));
        Sort.by(orders)*/

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("time", "userId").descending());
        return examRecordRepository.findAll(pageable);
    }

    @Override
    public Page<ExamRecord> getExamRecordsByUserIdWithPagination(long userId, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("time").descending());
        return examRecordRepository.findByUserId(userId, pageable);
    }
}
