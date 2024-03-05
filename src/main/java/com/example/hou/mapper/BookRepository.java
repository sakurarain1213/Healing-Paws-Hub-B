package com.example.hou.mapper;

import com.example.hou.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;


/*

利用Repository 根据命名自动生成查询
注意注解 mongo的Repository是不需要的  但是  如果一个项目引用多个数据来源
Spring Data模块属于不同的jar，但用的是同一个接口，Spring在运行时不知道当前的bean是绑定的JPA的，还是MongoDB或者Elasticsearch的库。
此时需要使用注解来声明不同模块对应的包路径，以此区分开这些Repository的bean：
在配置类config改即可
*/


public interface BookRepository extends MongoRepository<Book, String> {
    Book getBookByName(String name);
}
