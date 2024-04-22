package com.example.hou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AffairNodeCreateVo {
    @NotBlank
    private String name;

    @NotBlank
    @Size(max = 65535, message = "content不合法")
    private String content;

    /**
     * 位置信息
     */
    private Integer positionX;
    private Integer positionY;

    private MultipartFile contentImg;
    private MultipartFile contentVideo;
}
