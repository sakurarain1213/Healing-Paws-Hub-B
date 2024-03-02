package com.example.hou.entity;

import lombok.Data;

/**
自定义参数实体
 */

@Data
public class LoginUserParam {

    //用户名
    private String userName;

    //用户密码
    private String password;
}