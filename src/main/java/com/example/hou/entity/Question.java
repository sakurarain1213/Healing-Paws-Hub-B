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

@Data
@Document(collection = "question")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Question {
    @Id
    @Size(min = 24, max = 24, message = "id不合法")
    @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
    private String id;


    private String statement;

    /**
     * 判断 - T,F；选择:A,B,C,D(单选)//多选暂时不考虑
     */
    @Size(max = 15, message = "answer不合法")
    @Pattern(regexp = "^[TFA-Da-d]$", message = "answer为0,1如果为判断题，A,B,C,D如果为选择题")
    private String answer;

    /**
     * 所属病Disease列表
     */
    private List<String> type;

    /**
     * 答案解析
     */
    private String detail;

    @Max(100)
    @Min(1)
    private Long score;

    public boolean missingRequiredFields(){
        return (statement == null || statement.trim().isEmpty() || answer == null
                || type == null || score == null);
    }

    public boolean missingAllRequiredFields(){
        return ((statement == null || statement.trim().isEmpty()) && answer == null
                && type == null && detail == null && score == null);
    }
}
