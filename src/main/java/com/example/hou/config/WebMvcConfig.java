package com.example.hou.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${web.uploadPath}")   //application.yml中配置的
    private String baseUploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/js/**")
        //      .addResourceLocations("file:" + baseUploadPath + "jsFiles/"); // 可以直接加更多映射

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:"+baseUploadPath+"mediaFile/");  //同样也是服务器硬盘下载路径 注意和文件工具类统一
        //WebMvcConfigurer.super.addResourceHandlers(registry); 通常不需要调用 spring自动配置不会调用这个方法
    }
    //路径最后的斜杠必须加 通过ip:port/images/文件名  即可访问
}
