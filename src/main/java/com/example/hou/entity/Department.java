package com.example.hou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @program: Healing-Paws-Hub-B
 * @description:
 * @author: 作者
 * @create: 2024-03-29 09:23
 */
@Data
@Document(collection = "department")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Department {

    @Id
    @NotNull(message = "ID must not be null")
    private String id;

    //用于controller层的valid注解限制
    @NotNull(message = "Name must not be null")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    /**
     * 部门名称
     */
    private String departmentName;


    private String pic;//加一个图片字段


    /**
     * 部门介绍
     */
    private String introduction;

    /**
     * 邻接关系
     */
    //private String connect;todo 部门不多 考虑直接存id的list 利于查询   后续可以考虑存edge类的数据表  空间复杂度E

    private List<String> connectID;//暂时不用

    private Position position;//三维

    /**
     * 职员列表
     */
    private List<Staff> staffList; // Staff描述部门中的职员

    // staff只是静态内部类  直接耦合定义



}