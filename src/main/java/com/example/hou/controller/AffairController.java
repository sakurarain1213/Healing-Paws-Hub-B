package com.example.hou.controller;

import com.example.hou.entity.*;
import com.example.hou.result.Result;
import com.example.hou.service.AffairService;
import com.example.hou.util.ResultUtil;
import com.example.hou.validator.AffairCreateGroup;
import com.example.hou.validator.AffairUpdateGroup;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
@RequestMapping("/affair")
@Validated
public class AffairController {
    @Autowired
    private AffairService affairService;

    @PostMapping
    @Validated(AffairCreateGroup.class)
    public Result createAffair(@NonNull @Valid @RequestBody Affair affair){
        if (affair.getName() == null ||
            affair.getDescription() == null ||
            affair.getRole() == null ||
            affair.getAffairs() == null) return  ResultUtil.error("缺少必需参数");

        Affair created = affairService.createAffair(affair);
        System.out.println(created.getId());
        if (created.getId() == null)return ResultUtil.error(null);
        return ResultUtil.success(created);
    }

    @DeleteMapping
    public Result deleteById(@NotBlank @Size(min = 24, max = 24, message = "id不合法") @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
                             @RequestParam("id") String id){
        try {
            int flag = affairService.deleteById(id);
            if (flag < 0) ResultUtil.error("id不存在");
            System.out.println(id);
            return ResultUtil.success(id);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error("删除异常");
        }
    }

    @PutMapping
    @Validated(AffairUpdateGroup.class)
    public Result updateById(@NonNull @Valid @RequestBody Affair affair){
        if (affair.getId() == null)return ResultUtil.error("缺少必需参数");
        if(affair.nullFieldsExceptId())return ResultUtil.error("未填写任何需要更新的信息");

        long res = affairService.updateById(affair);
        if (res <= 0)return ResultUtil.error(null);
        return ResultUtil.success(res);
    }


    @GetMapping
    public Result getByPage(@NonNull @RequestParam("pageNum") Integer pageNum,
                            @NonNull @RequestParam("pageSize") Integer pageSize){
        if(pageNum < 1 || pageSize < 1)return ResultUtil.error("pageNum或pageSize不合法");

        Page<Affair> page = affairService.getByPage(pageNum, pageSize);
        if (page == null)return ResultUtil.error(null);
        System.out.println(page.getContent());
        return ResultUtil.success(page.getContent());
    }

    @GetMapping("/subs")
    public Result getAllNodesByAffairid(@NotBlank @Size(min = 24, max = 24, message = "id不合法") @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
                                        @RequestParam("affairId") String affairId){
        List<AffairNode> nodes = affairService.getAllNodesByAffairid(affairId);
        if(nodes == null)return ResultUtil.error(null);

        return ResultUtil.success(nodes);
    }

    /**
     * 返回指定count个推荐的Affair
     *
     */
    @PostMapping("/recommend")
    public Result getRecommendAffairs(@RequestBody AffairRecomendVo affairRecomendVo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated())return ResultUtil.error("未登录");

        LogUser loginUser = (LogUser) authentication.getPrincipal();
        Integer userId = Optional.ofNullable(loginUser)
                .map(LogUser::getUser)
                .map(SysUser::getUserId)
                .orElse(null);
        if (userId == null)return ResultUtil.error("未登录");
        Integer count = affairRecomendVo.getCount();
        if(count == null)return ResultUtil.error("缺少必需参数");
        if(count <= 0)return ResultUtil.error("count不合法");

        System.out.println("userId: " + userId);
        System.out.println("count: "+count);

//        System.out.println(loginUser);
//        System.out.println(loginUser.getPermissions());
//        return ResultUtil.success(loginUser);

        List<Affair> recommendAffairs = affairService.getRecommendAffairs(loginUser, count);
        if (recommendAffairs == null)return ResultUtil.error(null);

        recommendAffairs.stream().forEach(System.out::println);
        return ResultUtil.success(recommendAffairs);
    }


    /**
     * 模糊搜索匹配 name、description
     */
    @GetMapping("/fuzzy")
    public Result getFuzzyMatchedAffairs(@NotBlank @Size(max = 100, message = "input不合法")
                                         @RequestParam("input") String input){
        int pageNum = 0, pageSize = 10;
        List<Affair> affairs = affairService.getFuzzyMatchedAffairs(input, pageNum, pageSize);
        System.out.println(affairs);
        if (affairs == null)return ResultUtil.error(null);
        System.out.println(affairs.size());
        return ResultUtil.success(affairs);

    }










}
