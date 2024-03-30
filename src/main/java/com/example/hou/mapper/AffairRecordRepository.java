package com.example.hou.mapper;

import com.example.hou.entity.AffairRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AffairRecordRepository extends MongoRepository<AffairRecord, String> {

}
