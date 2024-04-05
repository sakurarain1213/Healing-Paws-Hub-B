package com.example.hou.service;

import com.example.hou.entity.AffairNode;
import org.springframework.data.domain.Page;

public interface AffairNodeService {

    AffairNode createAffairNode(AffairNode affairNode);

    int deleteById(String id);

    long updateById(AffairNode affairNode);

    AffairNode getById(String id);

    Page<AffairNode> getByPage(Integer pageNum, Integer pageSize);
}
