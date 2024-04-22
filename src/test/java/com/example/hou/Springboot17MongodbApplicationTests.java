package com.example.hou;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.hou.entity.*;
import com.example.hou.mapper.DepartmentRepository;
import com.example.hou.mapper.ItemRepository;
import com.example.hou.util.ESUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @program: Healing-Paws-Hub-B
 * @description:  测试mongoDB的crud   注意先执行添加 再查询  实体会自动以集合的形式创建一条记录
 * @author: hsin
 * @create: 2024-03-03 00:42

MongoTemplate 和 MongoRepository 都是在 Spring Boot 中与 MongoDB 进行交互的方法

MongoTemplate自定义程度高但是写的多   使用 Query 和 Criteria 来构建查询
MongoRepository 提供了一套高级别的抽象 常见功能写法简单

 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Springboot17MongodbApplicationTests {
    @Autowired //更灵活
    private MongoTemplate mongoTemplate;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void contextLoads() {
        Book book = new Book();
        book.setId(02);
        book.setName("testMongoDB");
        book.setType("testMongoDB");
        book.setDescription("testMongoDB");
        mongoTemplate.save(book);
    }
    @Test
    void find(){
        List<Book> all = mongoTemplate.findAll(Book.class);
        System.out.println(all);
    }
   //测试


    @Test
    void department(){
        /*测试通过   可以指定DB和collection保存  自己查
        Department d=new Department();
        d.setDepartmentName("急诊");
        d.setIntroduction("医院常见科室");

        List<String> connectIdList = new ArrayList<>();
        connectIdList.add("6610be93c9ffb85f45991f5e");
        d.setConnectID(connectIdList);

        List<Staff> ls = new ArrayList<>();
        Staff sb=new Staff("2","iraina","prime doc","13388880000");
        ls.add(sb);

        d.setStaffList(ls);


        departmentRepository.save(d);
        */
    }

    @Test
    void item(){
        /*测试通过
        Item i=new Item("1","knife","intro",
                "used for cut",12.34,"6610be93c9ffb85f45991f5e","normal");
        itemRepository.save(i);
        */
    }


    @Autowired
    private ESUtils<Affair> esAffairUtils;
    @Autowired
    private ElasticsearchClient esClient;
    //针对affair实现类的模糊功能测试
    @Test
    void ES______fuzzySearch() throws IOException {

        String input="一二";
        Integer pageNum=0;
        Integer pageSize=10;
        SearchResponse<HashMap> resp = esClient.search(s -> s
                        .index("test.affair")
                        .query(q -> q.bool(
                                b -> b.should(
                                        h -> h.fuzzy(
                                                f -> f.field("description")
                                                        .value(input)
                                                        .fuzziness("2")
                                        )
                                ).should(
                                        h -> h.fuzzy(
                                                f -> f.field("name")
                                                        .value(input)
                                                        .fuzziness("2")
                                        )
                                ))
                        )
                        .from(pageNum)
                        .size(pageSize)
                , HashMap.class);
        System.out.println(esAffairUtils.hitToTarget(resp.hits().hits(), Affair.class));

            }

}
