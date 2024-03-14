package com.example.hou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "question")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Question {
    @Id
    private String id;

    private String statement;

    /**
     * 判断 - 0,1；选择:A,B,C,D(单选/多选)
     */
    private String answer;

    /**
     * 所属病Disease列表
     */
    private String[] type;

    /**
     * 答案解析
     */
    private String detail;

    private long score;
}
