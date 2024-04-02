package com.example.hou.mapper;

import com.example.hou.entity.ExamRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExamRecordRepository extends MongoRepository<ExamRecord, String> {
    Page<ExamRecord> findByUserId(long userId, Pageable pageable);

    long countById(String id);
}
