package com.example.hou.service.impl;

import com.example.hou.entity.Item;
import com.example.hou.mapper.ItemRepository;
import com.example.hou.result.Result;
import com.example.hou.service.ItemService;
import com.example.hou.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
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
    public Item createItem(Item item, MultipartFile pic) {
        //如果传pic  就存到文件夹 ，返回url
        String url="";//考虑设置成默认值
        if (pic != null && !pic.isEmpty()){
            url= FileUtil.fileUpload(pic);  //利用文件工具类方法实现
            if(url==null) return null;//上传失败
        }
        item.setPic(url);
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


        // 获取现有的Item和要更新的Item的实例
        Item exist = existingItem.get();
        //URL优先覆盖
        exist.setPic(item.getPic());


        // 更新Item的字段，只有在字段值不为空且不同于现有值时才进行更新
        if (StringUtils.hasText(item.getName()) && !item.getName().equals(exist.getName())) {
            exist.setName(item.getName());
        }

        if (StringUtils.hasText(item.getIntroduction()) && !item.getIntroduction().equals(exist.getIntroduction())) {
            exist.setIntroduction(item.getIntroduction());
        }

        if (StringUtils.hasText(item.getUsage()) && !item.getUsage().equals(exist.getUsage())) {
            exist.setUsage(item.getUsage());
        }
        if (item.getPrice() != null && !Objects.equals(item.getPrice(), exist.getPrice())) {
            exist.setPrice(item.getPrice());
        }

        if (StringUtils.hasText(item.getDepartmentId()) && !Objects.equals(item.getDepartmentId(), exist.getDepartmentId())) {
            exist.setDepartmentId(item.getDepartmentId());
        }

        if (StringUtils.hasText(item.getType()) && !item.getType().equals(exist.getType())) {
            exist.setType(item.getType());
        }

        // 保存更新后的Item
        Item updatedItem = itemRepository.save(exist);

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
    public List<Item> searchItemByDepartment(String departmentId){
        //repository要在mapper写  这边直接用template手动实现
        // 创建查询条件
        Criteria criteria = new Criteria("departmentId").is(departmentId);
        // 创建查询对象
        Query query = new Query(criteria);
        // 执行查询并获取结果列表
        return mongoTemplate.find(query, Item.class);
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
    public Page<Item> getItemByDepartPage(Integer pageNum, Integer pageSize,String departID) {
        // 创建分页请求对象，注意pageNum需要减1，因为PageRequest的页码是从0开始的
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        // 调用Repository的自定义方法进行分页查询
        return itemRepository.findByDepartmentId(departID, pageable);//mapper自动实现
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