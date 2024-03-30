package com.example.hou.mapper;

import com.example.hou.entity.AffairNode;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AffairNodeRepository extends MongoRepository<AffairNode, String> {

}
