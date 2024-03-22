package com.example.hou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Document(collection = "exam")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Exam {
    /**
     * 匹配日期
     * <p>
     * <p>
     * 格式(首位不为0): XXXX-XX-XX或 XXXX-X-X
     * <p>
     * <p>
     * 范围:1900--2099
     * <p>
     * <p>
     * 匹配 : 2005-04-04 11:00:00
     * <p>
     * <p>
     * 不匹配: 01-01-01
     */
    public static final String DATE_BARS_REGEXP_HOUR_MIN_SS =
            "^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D*$";

    @Id
    @Size(min = 24, max = 24, message = "id不合法")
    @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
    private String id;

    @Pattern(regexp = DATE_BARS_REGEXP_HOUR_MIN_SS, message = "时间格式为: xxxx-xx-xx xx:xx:xx")
    private String startTime;
    /**
     * 以分钟为单位
     */
    private Long totalTime;
    /**
     * 结束时间>=开始时间+结束时间
     */
    @Pattern(regexp = DATE_BARS_REGEXP_HOUR_MIN_SS, message = "时间格式为: xxxx-xx-xx xx:xx:xx")
    private String endTime;

    private String examName;

    /**
     * 存放questionId列表
     */
    private String[] questionList;

    private Long totalScore;
}
