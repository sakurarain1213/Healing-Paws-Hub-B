package com.example.hou.controller;

import com.example.hou.entity.*;
import com.example.hou.result.Result;
import com.example.hou.service.AffairRecordService;
import com.example.hou.util.ResultUtil;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/affairrecord")
@Validated
public class AffairRecordController {
    @Autowired
    private AffairRecordService affairRecordService;

    @PostMapping
    public Result createAffairRecord(@NonNull @Valid @RequestBody AffairRecCreateVo createVo){
//        获取当前登录user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated())return ResultUtil.error("未登录");

        LogUser loginUser = (LogUser) authentication.getPrincipal();
        Integer userId = Optional.ofNullable(loginUser)
                                .map(LogUser::getUser)
                                .map(SysUser::getUserId)
                                .orElse(null);
        if (userId == null)return ResultUtil.error("未登录");

        System.out.println("affairId: "+createVo.getAffairId());
        System.out.println("userId: "+userId);

        AffairRecord created = affairRecordService.createAffairRecord(createVo.getAffairId(), userId);
        if(created == null)return ResultUtil.error(null);
        if (created.getId() == null)return ResultUtil.error(null);
        return ResultUtil.success(created);
    }

    @GetMapping("/page")
    public Result getByLateSortedPage(@NonNull @RequestParam("pageNum") Integer pageNum,
                            @NonNull @RequestParam("pageSize") Integer pageSize){
        if(pageNum < 1 || pageSize < 1)return ResultUtil.error("pageNum或pageSize不合法");

        List<AffairRecord> records = affairRecordService.getByLateSortedPage(pageNum, pageSize);
        if (records == null)return ResultUtil.error(null);

        long total = (affairRecordService.getByLateSortedPageCount(pageNum, pageSize) + pageSize - 1) / pageSize;
        System.out.println(total);

        PageSupport<AffairRecord> respPage = new PageSupport<>();
        respPage.setListData(records)
                .setTotalPages((int)total);

        return ResultUtil.success(respPage);
//        return ResultUtil.success(records);
    }

    @DeleteMapping
    public Result deleteById(@NotBlank @Size(min = 24, max = 24, message = "id不合法") @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
                             @RequestParam("id") String id){
        try {
            int flag = affairRecordService.deleteById(id);
            if (flag < 0) return ResultUtil.error("id不存在");
            System.out.println("delete:" + id);
            return ResultUtil.success(id);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error("删除异常");
        }
    }


}
