package com.example.hou.controller;

import com.example.hou.entity.Case;
import com.example.hou.entity.Question;
import com.example.hou.result.Result;
import com.example.hou.service.CaseService;
import com.example.hou.service.QuestionService;
import com.example.hou.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @PostMapping
    public Result createCase(@RequestBody Question req){

        Question created = questionService.createQuestion(req);
        if(created == null)return ResultUtil.error(null);
        return ResultUtil.success(created);
    }
}
