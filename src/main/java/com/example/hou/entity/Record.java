package com.example.hou.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @program: juanBao
 * @description:
 * @author: 作者
 * @create: 2024-01-04 23:51
 */

@Data
//@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)

@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "record")
//优化版本后  record表以句子为单位记录
public class Record {

    private static final long serialVersionUID = 1L;

    /**
     * 记录主键
     */
    @TableId(value = "record_id", type = IdType.AUTO)
    private Integer recordId;

    private String username;//!!!!!!!!!!!debug

    /**
     * 文本内容（不考虑存路径 直接存文件本身）
     */
    @TableField("txt")
    private String txt;//!!!!!!!!!!!!!!一定要有注解！@！！！！！
    //注意传json时字段名还是要为txtFile！！！！！！！！！！！！！！！！！！！！！！！！！


    /**
     * 语音内容
     */
    @TableField("mp3_file")
    private MultipartFile mp3File;    //文件类型不要blob   直接MultipartFile！！！！！！！！！！

    /**
     * 开始时间
     */
    //这种注解 不支持时间戳的解析!!!
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("start_time")
    private Date startTime;


    //这里即概括的内容存储位置
    private String ps;

//课程的编号
    @TableField("class_id")
    private Integer classId;



}
