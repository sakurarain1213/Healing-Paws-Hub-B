package com.example.hou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@TableName("sys_user_permission_relation")
public class SysUserPermissionRelation  {

    @TableId(value = "user_permission_relation_id", type = IdType.ID_WORKER)
    private Integer userPermissionRelationId;

    private Integer userId;

    private Integer permissionId;
}
