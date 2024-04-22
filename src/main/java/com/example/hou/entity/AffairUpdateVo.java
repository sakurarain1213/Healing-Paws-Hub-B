package com.example.hou.entity;


import com.example.hou.validator.AffairUpdateConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AffairUpdateVo {
    @NotBlank(message = "缺少必需参数")
    @Size(min = 24, max = 24, message = "id不合法")
    @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
    private String id;

    @Size(max = 200, message = "name不合法")
    private String name;

    @Size(max = 200, message = "description不合法")
    private String description;

    private MultipartFile pic;

    @Size(max = 30, message = "role不合法")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$", message = "role为中文、英文、数字、下划线组合")
    private String role;

    @AffairUpdateConstraint
    private List<String> affairs;

    private List<String[]> edges;

}
