package com.example.hou;

import com.example.hou.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

/**
 * @program: Healing-Paws-Hub-B
 * @description:  测试mongoDB的crud   注意先执行添加 再查询  实体会自动以集合的形式创建一条记录
 * @author: hsin
 * @create: 2024-03-03 00:42

MongoTemplate 和 MongoRepository 都是在 Spring Boot 中与 MongoDB 进行交互的方法

MongoTemplate自定义程度高但是写的多   使用 Query 和 Criteria 来构建查询
MongoRepository 提供了一套高级别的抽象 常见功能写法简单

 */

@SpringBootTest
class Springboot17MongodbApplicationTests {
    @Autowired //固定写法
    private MongoTemplate mongoTemplate;

    @Test
    void contextLoads() {
        Book book = new Book();
        book.setId(02);
        book.setName("testMongoDB");
        book.setType("testMongoDB");
        book.setDescription("testMongoDB");
        mongoTemplate.save(book);
    }
    @Test
    void find(){
        List<Book> all = mongoTemplate.findAll(Book.class);
        System.out.println(all);
    }
   //测试


}
