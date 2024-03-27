package com.example.hou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "case")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Case {
    @Id
    private String id;

    /**
     * 宠物学名
     */
    private String name;

    /**
     * 接诊（基本情况、临床症状）
     */
    private String description;
    private String descriptionImg;
    private List<Integer> descImgSize;
    private String descriptionVideo;

    /**
     * 病例检查
     */
    private String checkItem;
    private String checkItemImg;
    private List<Integer> checkImgSize;
    private String checkItemVideo;

    /**
     * 诊断结果
     */
    private String diagnosis;
    private String diagnosisImg;
    private List<Integer> diagImgSize;
    private String diagnosisVideo;

    /**
     * 治疗方案
     */
    private String remedy;
    private String remedyImg;
    private List<Integer> remedyImgSize;
    private String remedyVideo;
    /**
     * 病例类型数组
     */
//    @TextIndexed
    private List<String> type;

    private String mdText;

    // 除id以外的field是否全为空
    public boolean nullFieldsExceptId() {
        return name == null && description == null && descriptionImg == null && descriptionVideo == null &&
                checkItem == null && checkItemImg == null && checkItemVideo == null &&
                diagnosis == null && diagnosisImg == null && diagnosisVideo == null &&
                remedy == null && remedyImg == null && remedyVideo == null && type == null &&
                mdText == null;

    }
}

