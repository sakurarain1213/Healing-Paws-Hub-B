package com.example.hou.service.impl;

import com.example.hou.entity.Disease;
import com.example.hou.entity.Question;
import com.example.hou.mapper.DiseaseRepository;
import com.example.hou.service.DiseaseService;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class DiseaseServiceImpl implements DiseaseService {
    @Autowired
    private DiseaseRepository diseaseRepository;

    @Autowired
    private MongoTemplate template;

    @Override
    public long existName(String name) {
        Query query = new Query();
        Criteria criteria = Criteria.where("name").is(name);
        query.addCriteria(criteria);
        long count = template.count(query, Disease.class);
        return count;
    }

    @Override
    public Disease createDisease(Disease disease) {
        Disease created = diseaseRepository.insert(disease);
        return created;
    }

    public Disease findByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        Disease one = template.findOne(query, Disease.class);
        return one;
    }

    @Override
    public int deleteById(String id) {
        Optional<Disease> found = diseaseRepository.findById(id);
        Disease cur = found.orElse(null);
        if (cur == null)return -1;

        diseaseRepository.deleteById(id);

//        System.out.println("update=========");
//
////        从含有指定id元素的question的id列表 删除id
//        Query query = new Query();
//        query.addCriteria(Criteria.where("type").is(id));
//
//        Update update = new Update();
//        update.pull("type", id);
//
//        UpdateResult updateResult = template.updateMulti(query, update, Question.class);
//
//        System.out.println(updateResult.getMatchedCount());
//        System.out.println(updateResult.getModifiedCount());
//        System.out.println(updateResult.wasAcknowledged());
        return 0;
    }

    @Override
    public long updateById(Disease disease) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(disease.getId()));
        Update update = new Update();
        if (disease.getName() != null)update.set("name", disease.getName());
        if (disease.getType() != null)update.set("type", disease.getType());
        UpdateResult updateResult = template.updateFirst(query, update, Disease.class);
        System.out.println(updateResult.getModifiedCount());
        return updateResult.getModifiedCount();
    }

    @Override
    public Page<Disease> getByPage(Integer pageNum, Integer pageSize) {
        Page<Disease> res = diseaseRepository.findAll(PageRequest.of(pageNum - 1, pageSize));
        return res;
    }

    @Override
    public List<Disease> getPageByType(Integer pageNum, Integer pageSize, String type) {
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is(type));
        query.skip((pageNum - 1) * pageSize).limit(pageSize);
        List<Disease> diseases = template.find(query, Disease.class);
        return diseases;
    }

    @Override
    public long getPageByTypeCount(Integer pageNum, Integer pageSize, String type) {
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is(type));

        return template.count(query, Disease.class);
    }


}
