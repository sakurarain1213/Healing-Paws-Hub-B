package com.example.hou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "affair_node")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AffairNode {
    @Id
    private String id;
    private String content;
    private String contentImg;
    private String contentVideo;

    public boolean nullFieldsExceptId(){
        return content == null && contentImg == null && contentVideo == null;
    }
}
