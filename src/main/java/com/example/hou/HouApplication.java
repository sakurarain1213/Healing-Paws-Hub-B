package com.example.hou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling   // 开启定时任务

@SpringBootApplication  (exclude = {GsonAutoConfiguration.class})  //这个括号涉及到gson能否运行

@MapperScan(basePackages = {"com.example.hou.mapper"})//程序入口   mapper和dao选其一即可 要和自己的路径对应

@ComponentScan(basePackages ={"com.example.hou","com.gearwenxin"} )  //文心一言的配置  需要手动找包
public class HouApplication {
    public static void main(String[] args) {
        SpringApplication.run(HouApplication.class, args);
    }
}
