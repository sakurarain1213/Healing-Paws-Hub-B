package com.example.hou.service;

import com.example.hou.entity.LoginUserParam;
import com.example.hou.result.Result;

/**
 * @program: Runner
 * @description:
 * @author: 作者
 * @create: 2023-12-03 17:48
 */
public interface SecurityUserService {

    Result login(LoginUserParam param);

    Result logOut();




}
