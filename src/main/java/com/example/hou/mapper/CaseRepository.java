package com.example.hou.mapper;

import com.example.hou.entity.Case;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CaseRepository extends MongoRepository<Case, String> {

}

