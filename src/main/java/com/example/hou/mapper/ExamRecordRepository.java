package com.example.hou.mapper;

import com.example.hou.entity.ExamRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public interface ExamRecordRepository extends MongoRepository<ExamRecord, String> {
    Page<ExamRecord> findByUserId(long userId, Pageable pageable);

    long countByIdAndUserId(String id, Long userId);

    void deleteByIdAndUserId(String id, Long userId);
}
