package com.example.hou.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hou.entity.UserInfo;
import com.example.hou.mapper.UserInfoMapper;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hou.service.UserInfoService;

/**
 * @program: testhou
 * @description:
 * @author: 作者
 * @create: 2023-04-05 14:10
 */


@Service
public class UserInfoServiceImpl /* extends ServiceImpl<UserInfoMapper, UserInfo> */implements UserInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;
/*    也不用手搓
    public UserInfo getUserInfo(int id) {
        return userInfoMapper.getUserInfo(id);
    }

    public int deleteById(int id) {
        return userInfoMapper.deleteById(id);
    }

    public int Update(UserInfo userInfo) {
        return userInfoMapper.update(userInfo);
    }

    public UserInfo save(UserInfo userInfo) {
        int save = userInfoMapper.save(userInfo);
        return userInfo;
    }

    public List<UserInfo> selectAll() {
        return UserInfoMapper.selectAll();
    }
*/


    //尝试将两个string改成一个整体的对象
    /*
    public String loginService(String un, String pw) {
        UserInfo userInfo = userInfoMapper.searchByUsername(un);
        if (userInfo != null) {
            if (pw.equals(userInfo.getPassword())) {
                return "SUCCESS";
            } else {
                return "密码错误";
            }
        }
        return "此用户不存在";
    }
*/
    @Override
    public UserInfo loginService(UserInfo userInfo) {
        UserInfo userE = userInfoMapper.searchByUsername(userInfo.getUsername());
        if (userE != null) {
            if (userE.getPassword().equals(userInfo.getPassword())) {
                //return "SUCCESS";
                return userE;
            } else {
                userE.setUsername("密码错误");return userE;
                //return "密码错误";
            }
        }
        //return "此用户不存在";
       return null;
    }

    /*
    用string的登录版本    作为参考
        @Override
        public String loginService(String username,String password) {

            UserInfo userInfo = userInfoMapper.searchByUsername(username);
            if (userInfo != null) {
                if (password.equals(userInfo.getPassword())) {
                    return "SUCCESS";
                } else {
                    return "密码错误";
                }
            }
            return "此用户不存在";
        }

     */
    @Override
    public String registerService(UserInfo userInfo) {
        UserInfo userE = userInfoMapper.searchByUsername(userInfo.getUsername());
        if (userE == null) {
            if ("".equals(userInfo.getPassword())) {
                return "请输入密码";
            } else if ("".equals(userInfo.getUsername())) {
                return "请输入用户昵称";
            } else {
                userInfoMapper.insert(userInfo);
                return "SUCCESS";
            }
        }
        return "用户已存在";
    }

    @Override
    public String updateService(UserInfo user) {//传入的前端对象
        //用混合法  =对象+updatewrapper实现
        //传null要用lambda update wrapper  再说

        //找不到就直接返回错误
       // UserInfo userE = userInfoMapper.searchByUsername(user.getUsername());
       // if (userE == null) {return "用户不存在";}

        UserInfo temp = new UserInfo();
        //修改内容 包括密码！！
        temp.setPassword(user.getPassword());
        temp.setPhone(user.getPhone());
        temp.setGender(user.getGender());
        temp.setEmail(user.getEmail());
        //查询条件
        UpdateWrapper<UserInfo> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("username", user.getUsername());

        int flag = userInfoMapper.update(user, userUpdateWrapper);
        if (flag == 1) {
            return "SUCCESS";
        } else {
            return "用户不存在";
        }
    }


        @Override
        public UserInfo getuserService(UserInfo user){

            return userInfoMapper.searchByUsername(user.getUsername());

        }


        //顺便测试wrapper条件构造器的用法
        /*
    UserInfo userE = userInfoMapper.searchByUsername(userInfo.getUsername());
        if (userE != null) {
            UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();

            updateWrapper.eq("username", user.getUsername());//前端名字作为查询条件

            updateWrapper.set("phone", user.getPhone());//设置想要更新的字段 可以多个

            //updateWrapper.set("email", user.getEmail());
            // updateWrapper.set("gender", user.getGender());
            //updateWrapper.set("avatar", user.getAvatar());//存疑  由于是二进制文件
            userInfoMapper.update(null, updateWrapper);//这里的实体类设置为空
            //注意一个参数一定要设置null，这样就只会更新你set的字段。
            //这里需要实现前端json有什么就传什么
                return "SUCCESS";
            }
        return "用户不存在";
        */
        //可能null 用LambdaUpdateWrapper
        /*
// 1. 查询条件：商品标题中包含'学生白色丝袜'并且状态为上架的商品，并将符合条件的商品打上标签。
LambdaUpdateWrapper<MallxSpu> updateWrapper = new LambdaUpdateWrapper<>();
updateWrapper.like(MallxSpu::getTitle,"学生白色丝袜").eq(MallxSpu::getStatus,1).set(MallxSpu::getTag, "丝袜");

// 2. 执行更新
int result = mallxSpuMapper.update(null, updateWrapper);

       */
        /*
        UserInfo userE = userInfoMapper.searchByUsername(user.getUsername());
        if (userE != null) {
            LambdaUpdateWrapper<UserInfo> lambda = new UpdateWrapper<UserInfo>().lambda();
        //eq是指你查询的条件，set是指你修改的值
            lambda
                    .eq(UserInfo::getUsername, userE.getUsername())
                    .set(UserInfo::getPhone, user.getPhone());
            userInfoMapper.update(null, lambda);
            return "SUCCESS";
        }
        return "用户不存在";
    }*/
        /*
        UserInfo userE = userInfoMapper.searchByUsername(user.getUsername());//数据库对象
        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .eq("username", userE.getUsername())
                .set("phone", user.getPhone());
        int i = userInfoMapper.update(null, updateWrapper);
        if (i == 1) {
            return "SUCCESS";
        } else {
            return "用户不存在";
        }
    }
    */


}