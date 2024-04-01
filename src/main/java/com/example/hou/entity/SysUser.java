package com.example.hou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)//链式; 存取器。通过该注解可以控制getter和setter方法的形式。
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 915478504870211231L;//任何long均可 显式定义JVM序列化的类版本 用于成功反序列化

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;//注意long要和type = ID_WORKER匹配   或者int要type=IdType.AUTO  自增主键

    //账号 本项目设置为邮箱
    private String account;

    @TableField("user_name")  //mubatis plus一定要注意的坑  数据库里列的下划线和java的驼峰之间要映射
    //用户名
    private String userName;

    //用户密码
    private String password;

    @TableField("last_login_time")
    //上一次登录时间
    private Date lastLoginTime;

    //账号是否可用。默认为1（可用）
    private Boolean enabled;

    @TableField("account_not_expired")
    //是否过期。默认为1（没有过期）
    private Boolean accountNotExpired;

    @TableField("account_not_locked")
    //账号是否锁定。默认为1（没有锁定）
    private Boolean accountNotLocked;

    @TableField("credentials_not_expired")
    //证书（密码）是否过期。默认为1（没有过期）
    private Boolean credentialsNotExpired;

    @TableField("create_time")
    //创建时间
    private Date createTime;

    @TableField("update_time")
    //修改时间
    private Date updateTime;

    @TableField("avatar")
    // 头像URL属性  http地址
    private String avatar;


}
