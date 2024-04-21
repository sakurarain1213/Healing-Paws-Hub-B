package com.example.hou.service.impl;

import co.elastic.clients.util.VisibleForTesting;
import com.alibaba.fastjson.JSON;
import com.example.hou.entity.Exam;
import com.example.hou.entity.ExamRecord;
import com.example.hou.entity.QuestionEntity;
import com.example.hou.mapper.ExamRecordRepository;
import com.example.hou.mapper.ExamRepository;
import com.example.hou.service.ExamRecordService;
import com.example.hou.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ExamRecordServiceImpl implements ExamRecordService {
    @Autowired
    private ExamService examService;
    @Autowired
    private ExamRecordRepository examRecordRepository;
    @Autowired
    private MongoTemplate template;
    // 注入 RedisTemplate
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ExamRepository examRepository;

    // 将解答暂时存入redis
    @Override
    public void addExamRecord(ExamRecord req) {
        String value = JSON.toJSONString(req);

        // 这里value代表该exam的endTime(CommitController将其赋值)
        // 原因：存在redis的ExamRecord，除非过了endTime才会自动提交，故ExamRecord的time == endTime
        redisTemplate.opsForHash().put("exam:exam", req.getExamId(), req.getTime());
        // 由于每次CommitController都要调用此方法，故用哈希更快匹配更新value
        redisTemplate.opsForHash().put("examRecord:" + req.getExamId(), String.valueOf(req.getUserId()), value);
    }

    @Override
    public ExamRecord createExamRecord(ExamRecord req) {
        Exam exam = examService.getExamById(req.getExamId());

        long score = 0;

        int i = 0;
        for (String answer : req.getResult()) {
            if (answer == null) {
                i++;
                continue;
            }

            QuestionEntity question = exam.getQuestionList().get(i);
            if (answer.equalsIgnoreCase(question.getAnswer()))
                score += question.getScore();
            i++;
        }

        req.setScore(score);

        // 先清除redis缓存里的数据，防止到时候覆盖
        redisTemplate.opsForHash().delete("examRecord:" + req.getExamId(), String.valueOf(req.getUserId()));

        return examRecordRepository.insert(req);
    }
    @Override
    public boolean deleteExamRecordById(String id, Integer userId) {
        if(examRecordRepository.countByIdAndUserId(id, Long.valueOf(userId)) == 0)
            return false;
        examRecordRepository.deleteByIdAndUserId(id, Long.valueOf(userId));
        return true;
    }



    @Override
    public Page<ExamRecord> getExamRecordByTimeOrderWithPagination(Integer pageNum, Integer pageSize) {
//        Sort.by("time").descending()
        /*List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "time"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "userId"));
        Sort.by(orders)*/

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("time", "userId").descending());
        return examRecordRepository.findAll(pageable);
    }

    @Override
    public Page<ExamRecord> getExamRecordsByUserIdWithPagination(long userId, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("time").descending());
        return examRecordRepository.findByUserId(userId, pageable);
    }
}
