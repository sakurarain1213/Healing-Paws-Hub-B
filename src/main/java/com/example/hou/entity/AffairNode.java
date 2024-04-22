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
public class AffairNode implements FavoriteInfo{
    @Id
    private String id;

    private String name;
    private String content;
    private String contentImg;
    private String contentVideo;

    private Integer positionX;
    private Integer positionY;

    public boolean nullFieldsExceptId(){
        return name == null && content == null && contentImg == null && contentVideo == null && positionX == null
                && positionY == null;
    }
}
