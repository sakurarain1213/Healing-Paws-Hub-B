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
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

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
        if (item.getId() == null) {
            throw new IllegalArgumentException("Item ID must not be null.");
        }
        Optional<Item> existingItem = itemRepository.findById(item.getId());
        if (!existingItem.isPresent()) {
            throw new IllegalArgumentException("Item with ID " + item.getId() + " does not exist.");
        }
        Item updatedItem = itemRepository.save(item);
        return updatedItem.getId();
    }

    @Override
    public void deleteItemById(String id) {
        Optional<Item> existingItem = itemRepository.findById(id);
        if (!existingItem.isPresent()) {
            throw new IllegalArgumentException("Item with ID " + id + " does not exist.");
        }
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
        // 使用正则表达式进行模糊查询
        Pattern pattern = Pattern.compile(".*" + Pattern.quote(searchName) + ".*", Pattern.CASE_INSENSITIVE);

        // 创建对各个字段的模糊匹配条件
        Criteria nameCriteria = Criteria.where("name").regex(pattern);
        Criteria introductionCriteria = Criteria.where("introduction").regex(pattern);
        Criteria usageCriteria = Criteria.where("usage").regex(pattern);
        Criteria typeCriteria = Criteria.where("type").regex(pattern);
        // 如果还有其他字段需要模糊查询，可以继续添加Criteria

        // 使用orOperator创建一个逻辑或条件
        Criteria orCriteria = new Criteria().orOperator(nameCriteria,
                introductionCriteria,
                usageCriteria,
                typeCriteria

        );

        // 构建查询并添加分页逻辑
        Query query = new Query(orCriteria);
        query.with(PageRequest.of(pageNum - 1, pageSize));

        // 执行查询并返回结果
        List<Item> items = mongoTemplate.find(query, Item.class);

        return items;
    }


    //nope
    @Override
    public void updateItemStock(String itemId, Integer stock) {
    }

    //nope
    @Override
    public void updateItemPrice(String itemId, Double price) {
    }


    // ...
}