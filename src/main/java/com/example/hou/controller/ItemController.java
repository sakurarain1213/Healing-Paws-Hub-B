package com.example.hou.controller;

import com.example.hou.entity.Item;
import com.example.hou.result.Result;
import com.example.hou.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Objects;

/**
 * @program: Healing-Paws-Hub-B
 * @description:
 * @author: 作者     TODO  考虑删除的同时删除关联的收藏记录
 * @create: 2024-04-06 15:42
 */
@RestController
@RequestMapping("/item")
@Validated
public class ItemController {

    @Autowired
    private ItemService itemService;

    // 创建项目
    @PostMapping
    public ResponseEntity<Result> createItem(@Validated @RequestBody Item item) {
        Item createdItem = itemService.createItem(item);
        if (createdItem == null) {
            return ResponseEntity.badRequest().body(new Result(-100, "error", "创建项目失败"));
        }
        //Result可以嵌入response的体
        return ResponseEntity.created(URI.create("/item/" + createdItem.getId())).body(new Result(200, "success", createdItem));
    }

    // 更新项目（通过ID）
    @PutMapping
    public ResponseEntity<Result> updateItemById(@RequestBody Item item) {
        try {
            // 确保ID被正确设置
            if (item.getId() == null) {
                return ResponseEntity.badRequest().body(new Result(-100, "error", "缺少id字段"));
            }
            // 调用服务层方法更新项目
            String updatedId = itemService.updateItemById(item);
            // 验证返回的ID是否与传入的ID匹配，并返回结果
            if (Objects.equals(updatedId, String.valueOf(item.getId()))) {
                return ResponseEntity.ok(new Result(200, "success", "更新成功"));
            } else {
                return ResponseEntity.badRequest().body(new Result(-100, "error", "更新后的ID与传入的ID不匹配"));
            }
        } catch (Exception e) {
            // 捕获异常，并返回错误响应
            return ResponseEntity.internalServerError().body(new Result(-100, "error", "更新项目时发生错误"));
        }
    }

    // 删除项目（通过ID）
    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteItemById(@PathVariable @NotNull String id) {
        try {
            itemService.deleteItemById(id);
            return ResponseEntity.ok(new Result(200, "success", "删除成功"));
        } catch (Exception e) {
            // 捕获异常，并返回错误响应
            return ResponseEntity.internalServerError().body(new Result(-100, "error", "item的ID不存在或数据库错误"));
        }
    }




    // 根据ID获取商品
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable String id) {
        Item item = itemService.getItemById(id);
        return ResponseEntity.ok().body(item);
    }

    // 分页获取商品列表
    @GetMapping("/page")
    public ResponseEntity<Page<Item>> getItemByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                                    @RequestParam(defaultValue = "5") Integer pageSize) {
        Page<Item> items = itemService.getItemByPage(pageNum, pageSize);
        return ResponseEntity.ok(items);
    }

    // 根据商品名称组合查询商品（这里需要定义具体的查询逻辑）
    @GetMapping("/search")
    public ResponseEntity<List<Item>> getItemByCombinedName(@RequestParam(defaultValue = "1") Integer pageNum,
                                                            @RequestParam(defaultValue = "5") Integer pageSize,
                                                            @RequestParam("name") String searchName) {
        List<Item> items = itemService.getItemByCombinedName(pageNum, pageSize, searchName); // 假设服务层有这个方法
        return ResponseEntity.ok(items);
    }






}