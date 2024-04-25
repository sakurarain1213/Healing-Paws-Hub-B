package com.example.hou.service.impl;

import com.example.hou.entity.Exam;
import com.example.hou.entity.PageSupport;
import com.example.hou.entity.Question;
import com.example.hou.entity.QuestionEntity;
import com.example.hou.mapper.ExamRepository;
import com.example.hou.service.ExamService;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private MongoTemplate template;

    /**
     * 返回-1如果questionList的id有误，否则返回其题目的总分
     */
    @Override
    public long totalScore(List<String> questionIdList) {
        long score = 0;
        for(String questionId : questionIdList){
            Query query = new Query(Criteria.where("id").is(questionId));
            Question question = template.findOne(query, Question.class);
            if(question == null)
                return -1;
            score += question.getScore();
        }
        return score;
    }

    @Override
    public Exam createExam(Exam req) {
        return examRepository.insert(req);
    }

    @Override
    public Long updateExam(Exam req) {
        Optional<Exam> opt = examRepository.findById(req.getId());
        if (!opt.isPresent()) {
            return null;
        }
        Exam exam = opt.get();

        Update update = new Update();
        if(req.getExamName() != null)
            update.set("examName", req.getExamName());
        if(req.getQuestionIdList() != null)
            update.set("questionList", req.getQuestionIdList());

        if(req.getStartTime() != null) {
            update.set("startTime", req.getStartTime());
            exam.setStartTime(req.getStartTime());
        }
        if(req.getTotalTime() != null) {
            update.set("totalTime", req.getTotalTime());
            exam.setTotalTime(req.getTotalTime());
        }
        if(req.getEndTime() != null) {
            update.set("endTime", req.getEndTime());
            exam.setEndTime(req.getEndTime());
        }
        if(req.getTotalScore() != null)
            update.set("totalScore", req.getTotalScore());
        if(req.getType() != null)
            update.set("type", req.getType());

        // 判断更新之后，时间约束是否满足
        long addTime = exam.getStartTime().getTime() + exam.getTotalTime() * 60 * 1000;
        long endTime = exam.getEndTime().getTime();
        if(addTime > endTime)
            return null;

       /* // 只能修改未发布的exam
        Query query = new Query(Criteria.where("id").is(req.getId()).and("release").is(false));
       */
        // 只能发布未发布的exam
        Query query = new Query(Criteria.where("id").is(req.getId()).and("state").is(0));
        UpdateResult updateResult = template.updateFirst(query, update, Exam.class);
        System.out.println(updateResult.getModifiedCount());

        return updateResult.getModifiedCount();
    }


    @Override
    public boolean releaseById(String id) {
        Optional<Exam> res = examRepository.findById(id);

        if(!res.isPresent())
            return false;
        if(res.get().getState() != 0)
            return false;

        System.out.println("release");
        // 更新release, questionList
        Exam exam = res.get();
        List<String> questionIdList = exam.getQuestionIdList();
        Criteria criteria = Criteria.where("id").in(questionIdList);
        Query questionQuery = new Query(criteria);
        List<Question> questionList = template.find(questionQuery, Question.class);
//        System.out.println(questionList);

        List<QuestionEntity> questionEntityList = new ArrayList<>();
        for (Question question : questionList) {
            QuestionEntity questionEntity = new QuestionEntity(question.getName(),question.getStatement(),
                    question.getAnswer(), question.getDetail(), question.getScore(), question.getQuestionType());
            questionEntityList.add(questionEntity);
        }

        Query query = new Query(Criteria.where("id").is(id).
                and("state").is(0));

        Update update = new Update();
        update.set("state", 1);
        update.set("questionIdList", null);
        update.set("questionList", questionEntityList);

        UpdateResult result = template.updateFirst(query, update, Exam.class);
        System.out.println(result.getModifiedCount());
        return true;
    }

    @Override
    public boolean deleteExamById(String id) {
        if(examRepository.countById(id) == 0)
            return false;
        examRepository.deleteById(id);
        return true;
    }

    @Override
    public Exam getExamById(String id) {
        Optional<Exam> res = examRepository.findById(id);
        return res.orElse(null);
    }

    @Override
    public PageSupport<Exam> getExamsByMultiWithPagination(Integer sortTime,
                                                           String examName,
                                                           Integer type,
                                                           Date startTime,
                                                           Date endTime,
                                                           Integer pageNum,
                                                           Integer pageSize){

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Criteria criteria = new Criteria();

        // 包装类防止为null时判断出错
        if(sortTime == 1)
            pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("startTime").descending());
        else if(sortTime == 2)
            pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("startTime").ascending());

        if(examName != null)
            criteria.and("examName").regex("^.*" + examName + ".*$");

        if(type != null)
            criteria.and("type").is(type);

        if(startTime != null && endTime != null)
            criteria.and("startTime").gte(startTime).and("endTime").lte(endTime);

        Query query = Query.query(criteria);
        query.with(pageable);
        // 查询对应页码数据
        List<Exam> list = template.find(query, Exam.class);
        Query query111 = Query.query(criteria);
        // 查询总数
        long count = template.count(query111, Exam.class);

        System.out.println("集合总数：" + count);
        long pages = count / pageSize + (count % pageSize == 0 ? 0 : 1);

        PageSupport<Exam> pageSupport = new PageSupport<>();
        pageSupport.setTotalPages((int) pages).setListData(list);
        return pageSupport;
    }

    @Override
    public PageSupport<Exam> getReleasedExamsByMultiWithPagination(Integer sortTime,
                                                           String examName,
                                                           Integer type,
                                                           Date startTime,
                                                           Date endTime,
                                                           Integer pageNum,
                                                           Integer pageSize){

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Criteria criteria = new Criteria();

        // 包装类防止为null时判断出错
        if(sortTime == 1)
            pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("startTime").descending());
        else if(sortTime == 2)
            pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("startTime").ascending());

        if(examName != null)
            criteria.and("examName").regex("^.*" + examName + ".*$");

        if(type != null)
            criteria.and("type").is(type);

        if(startTime != null && endTime != null)
            criteria.and("startTime").gte(startTime).and("endTime").lte(endTime);

        // 只查找已发布的考试
        criteria.and("state").is(1);

        Query query = Query.query(criteria);
        query.with(pageable);
        // 查询对应页码数据
        List<Exam> list = template.find(query, Exam.class);
        Query query111 = Query.query(criteria);
        // 查询总数
        long count = template.count(query111, Exam.class);

        System.out.println("集合总数：" + count);
        long pages = count / pageSize + (count % pageSize == 0 ? 0 : 1);

        PageSupport<Exam> pageSupport = new PageSupport<>();
        pageSupport.setTotalPages((int) pages).setListData(list);
        return pageSupport;
    }









    @Override
    public boolean releaseExamById(String id) {
        Optional<Exam> res = examRepository.findById(id);

        if(!res.isPresent() || res.get().getRelease()) {
            return false;
        }
        System.out.println("release");
        // 更新release, questionList
        Exam exam = res.get();
        List<String> questionIdList = exam.getQuestionIdList();
        Criteria criteria = Criteria.where("id").in(questionIdList);
        Query questionQuery = new Query(criteria);
        List<Question> questionList = template.find(questionQuery, Question.class);
//        System.out.println(questionList);

        List<QuestionEntity> questionEntityList = new ArrayList<>();
        for (Question question : questionList) {
            QuestionEntity questionEntity = new QuestionEntity(question.getName(), question.getStatement(),
                    question.getAnswer(), question.getDetail(), question.getScore(), question.getQuestionType());
            questionEntityList.add(questionEntity);
        }

        Query query = new Query(Criteria.where("id").is(id).
                and("release").is(false));

        Update update = new Update();
        update.set("release", true);
        update.set("questionIdList", null);
        update.set("questionList", questionEntityList);

        UpdateResult result = template.updateFirst(query, update, Exam.class);
        System.out.println(result.getModifiedCount());
        return true;
    }

    @Override
    public Page<Exam> getExamByPage(Integer pageNum, Integer pageSize) {
        return examRepository.findAll(PageRequest.of(pageNum - 1, pageSize));
    }

    @Override
    public Page<Exam> getExamsByReleaseWithPagination(boolean release, Integer pageNum, Integer pageSize) {
        return examRepository.findByRelease(release, PageRequest.of(pageNum - 1, pageSize));
    }

    @Override
    public Page<Exam> getExamsByTimeOrderWithPagination(Integer pageNum, Integer pageSize) {
        // 按时间降序排序
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("startTime").descending());
        return examRepository.findAll(pageable);
    }
    @Override
    public Page<Exam> getExamsByNameLikeWithPagination(String examName, Integer pageNum, Integer pageSize) {
        return examRepository.findByExamNameLike(examName, PageRequest.of(pageNum - 1, pageSize));
    }
    @Override
    public Page<Exam> getExamsByTypeWithPagination(int type, Integer pageNum, Integer pageSize) {
        return examRepository.findByType(type, PageRequest.of(pageNum - 1, pageSize));
    }
    @Override
    public Page<Exam> getExamsByTimeWithPagination(Date startTime, Date endTime, Integer pageNum, Integer pageSize) {
        Criteria criteria = Criteria.where("startTime").gte(startTime).and("endTime").lte(endTime);
        Query query = Query.query(criteria);
        List<Exam> list = template.find(query, Exam.class);
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<Exam> page = new PageImpl<>(list, pageable, list.size());
        return page;
    }

}
