package com.example.hou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hou.entity.SysPermission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /*
     通过用户id查询用户的权限数据
     */

    @Select({"<script>"+
            " SELECT p.* FROM"+
            " sys_user u"+
            " LEFT JOIN sys_user_permission_relation r ON u.user_id = r.user_id"+
            " LEFT JOIN sys_permission p ON r.permission_id = p.permission_id"+
            " WHERE u.user_id = #{userId}  AND  p.permission_name IS NOT NULL "+
            "</script>"
    })

    List<SysPermission> selectPermissionList(@Param("userId") Integer userId);
}

