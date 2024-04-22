package com.example.hou;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTests {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Test
    public void dateTest(){
        redisTemplate.opsForHash().put("tmp", "time", new Date( System.currentTimeMillis()));
        Date time =(Date) redisTemplate.opsForHash().get("tmp", "time");
        System.out.println(time);
    }
}
