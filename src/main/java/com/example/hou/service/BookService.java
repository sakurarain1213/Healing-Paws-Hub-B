package com.example.hou.service;

import com.example.hou.entity.Book;

public interface BookService {

    public Book getBookByName(String name);  //一定要驼峰！

}
