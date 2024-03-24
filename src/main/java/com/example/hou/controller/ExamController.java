package com.example.hou.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.example.hou.entity.Exam;
import com.example.hou.result.Result;
import com.example.hou.service.ExamService;
import com.example.hou.util.ResultUtil;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/exam")
@Validated
public class ExamController {
    @Autowired
    private ExamService examService;

    @PostMapping
    public Result createExam(@RequestBody @NonNull @Valid Exam req){
        if(req.missingRequiredFields())
            return ResultUtil.error("缺少必须字段");

        long score = examService.totalScore(req.getQuestionList());
        if(score == -1)
            return ResultUtil.error("题目的ID有误");
        req.setTotalScore(score);

        if(req.getTotalTime() <= 0)
            return ResultUtil.error("totalTime <= 0 (minutes)");
        long addTime = req.getStartTime().getTime() + req.getTotalTime() * 60 * 1000;
        long endTime = req.getEndTime().getTime();
        if(addTime > endTime)
            return ResultUtil.error("错误：endTime < startTime + totalTime");

        Exam created = examService.createExam(req);
        if(created == null)
            return ResultUtil.error("maybe time error");
        return ResultUtil.success(created);
    }

    @PutMapping
    public Result updateExamById(@RequestBody @NonNull @Valid Exam req){
        if(req.getId() == null)
            return  ResultUtil.error(0);
        if(req.missingAllRequiredFields())
            return ResultUtil.error("未填写任何需要更新的信息");
        if(req.getQuestionList() != null) {
            long score = examService.totalScore(req.getQuestionList());
            if(score == -1)
                return ResultUtil.error("题目的ID有误");
            req.setTotalScore(score);
        }

        System.out.println(req.getId());

        Long res = examService.updateExam(req);
        if (res == null || res == 0)
            return ResultUtil.error(null);
        return ResultUtil.success(res);
    }

    @DeleteMapping
    public Result deleteExamById(@NotBlank(message = "id不能是空串或只有空格")
                                     @Size(min = 24, max = 24, message = "id不合法")
                                     @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
                                     @RequestParam("id") String id){
        examService.deleteExamById(id);
        return ResultUtil.success();
    }
    @GetMapping
    public Result getExamById(@NotBlank(message = "id不能是空串或只有空格")
                                  @Size(min = 24, max = 24, message = "id不合法")
                                  @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
                                  @RequestParam("id") String id){
        Exam res = examService.getExamById(id);
        System.out.println(res);
        if(res == null)
            return ResultUtil.error(null);
        return ResultUtil.success(res);
    }
    @GetMapping("/page")
    public Result getExamByPage(@NonNull @RequestParam("pageNum") Integer pageNum,
                                @NonNull @RequestParam("pageSize") Integer pageSize){
        if(pageNum < 1 || pageSize < 1)return ResultUtil.error("pageNum或pageSize不合法");

        Page<Exam> res = examService.getExamByPage(pageNum, pageSize);
        System.out.println(res.getTotalElements()); //集合中总数
        System.out.println("=========");
        System.out.println(res.getContent());
        System.out.println("=========");
        System.out.println(res.getTotalPages()); //按指定分页得到的总页数

        if(res == null)
            return ResultUtil.error(null);
        return ResultUtil.success(res.getContent());
    }
}
