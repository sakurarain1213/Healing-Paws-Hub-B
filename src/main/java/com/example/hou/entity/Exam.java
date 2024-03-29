package com.example.hou.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "exam")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Exam {
    @Id
    @Size(min = 24, max = 24, message = "id不合法")
    @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
    private String id;

    private String examName;

    /**
     * 存放questionId列表
     */
    private List<String> questionList;

    /*private List<Question> questionList;
    private Boolean release;*/

    /**
    * 约束约束前端传入的时间类型参数格式和后端响应前端的时间类型格式
     * */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 以分钟为单位
     */
    @Max(180)
    private Long totalTime;
    /**
     * 结束时间>=开始时间+结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    private Long totalScore;

    /**
     * 所属角色，取值[1,2,3]
     */
    @Max(3)
    private int type;

    public boolean missingRequiredFields(){
        return ((examName == null || examName.trim().isEmpty()) || startTime == null
                || questionList == null || endTime == null);
    }

    public boolean missingAllRequiredFields(){
        return ((examName == null || examName.trim().isEmpty()) && startTime == null
                && questionList == null && endTime == null);
    }
}
