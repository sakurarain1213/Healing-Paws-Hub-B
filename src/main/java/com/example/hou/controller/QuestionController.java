package com.example.hou.controller;

import com.example.hou.entity.Case;
import com.example.hou.entity.Question;
import com.example.hou.result.Result;
import com.example.hou.service.QuestionService;
import com.example.hou.util.ResultUtil;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @PostMapping
    public Result createQuestion(@RequestBody Question req){

        Question created = questionService.createQuestion(req);
        if(created == null)return ResultUtil.error(null);
        return ResultUtil.success(created);
    }
    @PutMapping
    public Result updateQuestionById(@RequestBody Question req){
        if (req.getId() == null)return ResultUtil.error(0);

        System.out.println(req.getId());

        Question updated = questionService.updateQuestionById(req);
        if(updated == null)return ResultUtil.error(null);
        return ResultUtil.success(updated);
    }

    @DeleteMapping
    public Result deleteQuestionById(@NonNull @RequestParam String id){
        questionService.deleteQuestionById(id);
        return ResultUtil.success();
    }

    @GetMapping
    public Result getQuestionById(@NonNull @RequestParam String id){
        Question res = questionService.getQuestionById(id);
        System.out.println(res);
        if(res == null)return ResultUtil.error(null);
        return ResultUtil.success(res);
    }

    @GetMapping("/page")
    public Result getQuestionByPage(@NonNull @RequestParam Integer pageNum, @NonNull @RequestParam Integer pageSize){
        Page<Question> res = questionService.getQuestionByPage(pageNum, pageSize);
        System.out.println(res.getTotalElements()); //集合中总数
        System.out.println("=========");
        System.out.println(res.getContent());
        System.out.println("=========");
        System.out.println(res.getTotalPages()); //按指定分页得到的总页数

        if(res == null)return ResultUtil.error(null);
        return ResultUtil.success(res.getContent());
    }

    @GetMapping("/group")
    public Result getQuestionByGroup(@NonNull @RequestParam Integer pageNum, @NonNull @RequestParam Integer pageSize, @NonNull @RequestParam String diseases){
        Page<Question> res = questionService.getQuestionByGroup(pageNum, pageSize, diseases);
        System.out.println(res.getTotalElements()); //集合中总数
        System.out.println("=========");
        System.out.println(res.getContent());
        System.out.println("=========");
        System.out.println(res.getTotalPages()); //按指定分页得到的总页数

        if(res == null)return ResultUtil.error(null);
        return ResultUtil.success(res.getContent());
    }
}
