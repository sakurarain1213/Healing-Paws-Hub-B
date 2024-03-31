package com.example.hou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName("sys_permission")
public class SysPermission implements Serializable {
    @TableId(value = "permission_id", type =IdType.AUTO)  //id=int类自增主键问题debug
    private Integer permissionId;

    @TableField("permission_code")
    private String permissionCode;

    @TableField("permission_name")
    private String permissionName;

    private String url;
}

