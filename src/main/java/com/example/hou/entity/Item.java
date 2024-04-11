package com.example.hou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @program: Healing-Paws-Hub-B
 * @description:
 * @author: 作者
 * @create: 2024-03-29 09:44
 */
@Data
@Document(collection = "item")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Item {

    @Id
    private String id;

    /**
     * 物品名称
     */
    private String name;

    /**
     * 物品介绍
     */
    private String introduction;


    private String pic;//加一个图片字段


    /**
     * 用途
     */
    private String usage;

    /**
     * 价格
     */
    private Double price;

    /**
     * 所属部门ID    唯一性   多部门同物品可以用id区分
     */
    private String departmentId;

    /**
     * 类型
     */
    private String type;

    // 可以添加其他字段和方法
}