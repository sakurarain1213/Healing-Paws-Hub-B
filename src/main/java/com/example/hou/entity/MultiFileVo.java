package com.example.hou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MultiFileVo {

    private MultipartFile[] files;

    private Integer[] elfs; //对应files中 每个被分块文件的结束idx

}
