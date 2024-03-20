package com.example.hou.controller;


import com.example.hou.entity.LogUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*

localhost:8080/testPreAuthorize/hello
{

}



*/


//在controller层控制用户能访问的接口权限
@RestController
@RequestMapping("/testPreAuthorize")
public class TestPreAuthorizeController {


    @PostMapping("/hello")
    // 只有sys:queryUser 权限才能访问
    //@PreAuthorize("hasAuthority('sys:queryUser')") //这是没有自定义权限校验方法的默认写法
    @PreAuthorize("@syex.hasAuthority('sys:queryUser')")
    public String hello(){

        //在任何函数都可以自由截获token  解析出信息 再作为一部分返回
        // 获取当前用户的身份验证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 检查用户是否已经登录
        if (authentication.isAuthenticated()) {
            Object o = authentication.getPrincipal();
            if (o instanceof LogUser) {
                LogUser logUser = (LogUser) o;
                int userId = logUser.getUser().getUserId();
                //可以拿到各种信息
                System.out.println("User ID: " + userId);
            } else {
                // 用户未登录，处理未登录情况
                System.out.println("nooooooooooooooooo");
            }
        }


        return "hello";
    }

    @PostMapping("/hello2")
    // 只有sys:queryUser2 权限才能访问    可以有and或者or的逻辑拼接  权限任意
    @PreAuthorize("@syex.hasAuthority('sys:queryUser2')and @syex.hasAuthority('sys:queryUser2') ")
    public String hello2(){
        return "hello2";
    }
}
