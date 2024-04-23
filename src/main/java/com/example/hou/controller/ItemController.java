package com.example.hou.controller;

import com.example.hou.entity.Department;
import com.example.hou.entity.Item;
import com.example.hou.entity.PageSupport;
import com.example.hou.mapper.ItemRepository;
import com.example.hou.result.Result;
import com.example.hou.service.ItemService;
import com.example.hou.util.FileUtil;
import com.example.hou.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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


    @Autowired
    private ItemRepository itemRepository;

    // 创建项目
    @PostMapping
    public Result createItem(@Validated @RequestPart Item item, @RequestPart(value = "pic", required = false) MultipartFile pic) {
        Item createdItem = itemService.createItem(item,pic);
        if (createdItem == null) {
            return new Result(-100, "上传文件有误导致上传失败", "error");
        }
        //Result可以嵌入response的体
        return new Result(200, "success", createdItem);
    }

    // 更新项目（通过ID）  注意也要改文件
    @PutMapping
    public Result updateItemById(@Validated @RequestPart Item item, @RequestPart(value = "pic", required = false) MultipartFile pic) {
        try {
            // 确保ID被正确设置
            if (item.getId() == null) {
                return new Result(-100, "缺少id字段", "error");
            }

            //确保先拿到DB里的URL
            Optional<Item> existingItem = itemRepository.findById(item.getId());
            if (!existingItem.isPresent()) {
                throw new IllegalArgumentException("Item with ID " + item.getId() + " does not exist.");
            }
            //文件部分
            String url=existingItem.get().getPic();
            if (pic != null && !pic.isEmpty()) {
                url= FileUtil.fileUpload(pic);
            }
            item.setPic(url);

            // 调用服务层方法更新项目
            String updatedId = itemService.updateItemById(item);
            // 验证返回的ID是否与传入的ID匹配，并返回结果
            if (Objects.equals(updatedId, String.valueOf(item.getId()))) {
                return new Result(200, "success", "更新成功");
            } else {
                return new Result(-100, "更新后的ID与传入的ID不匹配", "error");
            }
        } catch (Exception e) {
            // 捕获异常，并返回错误响应
            return new Result(-100, "更新项目时发生错误", "error");
        }
    }

    // 删除项目（通过ID）
    @DeleteMapping("/{id}")
    public Result deleteItemById(@PathVariable @NotNull String id) {
        try {
            itemService.deleteItemById(id);
            return new Result(200, "success", "删除成功");
        } catch (Exception e) {
            // 捕获异常，并返回错误响应
            return new Result(-100, "item的ID不存在或数据库错误", "error");
        }
    }




    // 根据ID获取商品
    @GetMapping("/{id}")
    public Result getItemById(@PathVariable String id) {
        Item item = itemService.getItemById(id);
        return new Result(200, "success", item);
    }

    // 分页获取商品列表
    @GetMapping("/page")
    public Result getItemByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                                    @RequestParam(defaultValue = "5") Integer pageSize) {
        Page<Item> items = itemService.getItemByPage(pageNum, pageSize);

        //封装一个分页标准返回
        PageSupport<Item> respPage = new PageSupport<>();
        respPage.setListData(items.getContent())
                .setTotalPages(items.getTotalPages());

        return ResultUtil.success(respPage);
    }

    // 根据商品名称组合查询商品（这里需要定义具体的查询逻辑）
    @GetMapping("/search")
    public Result getItemByCombinedName(@RequestParam(defaultValue = "1") Integer pageNum,
                                                            @RequestParam(defaultValue = "5") Integer pageSize,
                                                            @RequestParam("name") String searchName) {
        List<Item> items = itemService.getItemByCombinedName(pageNum, pageSize, searchName); // 假设服务层有这个方法
        return new Result(200,"success",items);
    }






}