package com.example.hou.service.impl;

import com.example.hou.entity.Disease;
import com.example.hou.entity.Exam;
import com.example.hou.entity.PageSupport;
import com.example.hou.entity.Question;
import com.example.hou.mapper.QuestionRepository;
import com.example.hou.service.DiseaseService;
import com.example.hou.service.QuestionService;
//import org.hibernate.Criteria;
import com.mongodb.client.result.UpdateResult;
import org.apache.poi.ss.formula.functions.T;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
    public boolean existErrorDisease(List<String> diseaseList) {
        for (String diseaseName : diseaseList) {
            Query query = new Query(Criteria.where("name").is(diseaseName));
            long count = template.count(query, Disease.class);
            if(count <= 0)
                return true;
        }
        return false;
    }

    @Override
    public Question createQuestion(Question req) {
        return questionRepository.insert(req);
    }

    @Override
    public Long updateQuestion(Question req) {
        Optional<Question> opt = questionRepository.findById(req.getId());
        if (!opt.isPresent()) {
            return null;
        }
        Question question = opt.get();

        Update update = new Update();
        if(req.getName() != null)
            update.set("name", req.getName());
        if(req.getStatement() != null)
            update.set("statement", req.getStatement());

        if(req.getType() != null)
            update.set("type", req.getType());

        if(req.getQuestionType() != null){
            update.set("questionType", req.getQuestionType());
            question.setQuestionType(req.getQuestionType());
        }
        if(req.getAnswer() != null) {
            update.set("answer", req.getAnswer());
            question.setAnswer(req.getAnswer());
        }

        if(req.getDetail() != null)
            update.set("detail", req.getDetail());
        if (req.getScore() != null)
            update.set("score", req.getScore());

        // 判断questionType是否和answer匹配
        if(question.questionTypeNotMatch())
            return null;

        Query query = new Query(Criteria.where("id").is(req.getId()));

        UpdateResult updateResult = template.updateFirst(query, update, Question.class);
        System.out.println("更新个数：" + updateResult.getModifiedCount());
        return updateResult.getModifiedCount();
    }

    @Override
    public boolean deleteQuestionById(String id) {
        Question question = getQuestionById(id);
        if(question == null)
            return false;
        questionRepository.deleteById(id);

        // 如果exam是未发布状态，从exam的questionIdList, 删除id, 并修改exam的总分
        Query query = new Query(Criteria.where("release").is(false)
                .and("questionIdList").is(id));



        Update update = new Update();
        // 从questionIdList找到id,并删除
        update.pull("questionIdList", id);
        // 修改exam的总分
        update.inc("totalScore", -question.getScore());
        UpdateResult updateResult = template.updateMulti(query, update, Exam.class);

        // 匹配到的文档数量
        System.out.println(updateResult.getMatchedCount());
        // 实际被修改的文档数量
        System.out.println(updateResult.getModifiedCount());
        // 更新操作是否被确认
        System.out.println(updateResult.wasAcknowledged());

        return true;
    }

    @Override
    public Question getQuestionById(String id) {
        Optional<Question> res = questionRepository.findById(id);
        return res.orElse(null);
    }

    @Override
    public PageSupport<Question> getQuestionByPage(Integer pageNum, Integer pageSize) {
//        return questionRepository.findAll(PageRequest.of(pageNum - 1, pageSize));
        // 查询对应页码数据
        Query query = new Query().with(PageRequest.of(pageNum - 1, pageSize));
        // 查询对应页码数据
        List<Question> list = template.find(query, Question.class);
        // 查询总数
        long count = template.count(new Query(), Question.class);
        long pages = count / pageSize + (count % pageSize == 0 ? 0 : 1);

        PageSupport<Question> pageSupport = new PageSupport<>();
        pageSupport.setTotalPages((int) pages).setListData(list);
        return pageSupport;

    }

    @Override
    public PageSupport<Question> getQuestionByGroup(Integer pageNum, Integer pageSize, List<String> diseases) {
        if(diseases == null || diseases.isEmpty())
            return this.getQuestionByPage(pageNum, pageSize);

        Criteria criteria = Criteria.where("type").all(diseases);
        Query query = new Query(criteria).with(PageRequest.of(pageNum - 1, pageSize));
        // 查询对应页码数据
        List<Question> list = template.find(query, Question.class);
        Query query111 = new Query(criteria);
        // 查询总数
        long count = template.count(query111, Question.class);
        long pages = count / pageSize + (count % pageSize == 0 ? 0 : 1);

        PageSupport<Question> pageSupport = new PageSupport<>();
        pageSupport.setTotalPages((int) pages).setListData(list);
        return pageSupport;
    }

}

    /*@Override
    public Page<Question> getQuestionByGroup(Integer pageNum, Integer pageSize, String diseases) {
        if(diseases == null || diseases.isEmpty())
            return questionRepository.findAll(PageRequest.of(pageNum - 1, pageSize));

        *//* 思路：根据diseaseList的病名查到对应病的idList，然后查询 type属性：包含idList 的 question *//*
        String[] diseaseNames = diseases.split(" ");

        Criteria criteria = Criteria.where("name").in(diseaseNames);
        Query query = new Query(criteria);
        List<Disease> diseaseList = template.find(query, Disease.class);

        // 通过病名查询到的病的idList
        List<String> idList = diseaseList.stream().map(Disease::getId).collect(Collectors.toList());

        System.out.println("病id列表: " + idList);
       *//* Criteria criteria1 = Criteria.where("type")
                .andDocumentStructureMatches(() ->
                        new Document().append("db.inventory.find", new Document("$all", idList)));*//*
        Criteria criteria1 = Criteria.where("type").all(idList);
        Query query1 = new Query(criteria1);
        List<Question> list = template.find(query1, Question.class);

        System.out.println("题目列表: " + list);

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<Question> page = new PageImpl<>(list, pageable, list.size());
        return page;
    }

}*/
