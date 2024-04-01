package com.example.hou.controller;

import com.example.hou.entity.*;
import com.example.hou.result.Result;
import com.example.hou.service.ExamRecordService;
import com.example.hou.service.ExamService;
import com.example.hou.util.ResultUtil;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/examrecord")
@Validated
public class ExamRecordController {
    @Autowired
    private ExamRecordService examRecordService;
    @Autowired
    private ExamService examService;

    @PostMapping
    public Result createExamRecord(@RequestBody @NonNull @Valid ExamRecord req) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated())return ResultUtil.error("未登录");

        LogUser loginUser = (LogUser) authentication.getPrincipal();
        Integer userId = Optional.ofNullable(loginUser)
                .map(LogUser::getUser)
                .map(SysUser::getUserId)
                .orElse(null);
        if (userId == null)return ResultUtil.error("未登录");

        Exam exam = examService.getExamById(req.getExamId());
        if(exam == null)
            return ResultUtil.error("Exam的ID有误");
        if (!exam.getRelease())
            return ResultUtil.error("Exam发布状态有误");
        if(exam.getQuestionList().size() != req.getResult().size())
            return ResultUtil.error("解答数与题目总数不对应");

        long score = 0;
        /*List<String> answers = exam.getQuestionList().stream().map(QuestionEntity::getAnswer).collect(Collectors.toList());
        List<Long> scores = exam.getQuestionList().stream().map(QuestionEntity::getScore).collect(Collectors.toList());*/
        int i = 0;
        for(String answer : req.getResult()){
            if(answer == null){
                i++;
                continue;
            }
            if(!answer.matches("[TFA-Da-d]"))
                return ResultUtil.error("解答不合法");
            QuestionEntity question = exam.getQuestionList().get(i);
            if(answer.equalsIgnoreCase(question.getAnswer()))
                score += question.getScore();
            i++;
        }
        req.setUserId(userId)
                .setScore(score)
                .setTime(new Date());

        ExamRecord created = examRecordService.createExamRecord(req);
        if(created == null)
            return ResultUtil.error(null);
        return ResultUtil.success(created);
    }

    @DeleteMapping
    public Result deleteExamRecordById(@NotBlank(message = "id不能是空串或只有空格")
                                 @Size(min = 24, max = 24, message = "id不合法")
                                 @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
                                 @RequestParam("id") String id) {
        examRecordService.deleteExamRecordById(id);
        return ResultUtil.success();
    }

    @GetMapping("/page")
    public Result getExamRecordByTimeOrderWithPagination(@RequestParam("pageNum") Integer pageNum,
                                @RequestParam("pageSize") Integer pageSize) {
        if (pageNum < 1 || pageSize < 1) return ResultUtil.error("pageNum或pageSize不合法");

        Page<ExamRecord> res = examRecordService.getExamRecordByTimeOrderWithPagination(pageNum, pageSize);
        System.out.println(res.getTotalElements()); //集合中总数
        System.out.println(res.getTotalPages()); //按指定分页得到的总页数

        if (res == null)
            return ResultUtil.error(null);
        return ResultUtil.success(res.getContent());
    }

    @GetMapping("/page/id")
    public Result getExamRecordsByUserIdWithPagination(@RequestParam("pageNum") Integer pageNum,
                                                         @RequestParam("pageSize") Integer pageSize) {
        if (pageNum < 1 || pageSize < 1) return ResultUtil.error("pageNum或pageSize不合法");

        //        获取当前登录user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated())return ResultUtil.error("未登录");

        LogUser loginUser = (LogUser) authentication.getPrincipal();
        Integer userId = Optional.ofNullable(loginUser)
                .map(LogUser::getUser)
                .map(SysUser::getUserId)
                .orElse(null);
        if (userId == null)return ResultUtil.error("未登录");

        Page<ExamRecord> res = examRecordService.getExamRecordsByUserIdWithPagination(userId, pageNum, pageSize);
        System.out.println(res.getTotalElements()); //集合中总数
        System.out.println(res.getTotalPages()); //按指定分页得到的总页数

        if (res == null)
            return ResultUtil.error(null);
        return ResultUtil.success(res.getContent());
    }
}
