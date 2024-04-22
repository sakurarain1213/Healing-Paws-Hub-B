package com.example.hou.entity;

import com.example.hou.validator.CaseTypeUpdateConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CaseUpdateVo {
    @NotBlank @Size(min = 24, max = 24, message = "id不合法")
    @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
    private String id;

    @Size(max = 30, message = "name不合法")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$", message = "name为中文、英文、数字、下划线组合")
    private String name;

    @Size(max = 65536, message = "description不合法")
    private String description;
    private MultipartFile descriptionImg;
    private MultipartFile descriptionVideo;

    @Size(max = 200, message = "checkItem不合法")
    private String checkItem;
    private MultipartFile checkItemImg;
    private MultipartFile checkItemVideo;

    @Size(max = 200, message = "diagnosis不合法")
    private String diagnosis;
    private MultipartFile diagnosisImg;
    private MultipartFile diagnosisVideo;

    @Size(max = 200, message = "remedy不合法")
    private String remedy;
    private MultipartFile remedyImg;
    private MultipartFile remedyVideo;

    @CaseTypeUpdateConstraint
    private List<String> type;

    private String mdText;
}
