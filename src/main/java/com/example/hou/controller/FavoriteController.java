package com.example.hou.controller;

import com.example.hou.entity.*;
import com.example.hou.result.Result;
import com.example.hou.service.FavoriteService;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/favorite")
@Validated
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @PostMapping
    public Result createFavorite(@NonNull @Valid @RequestBody Favorite createVo){
        if (createVo.getObjectId() == null || createVo.getObjectType() == null)return ResultUtil.error("缺少必需参数");
        String objType = createVo.getObjectType();
        if (!objType.equals("item") && !objType.equals("affair") && !objType.equals("affairNode")) return ResultUtil.error("objectType不合法");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated())return ResultUtil.error("未登录");

        LogUser loginUser = (LogUser) authentication.getPrincipal();
        Integer userId = Optional.ofNullable(loginUser)
                .map(LogUser::getUser)
                .map(SysUser::getUserId)
                .orElse(null);

        if (userId == null)return ResultUtil.error("未登录");

        createVo.setUserId(userId)
                .setCreatedAt(new Date());
        return favoriteService.createFavorite(createVo);
    }

    @DeleteMapping
    public Result deleteById(@NotBlank @Size(min = 24, max = 24, message = "id不合法") @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
                             @RequestParam("id") String id){
        try {
            int flag = favoriteService.deleteById(id);
            if(flag < 0)return ResultUtil.error("id不存在");
            System.out.println(id);
            return ResultUtil.success(id);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error("删除异常");
        }
    }


    @GetMapping
    public Result getByPage(@NonNull @RequestParam("pageNum") Integer pageNum,
                            @NonNull @RequestParam("pageSize") Integer pageSize){
        if(pageNum < 1 || pageSize < 1)return ResultUtil.error("pageNum或pageSize不合法");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated())return ResultUtil.error("未登录");

        LogUser loginUser = (LogUser) authentication.getPrincipal();
        Integer userId = Optional.ofNullable(loginUser)
                .map(LogUser::getUser)
                .map(SysUser::getUserId)
                .orElse(null);

        if (userId == null)return ResultUtil.error("未登录");

        List<FavoriteDTO> res = favoriteService.getByPage(userId, pageNum, pageSize);
        res.stream().forEach(System.out::println);

        long total = (favoriteService.getTotalPageCount(userId, pageNum, pageSize) + pageSize - 1) / pageSize;
        System.out.println("total:" + total);

        PageSupport<FavoriteDTO> respPage = new PageSupport<>();
        respPage.setListData(res)
                .setTotalPages((int)total);

        return ResultUtil.success(respPage);
    }

    @GetMapping("/favored")
    public Result judgeFavored(@Size(max = 100, message = "objectType不合法")
                               @RequestParam("objectType") String objectType,
                               @Size(min = 24, max = 24, message = "objectId不合法") @Pattern(regexp = "^[a-z0-9]+$", message = "objectId不合法")
                               @RequestParam("objectId") String objectId){
        if (!objectType.equals("item") && !objectType.equals("affair") && !objectType.equals("affairNode")) return ResultUtil.error("objectType不合法");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated())return ResultUtil.error("未登录");

        LogUser loginUser = (LogUser) authentication.getPrincipal();
        Integer userId = Optional.ofNullable(loginUser)
                .map(LogUser::getUser)
                .map(SysUser::getUserId)
                .orElse(null);

        if (userId == null)return ResultUtil.error("未登录");

        boolean res = favoriteService.judgeFavored(userId, objectType, objectId);
        System.out.println(res);
        return ResultUtil.success(res);
    }






}