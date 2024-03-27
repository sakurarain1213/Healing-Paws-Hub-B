package com.example.hou.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * @program: Healing-Paws-Hub-B
 * @description:
 * @author: 作者
 * @create: 2024-03-21 16:56
 */
@RestController
public class LogoController {
    //测试通过 用于email或前端使用
    @GetMapping(value = "/logo/{logoName}.svg", produces ="image/svg+xml")
    public FileSystemResource getLogo(@PathVariable String logoName) {
        String logoDirectory = "/www/wwwroot/";  //静态资源地址
        String logoPath = logoDirectory + logoName + ".svg";
        return new FileSystemResource(new File(logoPath));
    }
}