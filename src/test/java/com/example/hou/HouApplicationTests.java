package com.example.hou;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.hou.entity.LogUser;
import com.example.hou.mapper.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HouApplicationTests {

    //测login的redis

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String USER_PREFIX = "login:";


    @Test
    void contextLoads() {
        Pageable pageable = PageRequest.of(1- 1,5);
        System.out.println(departmentRepository.findAll(pageable));


            // 1.从Redis获取用户数据
        //   String userKey = USER_PREFIX + userId;
        //   String userJson = redisTemplate.opsForValue().get(userKey);

        //  System.out.println(userJson);
            //{"@type":"com.example.hou.entity.LogUser","accountNonExpired":true,"accountNonLocked":true,"credentialsNonExpired":true,"enabled":true,"password":"$2a$10$zpIj12VJUwVQAHAFJHjaaOwDl9ZkzYb21oA7a6s.Q6AN9PC7BX4Ka","permissions":["sys:queryUser"],"user":{"account":"w1625154105@163.com","accountNotExpired":true,"accountNotLocked":true,"avatar":"http://150.158.110.63:8080/images/678be954-d1a8-45b0-879b-b514653b44a1_2024-04-01-20-28-01_123.png","createTime":1710226628000,"credentialsNotExpired":true,"enabled":true,"lastLoginTime":1711976695000,"password":"$2a$10$zpIj12VJUwVQAHAFJHjaaOwDl9ZkzYb21oA7a6s.Q6AN9PC7BX4Ka","updateTime":1710226628000,"userId":3}}
        //  if (userJson == null) {
                //     throw new RuntimeException("User not found in Redis for ID: " + userId);
                //}

            // 2.反序列化用户对象
        // LogUser user =JSON.parseObject(userJson,LogUser.class);


        //List<String> newPermissions = new ArrayList<>(); // 创建一个新的ArrayList实例
        //newPermissions.add("123");


        //user.setPermissions(newPermissions);

            // 4.序列化更新后的用户对象
        //String updatedUserJson = JSON.toJSONString(user, SerializerFeature.WriteClassName);

            // 5.将更新后的用户数据存回Redis
        //redisTemplate.opsForValue().set(userKey, updatedUserJson);

            // 处理异常，例如序列化和反序列化错误
           // throw new RuntimeException("Error updating user permissions 未找到用户 token缓存过期", e);

        }



}


