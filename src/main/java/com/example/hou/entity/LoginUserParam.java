package com.example.hou.entity;

import lombok.Data;

/**
自定义参数实体   DTO   用于庞杂的request 后续考虑另开一个文件夹统一放
 */

@Data
public class LoginUserParam {

    //用户名   现在在login弃用这个
    private String userName;

    //用户账号
    private String account;

    //用户密码
    private String password;
}