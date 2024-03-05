package com.example.hou.service.impl;

import com.example.hou.entity.Book;
import com.example.hou.mapper.BookRepository;
import com.example.hou.service.BookService;
import com.example.hou.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: Healing-Paws-Hub-B
 * @description:
 * @author: 作者
 * @create: 2024-03-05 13:22
 */

//简单测试方法直接写实现 不用再写一层接口  规范起见 后续统一


@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book getBookByName(String name) {
        return bookRepository.getBookByName(name);
    }
}
