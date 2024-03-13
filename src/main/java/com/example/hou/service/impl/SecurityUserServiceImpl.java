package com.example.hou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.hou.entity.LogUser;
import com.example.hou.entity.LoginUserParam;
import com.example.hou.entity.SysUser;
import com.example.hou.entity.UserInfo;
import com.example.hou.mapper.SysUserMapper;
import com.example.hou.mapper.UserInfoMapper;
import com.example.hou.result.Result;
import com.example.hou.service.SecurityUserService;
import com.example.hou.util.JwtUtils;
import com.example.hou.util.RedisUtil;
import com.example.hou.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @program: Runner
 * @description:
 * @author: 作者
 * @create: 2023-12-03 17:49
 */

@Service
public class SecurityUserServiceImpl implements SecurityUserService {
    @Autowired
    SysUserMapper sysuserMapper;   //自己加的 为了update  可能越权

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public Result createUser(SysUser newUser) {
        // 1. 检查用户是否存在
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", newUser.getAccount());
        SysUser sysuser = sysuserMapper.selectOne(queryWrapper);
        if (sysuser != null) {
            return new Result(-100, "用户已存在", null);
        }

        //debug  注意逻辑问题  找不到已存在用户才对 那么
        // 需要new一个user来存 直接用上述sysuser会报空指针
        // 2. 创建用户   注意功能上在email时候已经加密过
        sysuserMapper.insert(newUser);

        return new Result(200, "用户创建成功", null);
    }





    @Override
    public Result login(LoginUserParam param) {

        // 1 获取AuthenticationManager 对象 然后调用 authenticate() 方法
        // UsernamePasswordAuthenticationToken 实现了Authentication 接口
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(param.getUserName(), param.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //2 认证没通过 提示认证失败
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("认证失败用户信息不存在");
        }

        //认证通过 使用userid 生成jwt token令牌
        LogUser loginUser = (LogUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getUserId().toString();

        Map<String, String> payloadMap = new HashMap<>();
        payloadMap.put("userId", userId);
        payloadMap.put("userName", loginUser.getUser().getUserName());
        payloadMap.put("token", JwtUtils.generateToken(payloadMap));

        boolean resultRedis = redisUtil.set("login:" + userId, loginUser);

        if (!resultRedis) {
            throw new RuntimeException("redis连接失败引起的登录失败");
        }


        return ResultUtil.success(payloadMap);
    }

    @Override
    public Result logOut() {

        // 1 获取 SecurityContextHolder 中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LogUser loginUser = (LogUser) authentication.getPrincipal();
        //2 删除redis 中的缓存信
        String key = "login:" + loginUser.getUser().getUserId().toString();
        redisUtil.del(key);
        return ResultUtil.success("退出成功!");
    }


    @Override
    public Result update(LoginUserParam user) {

        // 1. 从SecurityContext获取用户身份
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LogUser loginUser = (LogUser) authentication.getPrincipal();

        // 2. 检查用户是否存在并更新信息
        if (loginUser != null && loginUser.getUser() != null) {
            int userId = loginUser.getUser().getUserId();

            // 执行用户信息更新的逻辑
            // 这里的updateUserInfo需要根据自己的逻辑实现   用一下  mp
            QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId);
            SysUser sysuser = sysuserMapper.selectOne(queryWrapper);


            //修改内容
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String encode = bCryptPasswordEncoder.encode(user.getPassword());

            //System.out.println(user.getUserName()+">?????");
            // 创建一个SysUser 实体对象来保存要更新的值
            sysuser.setUserName(user.getUserName());
            sysuser.setPassword(encode);
            //查询条件
            UpdateWrapper<SysUser> userUpdateWrapper = new UpdateWrapper<>();
            userUpdateWrapper.eq("user_id", userId);

            // 执行更新操作
            int flag = sysuserMapper.update(sysuser, userUpdateWrapper);

            // 清除Redis缓存
            //String key = "login:"+loginUser.getUser().getUserId().toString();
            //redisUtil.del(key);
            if (flag == 1) {
                return new Result(200, "用户信息更新成功", null);
            } else {
                // 用户未找到或未登录
                return new Result(-100, "更新失败 ", null);
            }
        }
        return new Result(-100, "未登录或用户不存在", null);
    }


}

