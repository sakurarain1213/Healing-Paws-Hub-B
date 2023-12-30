package com.example.hou.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//在controller层控制用户能访问的接口权限
@RestController
@RequestMapping("/testPreAuthorize")
public class TestPreAuthorizeController {


    @PostMapping("/hello")
    // 只有sys:queryUser 权限才能访问
    //@PreAuthorize("hasAuthority('sys:queryUser')") //这是没有自定义权限校验方法的默认写法
    @PreAuthorize("@syex.hasAuthority('sys:queryUser')")
    public String hello(){

        return "hello";
    }

    @PostMapping("/hello2")
    // 只有sys:queryUser2 权限才能访问
    @PreAuthorize("@syex.hasAuthority('sys:queryUser2')")
    public String hello2(){

        return "hello2";
    }
}
