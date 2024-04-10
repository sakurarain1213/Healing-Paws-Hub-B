package com.example.hou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AddNodeVo {
    @NotBlank(message = "缺少必需参数")
    @Size(min = 24, max = 24, message = "affairId不合法")
    @Pattern(regexp = "^[a-z0-9]+$", message = "affairId不合法")
    private String affairId;

    @NotBlank(message = "缺少必需参数")
    @Size(min = 24, max = 24, message = "nodeId不合法")
    @Pattern(regexp = "^[a-z0-9]+$", message = "nodeId不合法")
    private String nodeId;
}
