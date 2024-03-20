package com.example.hou.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.example.hou.entity.Exam;
import com.example.hou.result.Result;
import com.example.hou.service.ExamService;
import com.example.hou.util.ResultUtil;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exam")
public class ExamController {
    @Autowired
    private ExamService examService;

    @PostMapping
    public Result createExam(@RequestBody Exam req){
        Exam created = examService.createExam(req);
        if(created == null)
            return ResultUtil.error(null);
        return ResultUtil.success(created);
    }

    @PutMapping
    public Result updateExamById(@RequestBody Exam req){
        if(req.getId() == null)
            return  ResultUtil.error(0);

        System.out.println(req.getId());

        Exam updated = examService.updateExamById(req);
        if(updated == null)
            return ResultUtil.error(null);
        return ResultUtil.success(updated);
    }

    @PutMapping
    public Result deleteExamById(@NonNull @RequestParam String id){
        examService.deleteExamById(id);
        return ResultUtil.success();
    }
    @PutMapping
    public Result getExamById(@NonNull @RequestParam String id){
        Exam res = examService.getExamById(id);
        System.out.println(res);
        if(res == null)
            return ResultUtil.error(null);
        return ResultUtil.success(res);
    }
    @PutMapping("/page")
    public Result getExamByPage(@NonNull @RequestParam Integer pageNum, @NonNull @RequestParam Integer pageSize){
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
