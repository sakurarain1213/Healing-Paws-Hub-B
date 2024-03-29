package com.example.hou.service.impl;

import com.example.hou.entity.Exam;
import com.example.hou.entity.Question;
import com.example.hou.mapper.ExamRepository;
import com.example.hou.mapper.QuestionRepository;
import com.example.hou.service.ExamService;
import com.example.hou.service.QuestionService;
import com.example.hou.util.ResultUtil;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

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
    public long totalScore(List<String> questionList) {
        long score = 0;
        for(String questionId : questionList){
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
        Update update = new Update();
        if(req.getExamName() != null)
            update.set("examName", req.getExamName());
        if(req.getQuestionList() != null)
            update.set("questionList", req.getQuestionList());
        if(req.getStartTime() != null)
            update.set("startTime", req.getStartTime());
        if(req.getTotalTime() > 0)
            update.set("totalTime", req.getTotalTime());
        if(req.getTotalScore() >= 0)
            update.set("totalScore", req.getTotalScore());
        if(req.getEndTime() != null)
            update.set("endTime", req.getEndTime());

        if(req.getTotalTime() <= 0)
            return null;
        long addTime = req.getStartTime().getTime() + req.getTotalTime() * 60 * 1000;
        long endTime = req.getEndTime().getTime();
        if(addTime > endTime)
            return null;

        Query query = new Query(Criteria.where("id").is(req.getId()));
        UpdateResult updateResult = template.updateFirst(query, update, Exam.class);
        System.out.println(updateResult.getModifiedCount());
        return updateResult.getModifiedCount();
    }

    @Override
    public void deleteExamById(String id) {
        examRepository.deleteById(id);
    }

    @Override
    public Exam getExamById(String id) {
        Optional<Exam> res = examRepository.findById(id);
        return res.orElse(null);
    }

    @Override
    public Page<Exam> getExamByPage(Integer pageNum, Integer pageSize) {
        return examRepository.findAll(PageRequest.of(pageNum - 1, pageSize));
    }
}
