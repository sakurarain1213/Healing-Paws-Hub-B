package com.example.hou.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.hou.entity.Book;
import com.example.hou.service.BookService;
import com.example.hou.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: Healing-Paws-Hub-B
 * @description:
 * @author: 作者
 * @create: 2024-03-05 13:25




localhost:8080/book/get
{
"result": "阿巴阿巴阿巴",
"name": "AB",
}


 */

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/get")
    public Book getUserByName(@RequestBody JSONObject requestBody) {
        String name = requestBody.getString("name");//直接根据任意json请求 拿到name属性值
        return bookService.getBookByName(name);
    }


}
