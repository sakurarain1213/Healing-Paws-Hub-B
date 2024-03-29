package com.example.hou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EsQueryDTO {
//    索引名称
    private String indexName;
//    关键字属性
    private String field;
//    关键字
    private String word;
//  起始页
    private Integer from;
//  返回的页数
    private Integer size;


}
