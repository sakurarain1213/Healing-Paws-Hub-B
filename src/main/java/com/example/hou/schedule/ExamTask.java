package com.example.hou.schedule;


import com.alibaba.fastjson.JSON;
import com.example.hou.entity.Exam;
import com.example.hou.entity.ExamRecord;
import com.example.hou.mapper.ExamRepository;
import com.example.hou.service.ExamRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

//        List<Integer> states = Arrays.asList(0, 1);
        Date now = new Date(); // 获取当前时间
        List<Exam> exams = examRepository.findByStateNotAndEndTimeBefore(-1, now); // 找到状态为0或1的exam
        for (Exam exam : exams) {
            /*Date endTime = exam.getEndTime(); // 获取每个考试的截止时间
            if (endTime != null && endTime.before(now)) { // 如果截止时间早于当前时间
                exam.setState(-1); // 更新考试状态为-1
                examRepository.save(exam); // 保存更新后的考试对象到数据库
            }*/
            exam.setState(-1); // 更新考试状态为-1
            examRepository.save(exam); // 保存更新后的考试对象到数据库
        }
    }

    /**
     * 用于每秒定时检查用户的暂时的考试记录是否超过考试的endTime
     * 如果是，则自动提交
     * */
    @Scheduled(fixedDelay = 1000) // 每秒钟检查一次
    void save_redis() {
        //先写一个接口：根据考试id和用户id  每道题更新list
        // 每调用这个改题接口

        Date now = new Date(); // 获取当前时间
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        Set<Object> examIds = hashOperations.keys("exam:exam");
        for (Object examId : examIds){
            // 获取考试的endTime
            Date endTime =(Date) hashOperations.get("exam:exam", examId);
            // endTime <= now, 则需要自动提交记录
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
            }
        }
        /*
        --------
        get  Date now

        检查考试id的endtime  is before now
        if 时间截止 {
             需要自动提交
             for(这场考试id下的所有redis用户record){
                redisTemplate 打包成Record
                调 createRecord 存到mongo  //等价于用户自己点提交
                清redis当前缓存
             }
        }
        else 还能作答{
                先检查redis是否有缓存{
                         有则更新redis里的record.list
                  }
                  else 没有redis缓存{
                          把record整个加到redis
                    }
        }

        redis操作

        */
    }
}
