package com.example.hou.schedule;


import com.alibaba.fastjson.JSON;
import com.example.hou.controller.ExamController;
import com.example.hou.entity.Exam;
import com.example.hou.entity.ExamRecord;
import com.example.hou.mapper.ExamRepository;
import com.example.hou.service.ExamRecordService;
import com.example.hou.service.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class ExamTask {
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private ExamRecordService examRecordService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 每分钟根据endTime更新一次考试的state
     * */
    @Scheduled(fixedDelay = 60000) // 上次任务结束一分钟后检查一次
    void changeStateTask() {
        log.info("开始更新examstate");

        Date now = new Date(); // 获取当前时间
        List<Exam> exams = examRepository.findByStateNotAndEndTimeBefore(-1, now); // 找到状态为0或1的exam
        for (Exam exam : exams) {
            exam.setState(-1); // 更新考试状态为-1
            examRepository.save(exam); // 保存更新后的考试对象到数据库
        }
    }

    /**
     * 用于每秒定时检查用户的暂时的考试记录是否超过考试的endTime
     * 如果是，则自动提交
     * */
    @Scheduled(fixedDelay = 1000) // 每秒钟检查一次
    void save_redis() throws IOException {
        Date now = new Date(); // 获取当前时间

        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();

        Set<Object> examIds = hashOperations.keys("exam:exam");
        for (Object examId : examIds){
            // 获取考试的endTime
            Date endTime =(Date) hashOperations.get("exam:exam", examId);

            // 如果endTime <= now, 说明考试已结束，则需要自动提交记录
            if(endTime.getTime() <= System.currentTimeMillis()) {
                // 获取该考试下的所有考试记录
                List<Object> examRecords = hashOperations.values("examRecord:" + examId);
                for(Object examRecordString : examRecords){
                    // 创建考试记录
                    ExamRecord examRecord = JSON.parseObject((String) examRecordString, ExamRecord.class);
                    examRecordService.createExamRecord(examRecord);
                }
                // 删除该考试的所有考试记录缓存
                redisTemplate.delete("examRecord:" + examId);
                // 删除该考试缓存
                hashOperations.delete("exam:exam", examId);

                // 调用该webSocketServer的群发消息static方法，主动给参与该考试的用户发送消息
                WebSocketServer.sendMessage("ended", (String) examId);
            }
        }
    }
}
