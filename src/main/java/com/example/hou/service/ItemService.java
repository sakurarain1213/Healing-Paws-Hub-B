package com.example.hou.service;

import com.example.hou.entity.Item;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ItemService {
    Item createItem(Item item);

    String updateItemById(Item item);

    void deleteItemById(String id);

    Item getItemById(String id);

    Page<Item> getItemByPage(Integer pageNum, Integer pageSize);

    List<Item> getItemByCombinedName(Integer pageNum, Integer pageSize, String searchName);

    // 可以按需添加其他与物品相关的业务方法
    // 例如，如果物品与库存、价格或销售记录等相关，可以添加以下方法：

    // nope
    void updateItemStock(String itemId, Integer stock);

    // nope
    void updateItemPrice(String itemId, Double price);


    // ...
}