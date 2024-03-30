package com.example.hou.service.impl;

import com.example.hou.entity.AffairNode;
import com.example.hou.mapper.AffairNodeRepository;
import com.example.hou.service.AffairNodeService;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AffairNodeServiceImpl implements AffairNodeService {
    @Autowired
    private AffairNodeRepository affairNodeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public AffairNode createAffairNode(AffairNode affairNode) {
        AffairNode insert = affairNodeRepository.insert(affairNode);
        return insert;
    }

    @Override
    public void deleteById(String id) {
        affairNodeRepository.deleteById(id);
    }

    @Override
    public long updateById(AffairNode affairNode) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(affairNode.getId()));

        Update upt = new Update();
        Optional.ofNullable(affairNode.getContent()).ifPresent(c -> upt.set("content", c));
        Optional.ofNullable(affairNode.getContentImg()).ifPresent(c -> upt.set("contentImg", c));
        Optional.ofNullable(affairNode.getContentVideo()).ifPresent(c -> upt.set("contentVideo", c));

        UpdateResult updateResult = mongoTemplate.updateFirst(query, upt, AffairNode.class);
        return updateResult.getModifiedCount();
    }

    @Override
    public AffairNode getById(String id) {
        Optional<AffairNode> res = affairNodeRepository.findById(id);
        return res.orElse(null);
    }

    @Override
    public Page<AffairNode> getByPage(Integer pageNum, Integer pageSize) {
        Page<AffairNode> page = affairNodeRepository.findAll(PageRequest.of(pageNum - 1, pageSize));
        return page;
    }


}
