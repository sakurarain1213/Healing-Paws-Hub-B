package com.example.hou.controller;

import com.example.hou.entity.Disease;
import com.example.hou.entity.PageSupport;
import com.example.hou.result.Result;
import com.example.hou.service.DiseaseService;
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
import java.util.List;

@RestController
@RequestMapping("/disease")
@Validated
public class DiseaseController {
    @Autowired
    private DiseaseService diseaseService;

    @PostMapping
    public Result createDisease(@NonNull @RequestBody @Valid Disease disease){
        if (disease.getName() == null || disease.getType() == null)return ResultUtil.error("缺少必需参数");
        Disease exist = diseaseService.findByName(disease.getName());
        if (exist != null)return ResultUtil.error("已存在该病名");

        Disease created = diseaseService.createDisease(disease);
        System.out.println(created.getId());
        if(created.getId() == null)return ResultUtil.error(null);
        return ResultUtil.success(created);
    }

    @DeleteMapping
    public Result deleteById(@NotBlank @Size(min = 24, max = 24, message = "id不合法") @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
                             @RequestParam("id") String id){
        try {
            int flag = diseaseService.deleteById(id);
            if (flag < 0)return ResultUtil.error("id不存在");
            System.out.println("delete:"+id);
            return ResultUtil.success(id);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error("删除异常");
        }
    }

    @PutMapping
    public Result updateById(@NonNull @RequestBody @Valid Disease disease){
        if(disease.getId() == null)return ResultUtil.error("缺少必需参数");
        if(disease.getName() == null && disease.getType() == null)return ResultUtil.error("未填写任何需要更新的信息");

        if(disease.getName() != null){
            Disease exist = diseaseService.findByName(disease.getName());
            if (exist != null)return ResultUtil.error("已存在该病名");
        }


        long res = diseaseService.updateById(disease);
        if (res <= 0)return ResultUtil.error(null);
        return ResultUtil.success(res);
    }

    @GetMapping
    public Result getByPage(@NonNull @RequestParam("pageNum") Integer pageNum,
                            @NonNull @RequestParam("pageSize") Integer pageSize){
        if(pageNum < 1 || pageSize < 1)return ResultUtil.error("pageNum或pageSize不合法");

        Page<Disease> res = diseaseService.getByPage(pageNum, pageSize);
        if(res == null)return ResultUtil.error(null);
        System.out.println(res.getContent());

        PageSupport<Disease> respPage = new PageSupport<>();
        respPage.setListData(res.getContent())
                .setTotalPages(res.getTotalPages());

        return ResultUtil.success(respPage);
//        return ResultUtil.success(res.getContent());
    }

    @GetMapping("/belong")
    public Result getPageByType(@NonNull @RequestParam("pageNum") Integer pageNum,
                                @NonNull @RequestParam("pageSize") Integer pageSize,
                                @NotBlank @RequestParam("type")  @Size(max = 30, message = "type不合法")
                                @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$", message = "type为中文、英文、数字、下划线组合")
                                String type){
        if(pageNum < 1 || pageSize < 1)return ResultUtil.error("pageNum或pageSize不合法");

        List<Disease> res = diseaseService.getPageByType(pageNum, pageSize, type);
        System.out.println(res);
        if (res == null)return ResultUtil.error(null);

        long total = (diseaseService.getPageByTypeCount(pageNum, pageSize, type) + pageSize - 1) / pageSize;;

        PageSupport<Disease> respPage = new PageSupport<>();
        respPage.setListData(res)
                .setTotalPages((int)total);

        return ResultUtil.success(respPage);

//        return ResultUtil.success(res);
    }






}
