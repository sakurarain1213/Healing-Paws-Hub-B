package com.example.hou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AffairDTO {
    @Size(min = 24, max = 24, message = "id不合法")
    @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
    private String id;

    @Size(max = 65536, message = "name不合法")
    private String name;

    @Size(max = 65536, message = "description不合法")
    private String description;

    private String pic;
    private List<Integer> picSize;


    @Size(max = 30, message = "role不合法")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$", message = "role为中文、英文、数字、下划线组合")
    private String role;

    private List<AffairNode> affairs;
}
