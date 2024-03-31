package com.example.hou.controller;

import com.example.hou.entity.Case;
import com.example.hou.entity.Question;
import com.example.hou.result.Result;
import com.example.hou.service.QuestionService;
import com.example.hou.util.ResultUtil;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("/question")
@Validated
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @PostMapping
    public Result createQuestion(@RequestBody @NonNull @Valid Question req){
        if(req.missingRequiredFields())
            return ResultUtil.error("缺少必须字段");
        if(questionService.existErrorDisease(req.getType()))
            return ResultUtil.error("病的ID有误");
        if(req.getScore() <= 0)
            return ResultUtil.error("分数<=0");

        Question created = questionService.createQuestion(req);
        if(created == null)
            return ResultUtil.error(null);
        return ResultUtil.success(created);
    }
    @PutMapping
    public Result updateQuestionById(@RequestBody @NonNull @Valid Question req){
        if (req.getId() == null)
            return ResultUtil.error(0);

        System.out.println(req.getId());
//        System.out.println(req.missingRequiredFields());
        if(req.missingAllRequiredFields() && req.getDetail() == null)
            return ResultUtil.error("未填写任何需要更新的信息");
        if(req.getType() != null && questionService.existErrorDisease(req.getType()))
            return ResultUtil.error("病的ID有误");
        if(req.getScore() <= 0)
            return ResultUtil.error("分数<=0");

        Long res = questionService.updateQuestion(req);
        if (res == null || res == 0)
            return ResultUtil.error(null);
        return ResultUtil.success(res);
    }

    @DeleteMapping
    public Result deleteQuestionById(@NotBlank(message = "id不能是空串或只有空格")
                                         @Size(min = 24, max = 24, message = "id不合法")
                                         @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
                                         @RequestParam("id")
                                         String id){
        questionService.deleteQuestionById(id);
        System.out.println("delete by id: " + id);
        return ResultUtil.success();
    }

    @GetMapping
    public Result getQuestionById(@NotBlank(message = "id不能是空串或只有空格")
                                      @Size(min = 24, max = 24, message = "id不合法")
                                      @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
                                      @RequestParam("id") String id){
        Question res = questionService.getQuestionById(id);
        System.out.println(res);
        if(res == null)return ResultUtil.error(null);
        return ResultUtil.success(res);
    }

    @GetMapping("/page")
    public Result getQuestionByPage(@NonNull @RequestParam("pageNum") Integer pageNum,
                                    @NonNull @RequestParam("pageSize") Integer pageSize){
        if(pageNum < 1 || pageSize < 1)return ResultUtil.error("pageNum或pageSize不合法");

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
    public Result getQuestionByGroup(@NotBlank
                                         @RequestParam("diseases")
                                         @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9 ]+$", message = "diseases为中文、英文、空格组合") String diseases, @NonNull @RequestParam Integer pageNum, @NonNull @RequestParam Integer pageSize) {
        if(pageNum < 1 || pageSize < 1)return ResultUtil.error("pageNum或pageSize不合法");
        Page<Question> res = questionService.getQuestionByGroup(pageNum, pageSize, diseases);
        System.out.println("集合中总数：" + res.getTotalElements()); //集合中总数
        System.out.println("=========");
        System.out.println(res.getContent());
        System.out.println("=========");
        System.out.println("指定分页中总数：" + res.getTotalPages()); //按指定分页得到的总页数

        if (res == null) return ResultUtil.error(null);
        return ResultUtil.success(res.getContent());
    }
}
