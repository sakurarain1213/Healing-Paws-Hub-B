package com.example.hou.service.impl;

import com.example.hou.entity.Item;
import com.example.hou.mapper.ItemRepository;
import com.example.hou.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: Healing-Paws-Hub-B
 * @description:
 * @author: 作者                     todo  整个类方法需要修改
 * @create: 2024-03-29 10:45
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Item createItem(Item item) {
        return itemRepository.save(item);   //save= 无id的insert+和有id的update
    }

    @Override
    public String updateItemById(Item item) {
        return itemRepository.save(item).getId();   //save即使是空也会覆盖原有信息
    }

    @Override
    public void deleteItemById(String id) {
        itemRepository.deleteById(id);
    }

    @Override
    public Item getItemById(String id) {
        return itemRepository.findById(id).orElse(null);  //或者optional
    }

    @Override
    public Page<Item> getItemByPage(Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return itemRepository.findAll(pageable);
        /*
       Pageable 是向数据源的请求，想要什么数据。
        Page<T> 是数据源返回响应，包含了请求的数据以及分页额外信息 */
    }

    @Override
    public List<Item> getItemByCombinedName(Integer pageNum, Integer pageSize, String searchName) {
        //考虑  ES的模糊查找 考虑封装工具类
        return null;
    }

    @Override
    public void updateItemStock(String itemId, Integer stock) {

    }

    @Override
    public void updateItemPrice(String itemId, Double price) {

    }


    // ...
}