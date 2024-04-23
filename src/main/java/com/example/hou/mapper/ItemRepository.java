package com.example.hou.mapper;


import com.example.hou.entity.Item;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ItemRepository extends MongoRepository<Item, String> {

    // 例如，根据某个条件查找物品  略   不随意加方法 报Error creating bean with name 'itemRepository'
    //List<Item> findBySomeCondition(String condition);

    // 例如，根据名称查找物品 略
    //List<Item> findByName(String name);
    // ...
    Page<Item> findByDepartmentId(String departmentId, Pageable pageable);


}