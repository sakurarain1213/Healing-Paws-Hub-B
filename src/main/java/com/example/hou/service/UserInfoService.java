package com.example.hou.service;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hou.entity.UserInfo;



public interface UserInfoService /*extends IService<UserInfo>*/ {

    public UserInfo loginService(UserInfo user);

    public String registerService(UserInfo user);

    public String updateService(UserInfo user);


    public UserInfo getuserService(UserInfo user);


}
