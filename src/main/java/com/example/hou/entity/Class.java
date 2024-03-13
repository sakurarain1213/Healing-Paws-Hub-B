package com.example.hou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: juanBao
 * @description:
 * @author: 作者
 * @create: 2024-01-05 05:28
 */

@Data

@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "class")
public class Class {
    /**
     * 记录主键   只有主键 表示序号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableId(value = "txt")
    private String txt;
    @TableId(value = "summary")
    private String summary;


}
