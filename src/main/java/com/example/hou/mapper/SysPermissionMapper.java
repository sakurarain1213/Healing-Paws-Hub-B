package com.example.hou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hou.entity.SysPermission;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /*
     通过用户id查询用户的权限数据
     */

    @Select({
            " SELECT p.* FROM"+
            " sys_user u"+
            " LEFT JOIN sys_user_permission_relation r ON u.user_id = r.user_id"+
            " LEFT JOIN sys_permission p ON r.permission_id = p.permission_id"+
            " WHERE u.user_id = #{userId}  AND  p.permission_name IS NOT NULL "
    })
    //复杂查询建议还是用sql和 @Select    不要写mp的wrapper了
    //重大debug   select已经找到 Row: 1, sys:queryUser, 查询用户, /getUser   但是存到 List<SysPermission>
    //的时候存在映射问题  这边强制映射一下结果
    //但是加了这个有序列化问题  现在已经通过更改fastjson版本修复
    @Results({
            @Result(property = "permissionId", column = "permission_id"),
            @Result(property = "permissionCode", column = "permission_code"),
            @Result(property = "permissionName", column = "permission_name"),
            @Result(property = "url", column = "url")
    })

    List<SysPermission> selectPermissionList(@Param("userId") Integer userId);
}

