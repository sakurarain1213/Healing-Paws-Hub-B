package com.example.hou.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Document(collection = "favorite")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Favorite {
    @Id
    @Size(min = 24, max = 24, message = "id不合法")
    @Pattern(regexp = "^[a-z0-9]+$", message = "id不合法")
    protected String id;

    @Size(min = 24, max = 24, message = "objectId不合法")
    @Pattern(regexp = "^[a-z0-9]+$", message = "objectId不合法")
    protected String objectId;
    /**
     * 多选一 必须有 type名同mongo的collection: item affair affairNode
     */
    protected String objectType;

    protected Integer userId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date createdAt;
}
