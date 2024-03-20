package com.example.hou.service.impl;

import com.example.hou.entity.Disease;
import com.example.hou.entity.Question;
import com.example.hou.mapper.QuestionRepository;
import com.example.hou.service.QuestionService;
//import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

//import javax.management.Query;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private MongoTemplate template;
    @Override
    public Question createQuestion(Question req) {
        Question saved = questionRepository.insert(req);
        return saved;
    }

    @Override
    public Question updateQuestionById(Question req) {
        Question saved = questionRepository.save(req);
        return saved;
    }

    @Override
    public void deleteQuestionById(String id) {
        questionRepository.deleteById(id);
    }

    @Override
    public Question getQuestionById(String id) {
        Optional<Question> res = questionRepository.findById(id);
        return res.orElse(null);
    }

    @Override
    public Page<Question> getQuestionByPage(Integer pageNum, Integer pageSize) {
        Page<Question> page = questionRepository.findAll(PageRequest.of(pageNum - 1, pageSize));
        return page;
    }

    @Override
    public Page<Question> getQuestionByGroup(Integer pageNum, Integer pageSize, String diseases) {
        String[] diseaseList = diseases.split(" ");
        /* 思路：根据diseaseList的病名查到对应病的idList，然后查询 type属性：包含idList 的 question */
//        Page<Question> page = template.findAll(PageRequest.of(pageNum - 1, pageSize));
//        Criteria criteria = Criteria.where("name").in(diseaseList);
////        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
//        Query query = new Query(criteria)/*.with(pageable)*/;
//        List<Disease> diseaseList1 = template.find(query, Disease.class);
//
//        // 通过病名查询到的病的idList
//        List<Long> idList = diseaseList1.stream().map(Disease::getId).collect(Collectors.toSet());

        return null;
    }

}
