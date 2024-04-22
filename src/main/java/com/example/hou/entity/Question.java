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

    @Size(max = 30, message = "题目名长度不能超过30")
    private String name;

    /**
     * 题目详情
     * */
    private String statement;

    /**
     * 1选择，2判断，3填空
     * */
    private Integer questionType;

    /**
     * 判断 - T,F; 选择:A,B,C,D(单选)
     */
    @Size(max = 65536, message = "answer长度不能超过30")
    /*@Pattern(regexp = "^[TFA-Da-d]$", message = "answer为T/F如果为判断题，Aa/Bb/Cc/Dd如果为选择题")*/
    private String answer;

    /**
     * 所属病Disease的name列表
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
        return (name == null || name.trim().isEmpty() ||
                statement == null || statement.trim().isEmpty()
                || questionType == null || answer == null
                || type == null || score == null);
    }

    public boolean missingAllRequiredFields(){
        return ((name == null || name.trim().isEmpty())
                && (statement == null || statement.trim().isEmpty())
                && answer == null && questionType == null && type == null
                && detail == null && score == null);
    }

    // 判断questionType是否和answer匹配
    public boolean questionTypeNotMatch() {
        if(questionType == 1 && !answer.matches("[A-Da-d]"))
            return true;
        return questionType == 2 && !answer.matches("[TF]");
    }
}
