package com.example.hou.controller;

import com.example.hou.entity.LogUser;
import com.example.hou.entity.LoginUserParam;
import com.example.hou.entity.SysUser;
import com.example.hou.mapper.SysUserMapper;
import com.example.hou.result.Result;
import com.example.hou.service.SecurityUserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


//每个匿名能访问的接口都要 在security的config里允许访问！！！！   一般就是登录

/*

登录接口   返回token
localhost:8080/sysUser/login
47.103.113.75:8080/sysUser/login
{
    "userName":"888",
    "password":"123456"
}

直接退出会失败 需要先在header里加入token 和具体值  不需要body参数
47.103.113.75:8080/sysUser/logout



localhost:8080/sysUser/update
47.103.113.75:8080/sysUser/update
{
    "userName":"888",
    "password":"123"
}



 */


@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    private SecurityUserService logService;

    @Autowired
    SysUserMapper sysuserMapper;   //为了get方法的方便


    /*自定义注册放到了邮箱注册服务 暂时不移到service层写*/

    //TODO 需要在注册的同时去自动赋予普通权限 更改用户表信息  需要标准格式返回 okk
    //只是login的密码错误返回需要
    /*自定义登录*/
    @PostMapping("/login")
    public Result login(@RequestBody LoginUserParam param) {
        return logService.login(param);
    }
    /*自定义登出*/
    @PostMapping("/logout")
    public Result logOut() {
        return logService.logOut();
    }


    //可以同时更新三者  可以传任意个属性
    @PostMapping("/update")
    public Result updateAccountUsernamePassword(@RequestBody LoginUserParam param) {
        return logService.update(param);
    }



    @PostMapping("/setPermission")
    public Result setPermission(@NonNull @RequestParam("permission")String p ) {
        return logService.setUserPermission(p);
    }

    @PostMapping("/setAvatar")
    public Result setAvatar(@NonNull @RequestParam("avatar") MultipartFile ava ) {
        return logService.updateAvatar(ava);
    }

    //直接get用户基本信息
    @GetMapping
    public Result getBaseInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 检查用户是否已经登录
        if (authentication.isAuthenticated()) {
            Object o = authentication.getPrincipal();
            if (o instanceof LogUser) {
                LogUser logUser = (LogUser) o;
                int userId = logUser.getUser().getUserId();
                //可以拿到各种信息
                SysUser u=sysuserMapper.selectById(userId);
                if (u == null) {
                    // 用户不存在或其他数据库查询错误
                    return new Result(-200, "用户不存在或查询出错，无法获取用户基本信息", null);
                }

                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("username", u.getUserName());
                userInfo.put("account", u.getAccount());
                userInfo.put("avatar", u.getAvatar());

                // 构造Result对象并返回
                return new Result(200,"成功得到用户基本信息",userInfo);

            } else {
                // 用户未登录，处理未登录情况
                return new Result(-100,"基本信息获取失败，未登录",null);
            }
        }
        return new Result(-100,"基本信息获取失败，未认证",null);

    }



}
