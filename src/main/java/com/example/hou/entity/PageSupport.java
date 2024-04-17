package com.example.hou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

import lombok.experimental.Accessors;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PageSupport<T> {
    /**
     * 当前页码, 页码从0开始
     */
//    private int currPageNum;

    /**
     * 分页显示条数
     */
//    private int pageSize;

    /**
     * 总条数
     */
//    private long totalCount;

    /**
     * 总页数
     */
    private int totalPages;

    /**
     * 分页数据
     */
    private List<T> listData;

    public PageSupport(Page<T> page){
        listData = page.getContent();
//        currPageNum = page.getNumber();
//        pageSize = page.getSize();
        totalPages = page.getTotalPages();
//        totalCount = page.getTotalElements();
    }
}
