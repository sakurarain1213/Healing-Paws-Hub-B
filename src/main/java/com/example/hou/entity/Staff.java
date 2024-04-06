package com.example.hou.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @program: Healing-Paws-Hub-B
 * @description:
 * @author: 作者
 * @create: 2024-04-06 11:05
 */
//无数据表 静态信息类  属于Department
@NoArgsConstructor
public class Staff {
        /**
         * 职员ID
         */
        @Setter
        @Getter
        private String id;

        /**
         * 姓名
         */
        @Setter
        @Getter
        private String name;

        /**
         * 职务
         */
        @Setter
        @Getter
        private String position;
        /**
         * 电话
         */
        @Setter
        @Getter
        private String phone;
        //。。。。。


        //全参构造函数
        public Staff(String id, String name, String position, String phone) {
            this.id = id;
            this.name = name;
            this.position = position;
            this.phone = phone;
        }
}
