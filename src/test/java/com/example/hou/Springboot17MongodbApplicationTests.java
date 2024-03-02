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
 */

@SpringBootTest
class Springboot17MongodbApplicationTests {
    @Autowired //固定写法
    private MongoTemplate mongoTemplate;

    @Test
    void contextLoads() {
        Book book = new Book();
        book.setId(10);
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
}
