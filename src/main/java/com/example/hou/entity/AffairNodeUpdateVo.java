package com.example.hou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AffairNodeUpdateVo {
    @NotBlank
    @Size(min = 24, max = 24, message = "id不合法")
    @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
    private String id;

    private String name;

    @Size(max = 65535, message = "content不合法") //长度限制
    private String content;

    /**
     * 位置信息
     */
    private Integer positionX;
    private Integer positionY;

    private MultipartFile contentImg;
    private MultipartFile contentVideo;
}
