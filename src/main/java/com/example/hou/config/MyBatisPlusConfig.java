package com.example.hou.config;

/**
 * @program: hou
 * @description:
 * @author: 作者
 * @create: 2022-12-26 14:10
 */

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan("com.example.hou.mapper")//mapper接口扫描注解
@EnableTransactionManagement
public class MyBatisPlusConfig {//分页配置,本博客不展示分页操作
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }
}
