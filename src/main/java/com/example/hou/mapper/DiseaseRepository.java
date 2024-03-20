package com.example.hou.mapper;

import com.example.hou.entity.Disease;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DiseaseRepository extends MongoRepository<Disease, String> {

}
