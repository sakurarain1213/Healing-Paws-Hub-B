package com.example.hou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.parameters.P;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data/*生成getter和setter*/
@NoArgsConstructor/*无参构造器*/
@AllArgsConstructor/*全参构造器*/
@Accessors(chain = true)/*链式*/
public class QuestionEntity {
    private String name;
    private String statement;
    private String answer;
    private String detail;
    private Long score;
    private Integer questionType;
}
