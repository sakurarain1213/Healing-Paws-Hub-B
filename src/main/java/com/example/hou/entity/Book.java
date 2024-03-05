package com.example.hou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 @program: Healing-Paws-Hub-B
    hsin   测试类
    用于简单测试mongoDB的crud
 * @create: 2024-03-03 00:38
 */
//mongo的定义文档和字段映射
@Document(collection = "book")  //类比 即表名
@Data

public class Book {
    @Indexed(unique = true)  //用于确定主键   或者直接@Id
    private Integer id;

    @Field("name")  //类比 确定表中的列名
    private String name;

    private String type;

    private String description;

    public Book(Integer id, String name, String type, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public Book() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        System.out.println("hello");
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';

    }



}

