package com.example.hou.controller;

import com.example.hou.entity.LoginUserParam;
import com.example.hou.result.Result;
import com.example.hou.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//每个匿名能访问的接口都要 在security的config里允许访问！！！！   一般就是登录

/*

登录接口   返回token
47.103.113.75:8080/sysUser/login
{
    "userName":"888",
    "password":"123456"
}

直接退出会失败 需要先在header里加入token 和具体值  不需要body参数
47.103.113.75:8080/sysUser/logout



 */






@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    private SecurityUserService logService;
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

}
