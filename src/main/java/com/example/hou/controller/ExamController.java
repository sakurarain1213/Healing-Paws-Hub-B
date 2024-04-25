package com.example.hou.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.example.hou.entity.Exam;
import com.example.hou.entity.ExamRecord;
import com.example.hou.entity.PageSupport;
import com.example.hou.result.Result;
import com.example.hou.result.ResultCode;
import com.example.hou.service.ExamService;
//import com.example.hou.service.websocket.WebSocketServer;
import com.example.hou.service.websocket.WebSocketServer;
import com.example.hou.util.ResultUtil;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;

@RestController
@RequestMapping("/exam")
@Validated
public class ExamController {
    @Autowired
    private ExamService examService;

    @PostMapping
    public Result createExam(@RequestBody @NonNull @Valid Exam req) {
        if (req.missingRequiredFields())
            return ResultUtil.error("缺少必须字段");

        /*if (req.getType() <= 0)
            return ResultUtil.error("type有误");*/

        long score = examService.totalScore(req.getQuestionIdList());
        if (score == -1)
            return ResultUtil.error("题目的ID有误");
        // 设置totalScore
        req.setTotalScore(score);

        if (req.getTotalTime() <= 0)
            return ResultUtil.error("totalTime <= 0 (minutes)");
        long addTime = req.getStartTime().getTime() + req.getTotalTime() * 60 * 1000;
        long endTime = req.getEndTime().getTime();
        if (addTime > endTime)
            return ResultUtil.error("错误：endTime < startTime + totalTime");

        /*// 设置exam为未发布状态
        req.setRelease(false);*/

        // 设置exam为未发布状态
        req.setState(0);

        Exam created = examService.createExam(req);
        if (created == null)
            return ResultUtil.error(null);
        return ResultUtil.success(created);
    }

    @PutMapping
    public Result updateExamById(@RequestBody @NonNull @Valid Exam req) {
        if (req.getId() == null)
            return ResultUtil.error(null);
        if (req.missingAllRequiredFields())
            return ResultUtil.error("未填写任何需要更新的信息");

        /*if (req.getType() != null && req.getType() <= 0)
            return ResultUtil.error("type有误");*/

        // 更新questionIdList，则更新totalScore
        if (req.getQuestionIdList() != null) {
            long score = examService.totalScore(req.getQuestionIdList());
            if (score == -1)
                return ResultUtil.error("题目的ID有误");
            req.setTotalScore(score);
        }

        System.out.println(req.getId());

        Long res = examService.updateExam(req);
        if (res == null)
            return ResultUtil.error("id不存在, 或startTime + totalTime > endTime");
        if (res == 0)
            return ResultUtil.error("该exam已发布");
        return ResultUtil.success(res);
    }

    @PutMapping("/newrelease")
    public Result releaseById(/*@NotBlank(message = "id不能是空串或只有空格")
                                  @Size(min = 24, max = 24, message = "id不合法")
                                  @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
                                  @RequestParam("id") String id*/
            @RequestBody @NonNull @Valid Exam req) {
        try {
            boolean judge = examService.releaseById(req.getId());
            if (!judge) {
                System.out.println("发布exam失败");
                return ResultUtil.error("id不存在或考试已发布或已结束");
            }
            System.out.println("发布Exam成功：" + req.getId());
            return ResultUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(null);
        }
    }

    @DeleteMapping
    public Result deleteExamById(@NotBlank(message = "id不能是空串或只有空格")
                                 @Size(min = 24, max = 24, message = "id不合法")
                                 @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
                                 @RequestParam("id") String id) {
        try {
            boolean judge = examService.deleteExamById(id);
            if (!judge) {
                System.out.println("删除Exam失败，id: " + id + " 不存在");
                return ResultUtil.error("id不存在");
            }
            System.out.println("删除Exam成功：" + id);
            return ResultUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(null);
        }
    }

    @GetMapping
    public Result getExamById(@NotBlank(message = "id不能是空串或只有空格")
                              @Size(min = 24, max = 24, message = "id不合法")
                              @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
                              @RequestParam("id") String id) {
        Exam res = examService.getExamById(id);
        System.out.println(res);
        if (res == null)
            return ResultUtil.error(null);
        return ResultUtil.success(res);
    }

    /*@GetMapping("/socket/endexam/{examId}")
    public Result endExam(@PathVariable("examId") String examId, String message) {
        try {
            WebSocketServer.sendMessage("服务端推送消息：" + message, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        result.setData(message);

        return result;
    }*/

    @GetMapping("/page/multi/release")
    public Result getReleasedExamsByMultiWithPagination(@RequestParam(value = "sortTime")
                                                        Integer sortTime,
                                                        @RequestParam(value = "examName", required = false)
                                                        String examName,
                                                        @RequestParam(value = "type", required = false)
                                                        Integer type,
                                                        // 设置非必需
                                                        @RequestParam(value = "startTime", required = false)
                                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                        Date startTime,
                                                        @RequestParam(value = "endTime", required = false)
                                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                        Date endTime,
                                                        @NonNull @RequestParam("pageNum") Integer pageNum,
                                                        @NonNull @RequestParam("pageSize") Integer pageSize) {
        if (sortTime < 0 || sortTime > 2)
            return ResultUtil.error("sortTime不合法");
        if (pageNum < 1 || pageSize < 1) return ResultUtil.error("pageNum或pageSize不合法");
        if (type != null && (type <= 0 || type >= 3))
            return ResultUtil.error("type不合法");
        if (startTime != null && endTime != null && startTime.getTime() > endTime.getTime())
            return ResultUtil.error("日期不合法");

        PageSupport<Exam> res = examService.getReleasedExamsByMultiWithPagination(sortTime, examName, type, startTime, endTime, pageNum, pageSize);
        System.out.println("总页数：" + res.getTotalPages()); //按指定分页得到的总页数

        /*if (res == null)
            return ResultUtil.error(null);*/

        return ResultUtil.success(res);
    }
        @GetMapping("/page/multi")
        public Result getExamsByMultiWithPagination (@RequestParam(value = "sortTime")
                Integer sortTime,
                @RequestParam(value = "examName", required = false)
                        String examName,
                @RequestParam(value = "type", required = false)
                        Integer type,
                // 设置非必需
                @RequestParam(value = "startTime", required = false)
                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                        Date startTime,
                @RequestParam(value = "endTime", required = false)
                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                        Date endTime,
                @NonNull @RequestParam("pageNum") Integer pageNum,
                @NonNull @RequestParam("pageSize") Integer pageSize){
            if (sortTime < 0 || sortTime > 2)
                return ResultUtil.error("sortTime不合法");
            if (pageNum < 1 || pageSize < 1) return ResultUtil.error("pageNum或pageSize不合法");
            if (type != null && (type <= 0 || type >= 3))
                return ResultUtil.error("type不合法");
            if (startTime != null && endTime != null && startTime.getTime() > endTime.getTime())
                return ResultUtil.error("日期不合法");

            PageSupport<Exam> res = examService.getExamsByMultiWithPagination(sortTime, examName, type, startTime, endTime, pageNum, pageSize);
            System.out.println("总页数：" + res.getTotalPages()); //按指定分页得到的总页数

        /*if (res == null)
            return ResultUtil.error(null);*/

            return ResultUtil.success(res);
        }


        @Deprecated
        @PutMapping("/release")
        public Result releaseExamById (/*@NotBlank(message = "id不能是空串或只有空格")
                                  @Size(min = 24, max = 24, message = "id不合法")
                                  @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
                                  @RequestParam("id") String id*/
                @RequestBody @NonNull @Valid Exam req){
            try {
                boolean judge = examService.releaseExamById(req.getId());
                if (!judge) {
                    System.out.println("发布exam失败");
                    return ResultUtil.error("id不存在或考试已发布或已结束");
                }
                System.out.println("发布Exam成功：" + req.getId());
                return ResultUtil.success();
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.error(null);
            }
        }

        @Deprecated
        @GetMapping("/page")
        public Result getExamByPage (@RequestParam("pageNum") Integer pageNum,
                @RequestParam("pageSize") Integer pageSize){
            if (pageNum < 1 || pageSize < 1) return ResultUtil.error("pageNum或pageSize不合法");

            Page<Exam> res = examService.getExamByPage(pageNum, pageSize);
            System.out.println(res.getTotalElements()); //集合中总数
            System.out.println(res.getTotalPages()); //按指定分页得到的总页数

            if (res == null)
                return ResultUtil.error(null);
            return ResultUtil.success(res.getContent());
        }

        @Deprecated
        @GetMapping("/page/time_order")
        public Result getExamsByTimeOrderWithPagination (@RequestParam("pageNum") Integer pageNum,
                @RequestParam("pageSize") Integer pageSize){
            if (pageNum < 1 || pageSize < 1) return ResultUtil.error("pageNum或pageSize不合法");

            Page<Exam> res = examService.getExamsByTimeOrderWithPagination(pageNum, pageSize);
            System.out.println(res.getTotalElements()); //集合中总数
            System.out.println(res.getTotalPages()); //按指定分页得到的总页数

            if (res == null)
                return ResultUtil.error(null);
            return ResultUtil.success(res.getContent());
        }

        @Deprecated
        @GetMapping("/page/name")
        public Result getExamsByNameLikeWithPagination (@NotBlank @RequestParam("examName") String examName,
                @RequestParam("pageNum") Integer pageNum,
                @RequestParam("pageSize") Integer pageSize){
            if (pageNum < 1 || pageSize < 1) return ResultUtil.error("pageNum或pageSize不合法");
            Page<Exam> res = examService.getExamsByNameLikeWithPagination(examName, pageNum, pageSize);
            System.out.println(res.getTotalElements()); //集合中总数
            System.out.println(res.getTotalPages()); //按指定分页得到的总页数

            if (res == null)
                return ResultUtil.error(null);
            return ResultUtil.success(res.getContent());
        }

        @Deprecated
        @GetMapping("/page/type")
        public Result getExamsByTypeWithPagination ( @RequestParam("type") int type,
        @RequestParam("pageNum") Integer pageNum,
        @RequestParam("pageSize") Integer pageSize){
            if (pageNum < 1 || pageSize < 1) return ResultUtil.error("pageNum或pageSize不合法");
            if (type <= 0 || type >= 3)
                return ResultUtil.error("type不合法");

            Page<Exam> res = examService.getExamsByTypeWithPagination(type, pageNum, pageSize);
            System.out.println(res.getTotalElements()); //集合中总数
            System.out.println(res.getTotalPages()); //按指定分页得到的总页数

            if (res == null)
                return ResultUtil.error(null);
            return ResultUtil.success(res.getContent());
        }

        @Deprecated
        @GetMapping("/page/time")
        public Result getExamsByTimeWithPagination (@RequestParam("startTime")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                Date startTime,
                @RequestParam("endTime")
                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                        Date endTime,
                @RequestParam("pageNum") Integer pageNum,
                @RequestParam("pageSize") Integer pageSize){
            if (pageNum < 1 || pageSize < 1) return ResultUtil.error("pageNum或pageSize不合法");
            if (startTime.getTime() > endTime.getTime())
                return ResultUtil.error("日期不合法");

            Page<Exam> res = examService.getExamsByTimeWithPagination(startTime, endTime, pageNum, pageSize);
            System.out.println(res.getTotalElements()); //集合中总数
            System.out.println(res.getTotalPages()); //按指定分页得到的总页数

            if (res == null)
                return ResultUtil.error(null);
            return ResultUtil.success(res.getContent());
        }


    }