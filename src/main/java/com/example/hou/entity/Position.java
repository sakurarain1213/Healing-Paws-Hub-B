package com.example.hou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @program: Healing-Paws-Hub-B
 * @description:   用于部门的空间位置  三维
 * @author: 作者
 * @create: 2024-04-23 23:19
 */
@Data
@NoArgsConstructor
public class Position {

    private double x;
    private double y;
    private double z;

    public Position(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

}
