package com.example.hou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String id;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 部门介绍
     */
    private String introduction;

    /**
     * 邻接关系
     */
    //private String connect;todo 部门不多 考虑直接存id的list 利于查询   后续可以考虑存edge类  空间复杂度E

    private List<String> connectID;
    /**
     * 职员列表
     */
    private List<Staff> staff; // 假设Staff是另一个实体类，用于描述部门中的职员信息

    // staff只是静态内部类  直接耦合定义
    public class Staff {

        /**
         * 职员ID
         */
        private String id;

        /**
         * 姓名
         */
        private String name;

        /**
         * 职务
         */
        private String position;
        /**
         * 电话
         */
        private String phone;
        //。。。。。
    }


}