package com.example.hou.mapper;

import com.example.hou.entity.Case;
import com.example.hou.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: Healing-Paws-Hub-B
 * @description:
 * @author: 作者
 * @create: 2024-03-29 09:34
 */

public interface DepartmentRepository extends MongoRepository<Department, String> {

}
