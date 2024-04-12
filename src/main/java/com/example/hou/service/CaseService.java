package com.example.hou.service;

import com.example.hou.entity.Case;
import org.springframework.data.domain.Page;

import java.util.List;


public interface CaseService {
    Case createCase(Case req);

    Long updateCaseById(Case req);

    int deleteCaseById(String id);

    Case getCaseById(String id);

    Page<Case> getCaseByPage(Integer pageNum, Integer pageSize);

    List<Case> getCaseByCombinedName(Integer pageNum, Integer pageSize, String diseases);

    long getCaseByCombinedNameCount(Integer pageNum, Integer pageSize, String diseases);

}
