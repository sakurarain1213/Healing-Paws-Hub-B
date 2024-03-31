package com.example.hou.service;

import com.example.hou.entity.LoginUserParam;
import com.example.hou.entity.SysUser;
import com.example.hou.result.Result;

/**
 * @program: Runner
 * @description:
 * @author: 作者
 * @create: 2023-12-03 17:48
 */
public interface SecurityUserService {

    //集成在email注册里  现在只用于邮箱注册
    Result createUser(SysUser newUser);

    Result login(LoginUserParam param);

    Result logOut();

    Result update(LoginUserParam user);

    //token和用户信息由前端提供  只需要permission的名字作为参数
    Result setUserPermission(String permission);


}
