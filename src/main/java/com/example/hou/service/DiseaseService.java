package com.example.hou.service;

import com.example.hou.entity.Disease;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DiseaseService {
    long existName(String name);

    Disease createDisease(Disease disease);

    Disease findByName(String name);

    int deleteById(String id);

    long updateById(Disease disease);

    Page<Disease> getByPage(Integer pageNum, Integer pageSize);

    List<Disease> getPageByType(Integer pageNum, Integer pageSize, String type);

    long getPageByTypeCount(Integer pageNum, Integer pageSize, String type);
}
