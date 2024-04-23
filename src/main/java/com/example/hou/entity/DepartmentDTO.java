package com.example.hou.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @program: Healing-Paws-Hub-B
 * @description:
 * @author: 作者
 * @create: 2024-04-24 01:15
 */
@Data
public class DepartmentDTO {

    private String id;

    //用于controller层的valid注解限制
    @NotNull(message = "Name must not be null")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    private String departmentName;


    private String pic;//加一个图片字段


    private Position position;//三维
}
