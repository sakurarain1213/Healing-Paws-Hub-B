package com.example.hou.service.impl;

import com.example.hou.entity.Affair;
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
    public int deleteById(String id) {
        AffairNode cur = affairNodeRepository.findById(id).orElse(null);
        if(cur == null)return -1;

        affairNodeRepository.deleteById(id);

//        删除affair.affairs (list)中与当前id相同的元素
        Query query = new Query();
        query.addCriteria(Criteria.where("affairs").is(id));

        Update update = new Update();
        update.pull("affairs", id);

        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, Affair.class);
        System.out.println("updateResult.getModifiedCount: " + updateResult.getModifiedCount());

        return 0;
    }

    @Override
    public long updateById(AffairNode affairNode) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(affairNode.getId()));

        Update upt = new Update();
        Optional.ofNullable(affairNode.getName()).ifPresent(c -> upt.set("name", c));
        Optional.ofNullable(affairNode.getContent()).ifPresent(c -> upt.set("content", c));

        Optional.ofNullable(affairNode.getPositionX()).ifPresent(c -> upt.set("positionX", c));
        Optional.ofNullable(affairNode.getPositionY()).ifPresent(c -> upt.set("positionY", c));

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
