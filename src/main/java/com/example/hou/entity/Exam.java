package com.example.hou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "exam")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)

public class Exam {
    @Id
    private String id;

    private String startTime;
    /**
     * 以分钟为单位
     */
    private Long totalTime;
    /**
     * 结束时间>=开始时间+结束时间
     */
    private String endTime;

    private String examName;

    /**
     * 存放questionId列表
     */
    private String[] questionList;

    private Long totalScore;
}
