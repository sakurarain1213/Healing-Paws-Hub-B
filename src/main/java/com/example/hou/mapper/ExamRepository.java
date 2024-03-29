package com.example.hou.mapper;

import com.example.hou.entity.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;


public interface ExamRepository extends MongoRepository<Exam, String> {
    // 在使用Spring Data JPA时，只需要在Repository接口中定义方法
    // 具体的实现会由Spring Data JPA自动完成
    Page<Exam> findByExamNameLike(String name, Pageable pageable);
    Page<Exam> findByType(int type, Pageable pageable);
}
