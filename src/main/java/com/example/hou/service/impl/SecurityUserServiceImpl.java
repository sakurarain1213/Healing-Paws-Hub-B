package com.example.hou.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.hou.entity.*;
import com.example.hou.mapper.SysPermissionMapper;
import com.example.hou.mapper.SysUserMapper;
import com.example.hou.mapper.SysUserPermissionRelationMapper;
import com.example.hou.mapper.UserInfoMapper;
import com.example.hou.result.Result;
import com.example.hou.service.SecurityUserService;
import com.example.hou.util.FileUtil;
import com.example.hou.util.JwtUtils;
import com.example.hou.util.RedisUtil;
import com.example.hou.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.example.hou.util.FileUtil.fileUpload;

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
    SysUserPermissionRelationMapper relationMapper;

    @Autowired
    SysPermissionMapper permissionMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;//用于更新缓存的权限

    private static final String USER_PREFIX = "login:";


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




    //要求同时返回权限

    @Override
    public Result login(LoginUserParam param) {
        //前端需求  登录字段统一成 account  不要userName
        try {
            // 1 获取AuthenticationManager 对象 然后调用 authenticate() 方法
            // UsernamePasswordAuthenticationToken 实现了Authentication 接口
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(param.getAccount(), param.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);

            //2 认证没通过 提示认证失败
            if (Objects.isNull(authenticate)) {
                throw new RuntimeException("认证失败用户信息不存在");
            }
            //认证通过 使用userid 生成jwt token令牌
            LogUser loginUser = (LogUser) authenticate.getPrincipal();
            String userId = loginUser.getUser().getUserId().toString();

            //先更新user表的最新登录时间
            int id = loginUser.getUser().getUserId();
            QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId);
            SysUser sysuser = sysuserMapper.selectOne(queryWrapper);


            //有一个判断第一次登录的逻辑  todo  去检查email注册的时候不能加login time
            String isNew="";
            if(sysuser.getLastLoginTime()==null) isNew="NEW!用户第一次登录标记.";

            sysuser.setLastLoginTime(new Date());

            UpdateWrapper<SysUser> userUpdateWrapper = new UpdateWrapper<>();
            userUpdateWrapper.eq("user_id", userId);

            int flag = sysuserMapper.update(sysuser, userUpdateWrapper);
            if(flag !=1) return ResultUtil.error("更新登录时间失败");

            //再存储登录信息到redis

            Map<String, String> payloadMap = new HashMap<>();
            payloadMap.put("userId", userId);
            payloadMap.put("userName", loginUser.getUser().getUserName());

            //要求返回permission 但是要List转String 用join连接  逗号分隔
            List<String> permissions = loginUser.getPermissions();
            String permissionsString = String.join(",", permissions);
            payloadMap.put("permission", permissionsString);

            payloadMap.put("token",  JwtUtils.generateToken(payloadMap)); //在这里无状态地设置过期时间


            boolean resultRedis = redisUtil.set("login:" + userId, loginUser);

            if (!resultRedis) {
                throw new RuntimeException("redis连接失败导致登录失败");
            }

            return new Result(200,isNew+"Success",payloadMap);
        //--------------------------------------------------------------------
        } catch (BadCredentialsException e) {
            // 密码错误，直接返回错误信息
            return ResultUtil.error("密码错误");
        } catch (AuthenticationException e) {
            // 其他认证异常，返回通用认证失败信息
            return ResultUtil.error("认证失败，用户不存在");
        } catch (Exception e) {
            // 其他异常，可以根据需要处理或抛出
            throw new RuntimeException("认证过程中发生未知错误,见后端控制台输出", e);
        }

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


    //修改基本信息 包括密码  todo  DB的更新time属性需要刷新
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
            QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId);
            SysUser sysuser = sysuserMapper.selectOne(queryWrapper);


            //redis要同步修改---------------------------------------以下
            // Redis的头像信息也需要更新
            String userKey = USER_PREFIX + userId;
            String userJson = redisTemplate.opsForValue().get(userKey);
            if (userJson == null) {
                throw new RuntimeException("User not found in Redis for ID: " + userId);
            }
            // 反序列化用户对象
            LogUser loguser = JSON.parseObject(userJson,LogUser.class);
            //注意 logUser里面的user的avatar才是需要改的属性  要两层
            SysUser temp=loguser.getUser();
            //redis要同步修改---------------------------------------以上


            // 检查是否有account字段需要更新
            if (user.getAccount() != null && !user.getAccount().isEmpty()) {
                sysuser.setAccount(user.getAccount());
                //redis同步
                temp.setAccount(user.getAccount());
            }

            // 检查是否有username字段需要更新
            if (user.getUserName() != null && !user.getUserName().isEmpty()) {
                sysuser.setUserName(user.getUserName());
                //redis同步
                temp.setUserName(user.getUserName());
                //考虑为什么外层没有username
            }

            //检查是否有密码要改
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                String encode = bCryptPasswordEncoder.encode(user.getPassword());
                sysuser.setPassword(encode);

                //redis同步
                temp.setPassword(encode);
                //考虑为什么外层没有password
            }

            //redis要同步修改---------------------------------------以下
            loguser.setUser(temp);
            // 序列化更新后的用户对象
            String updatedUserJson = JSON.toJSONString(loguser, SerializerFeature.WriteClassName);
            // 将更新后的用户数据存回Redis
            redisTemplate.opsForValue().set(userKey, updatedUserJson);
            //redis要同步修改---------------------------------------以上


            //数据库的更新
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



    //注意传的permission是中文的name  不是sys:user
    @Override
    public Result setUserPermission(String permission){
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LogUser loginUser = (LogUser) authentication.getPrincipal();

        if (loginUser != null && loginUser.getUser() != null) {
            int userId = loginUser.getUser().getUserId();

            //先删已有权限
            QueryWrapper<SysUserPermissionRelation> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId);
            relationMapper.delete(queryWrapper);

            // 再查新权限id  注意权限名和数据库一致
            QueryWrapper<SysPermission> permissionWrapper = new QueryWrapper<>();
            permissionWrapper.eq("permission_name", permission);
            SysPermission sp = permissionMapper.selectOne(permissionWrapper);

            if (sp == null)   return new Result(-100, "对应权限不存在", null);

            //注意redis缓存里的permission要改
            //把HouApplicationTest的redis方法移动到这里即可
                // 1.从Redis获取用户数据
            String userKey = USER_PREFIX + userId;
            String userJson = redisTemplate.opsForValue().get(userKey);
            //System.out.println(userJson);
                //{"@type":"com.example.hou.entity.LogUser","accountNonExpired":true,"accountNonLocked":true,"credentialsNonExpired":true,"enabled":true,"password":"$2a$10$zpIj12VJUwVQAHAFJHjaaOwDl9ZkzYb21oA7a6s.Q6AN9PC7BX4Ka","permissions":["sys:queryUser"],"user":{"account":"w1625154105@163.com","accountNotExpired":true,"accountNotLocked":true,"avatar":"http://150.158.110.63:8080/images/678be954-d1a8-45b0-879b-b514653b44a1_2024-04-01-20-28-01_123.png","createTime":1710226628000,"credentialsNotExpired":true,"enabled":true,"lastLoginTime":1711976695000,"password":"$2a$10$zpIj12VJUwVQAHAFJHjaaOwDl9ZkzYb21oA7a6s.Q6AN9PC7BX4Ka","updateTime":1710226628000,"userId":3}}
            if (userJson == null) {
                throw new RuntimeException("User not found in Redis for ID: " + userId);
            }
                // 2.反序列化用户对象
                LogUser user = JSON.parseObject(userJson,LogUser.class);
                List<String> newPermissions = new ArrayList<>(); // 创建一个新的ArrayList实例
                newPermissions.add(sp.getPermissionCode());//redis存的是permission的英文码
                user.setPermissions(newPermissions);
                // 4.序列化更新后的用户对象
                String updatedUserJson = JSON.toJSONString(user, SerializerFeature.WriteClassName);
                // 5.将更新后的用户数据存回Redis
                redisTemplate.opsForValue().set(userKey, updatedUserJson);
                // 处理异常，例如序列化和反序列化错误
                // throw new RuntimeException("Error updating user permissions 未找到用户 token缓存过期", e);


            //插入新的关系到数据库
            SysUserPermissionRelation relation = new SysUserPermissionRelation();
            relation.setUserId(userId);
            relation.setPermissionId(sp.getPermissionId());
            int res=relationMapper.insert(relation);

            if (res >0)  return new Result(200, "权限更新成功", null);
            else return new Result(-100, "权限更新失败", null);

        }
        return new Result(-100, "未登录或用户不存在", null);

    }

    @Override
    public Result updateAvatar(MultipartFile avatarFile){

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LogUser loginUser = (LogUser) authentication.getPrincipal();

        //检查用户是否存在
        if (loginUser != null && loginUser.getUser() != null) {
            int userId = loginUser.getUser().getUserId();

            String url= FileUtil.fileUpload(avatarFile);  //利用文件工具类方法实现
            if(url==null) return new Result(-100, "头像上传失败,请检查文件大小和后缀", null);
            //已经保存好头像 并生成url
            //先根据id找到原始url对应的头像地址 删除文件本身  （可以先不做）
            QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId);
            SysUser sysuser = sysuserMapper.selectOne(queryWrapper);

            if (sysuser.getAvatar() != null && !sysuser.getAvatar().isEmpty()) {
                 FileUtil.deleteFile(sysuser.getAvatar()); //文件工具类删除逻辑
            }


            // Redis的头像信息也需要更新
            String userKey = USER_PREFIX + userId;
            String userJson = redisTemplate.opsForValue().get(userKey);
            if (userJson == null) {
                throw new RuntimeException("User not found in Redis for ID: " + userId);
            }
            // 反序列化用户对象
            LogUser loguser = JSON.parseObject(userJson,LogUser.class);
            //注意 logUser里面的user的avatar才是需要改的属性  要两层
            SysUser temp=loguser.getUser();
            temp.setAvatar(url);
            loguser.setUser(temp);
            // 序列化更新后的用户对象
            String updatedUserJson = JSON.toJSONString(loguser, SerializerFeature.WriteClassName);
            // 将更新后的用户数据存回Redis
            redisTemplate.opsForValue().set(userKey, updatedUserJson);



            //数据库里 再用新url覆盖avatar字段值
            sysuser.setAvatar(url);
            int flag = sysuserMapper.updateById(sysuser);

            if (flag == 1) {
                return new Result(200, "头像更新成功", url);
            } else {
                // 用户未找到或未登录
                return new Result(-100, "头像更新失败", null);
            }
        }
        return new Result(-100, "未登录或用户不存在", null);


    }


}
