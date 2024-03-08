package com.example.hou.service.impl;

import com.example.hou.entity.Case;
import com.example.hou.mapper.CaseRepository;
import com.example.hou.service.CaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CaseServiceImpl implements CaseService {
    @Autowired
    private CaseRepository caseRepository;

    private MongoTemplate template;

    @Override
    public Case createCase(Case req) {
        Case saved = caseRepository.insert(req);
        return saved;
//        if (saved == null || StringUtils.isBlank(saved.getId()))return 0;
//        else return 1;
//        return saved == null ? 0 : 1;
    }

    @Override
    public Case updateCaseById(Case req) {
        Case saved = caseRepository.save(req);
//        if (saved == null)return 0;
//        else return 1;
        return saved;
    }

    @Override
    public void deleteCaseById(String id) {
        caseRepository.deleteById(id);
    }

    @Override
    public Case getCaseById(String id) {
        Optional<Case> res = caseRepository.findById(id);
        return res.orElse(null);
    }

    @Override
    public Page<Case> getCaseByPage(Integer pageNum, Integer pageSize) {
        Page<Case> page = caseRepository.findAll(PageRequest.of(pageNum - 1, pageSize));
        return page;
    }
}
