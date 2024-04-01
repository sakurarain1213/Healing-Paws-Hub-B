package com.example.hou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AffairNodeCreateVo {
    private String name;
    @Size(max = 200, message = "content不合法")
    private String content;
    private MultipartFile contentImg;
    private MultipartFile contentVideo;
}
