package com.example.hou.controller;

import com.example.hou.entity.Case;
import com.example.hou.entity.PageSupport;
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
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/question")
@Validated
public class QuestionController {
    // 当标注的属性是接口时，其实注入的是这个接口的实现类， 如果这个接口有多个实现类，只使用@Autowired就会报错
    @Autowired
    private QuestionService questionService;

    @PostMapping
    public Result createQuestion(@RequestBody @NonNull @Valid Question req){
        if(req.missingRequiredFields())
            return ResultUtil.error("缺少必须字段");
        if(questionService.existErrorDisease(req.getType()))
            return ResultUtil.error("所属diseases的Name有误");
        if(req.questionTypeNotMatch())
            return ResultUtil.error("questionType与answer不对应");

        Question created = questionService.createQuestion(req);
        if(created == null)
            return ResultUtil.error(null);
        return ResultUtil.success(created);
    }
    @PutMapping
    public Result updateQuestionById(@RequestBody @NonNull @Valid Question req){
        if (req.getId() == null)
            return ResultUtil.error(null);

        System.out.println(req.getId());

        if(req.missingAllRequiredFields())
            return ResultUtil.error("未填写任何需要更新的信息");
        if(req.getType() != null && questionService.existErrorDisease(req.getType()))
            return ResultUtil.error("病的Name有误");

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
        try {
            boolean judge = questionService.deleteQuestionById(id);
            if(!judge){
                System.out.println("删除Question失败，id: " + id + " 不存在");
                return ResultUtil.error("id不存在");
            }
            System.out.println("删除Question成功：" + id);
            return ResultUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(null);
        }
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

        PageSupport<Question> res = questionService.getQuestionByPage(pageNum, pageSize);

        if(res == null)return ResultUtil.error(null);

        System.out.println("总页数：" + res.getTotalPages()); //按指定分页得到的总页数

        return ResultUtil.success(res);
    }

    @GetMapping("/group")
    //修改一下  应该传string 而不是list 后端手工分词
    public Result getQuestionByGroup(@RequestParam(value = "diseases", required = false)
                                     String diseases,
                                     @NonNull @RequestParam Integer pageNum,
                                     @NonNull @RequestParam Integer pageSize) {
        if(pageNum < 1 || pageSize < 1)return ResultUtil.error("pageNum或pageSize不合法");

        if(diseases==null||diseases.equals("")) {
            return ResultUtil.success(questionService.getQuestionByPage(pageNum, pageSize));
        }
        //debug
        // 定义一个正则表达式，匹配空格、分号、逗号或点号等等
        String regex = "[\\s;,.`|]";
        // 使用split方法根据正则表达式分割字符串
        String[] splitDiseases = diseases.split(regex);
        // 将字符串数组转换为List<String>
        List<String> diseaseList = Arrays.asList(splitDiseases);

        if(questionService.existErrorDisease(diseaseList))
            return ResultUtil.error("病的Name有误,请检查是否有空格");

        PageSupport<Question> res = questionService.getQuestionByGroup(pageNum, pageSize, diseaseList);

        if (res == null) return ResultUtil.error(null);

        System.out.println("总页数：" + res.getTotalPages()); //按指定分页得到的总页数

        return ResultUtil.success(res);
    }

    /*@GetMapping("/group")
    public Result getQuestionByGroup(@RequestParam(value = "diseases", required = false)
                                         @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9 ]+$", message = "diseases为中文、英文、空格组合")
                                         String diseases,
                                     @NonNull @RequestParam Integer pageNum,
                                     @NonNull @RequestParam Integer pageSize) {
        if(pageNum < 1 || pageSize < 1)return ResultUtil.error("pageNum或pageSize不合法");

        Page<Question> res = questionService.getQuestionByGroup(pageNum, pageSize, diseases);

        if (res == null) return ResultUtil.error(null);

        System.out.println("集合中总数：" + res.getTotalElements()); //集合中总数
        System.out.println("=========");
        System.out.println(res.getContent());
        System.out.println("=========");
        System.out.println("指定分页中总数：" + res.getTotalPages()); //按指定分页得到的总页数

        return ResultUtil.success(res.getContent());
    }*/
}
