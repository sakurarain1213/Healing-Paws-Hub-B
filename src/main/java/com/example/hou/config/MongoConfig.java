package com.example.hou.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @program: Healing-Paws-Hub-B
 * @description:   用于多个spring data中防止mongoDB的bean和别的bean命名冲突用  主要解决repository类
 * @author: 作者
 * @create: 2024-03-05 14:05
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.example.hou.mapper")
public class MongoConfig {
}