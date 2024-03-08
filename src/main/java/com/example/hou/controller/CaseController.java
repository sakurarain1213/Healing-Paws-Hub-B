package com.example.hou.controller;

import com.example.hou.entity.Case;
import com.example.hou.result.Result;
import com.example.hou.service.CaseService;
import com.example.hou.util.ResultUtil;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/case")
public class CaseController {
    @Autowired
    private CaseService caseService;

    @PostMapping
    public Result createCase(@RequestBody Case req){
        if (req.getName() == null || req.getType() == null)return ResultUtil.error(0);
//        Integer num = caseService.createCase(req);
//        System.out.println(num);
//        if (num == 0)return ResultUtil.error(num);
//        return ResultUtil.success(num);
        Case created = caseService.createCase(req);
        if(created == null)return ResultUtil.error(null);
        return ResultUtil.success(created);
    }

    @PutMapping
    public Result updateCaseById(@RequestBody Case req){
        if (req.getId() == null)return ResultUtil.error(0);

        System.out.println(req.getId());

        Case updated = caseService.updateCaseById(req);
//        System.out.println(num);
//        if (num == 0)return ResultUtil.error(num);
        if(updated == null)return ResultUtil.error(null);
        return ResultUtil.success(updated);
    }

    @DeleteMapping
    public Result deleteCaseById(@NonNull @RequestParam String id){
        caseService.deleteCaseById(id);
        return ResultUtil.success();
    }

    @GetMapping
    public Result getCaseById(@NonNull @RequestParam String id){
        Case res = caseService.getCaseById(id);
        System.out.println(res);
        if(res == null)return ResultUtil.error(null);
        return ResultUtil.success(res);
    }

    @GetMapping("/page")
    public Result getCaseByPage(@NonNull @RequestParam Integer pageNum, @NonNull @RequestParam Integer pageSize){
        Page<Case> res = caseService.getCaseByPage(pageNum, pageSize);
        System.out.println(res.getTotalElements()); //集合中总数
        System.out.println("=========");
        System.out.println(res.getContent());
        System.out.println("=========");
        System.out.println(res.getTotalPages()); //按指定分页得到的总页数

        if(res == null)return ResultUtil.error(null);
        return ResultUtil.success(res.getContent());
    }




}
