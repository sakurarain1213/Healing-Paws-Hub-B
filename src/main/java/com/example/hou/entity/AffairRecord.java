package com.example.hou.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@Document(collection = "affair_record")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AffairRecord {
    @Id
    private String id;

    private Integer userId;

    private AffairDTO affairInfo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finishTime;
}
