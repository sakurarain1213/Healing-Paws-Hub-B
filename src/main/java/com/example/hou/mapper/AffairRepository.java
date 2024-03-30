package com.example.hou.mapper;

import com.example.hou.entity.Affair;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AffairRepository extends MongoRepository<Affair, String> {

}
