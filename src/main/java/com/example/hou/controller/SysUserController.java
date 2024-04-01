package com.example.hou.controller;

import com.example.hou.entity.LoginUserParam;
import com.example.hou.result.Result;
import com.example.hou.service.SecurityUserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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

    @PostMapping("/update")
    public Result up(@RequestBody LoginUserParam param) {
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


}
