package com.example.hou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AffairRecCreateVo {
    @NotBlank
    @Size(min = 24, max = 24, message = "affairId不合法")
    @Pattern(regexp = "^[a-z0-9]+$", message = "affairId不合法")
    private String affairId;
}
