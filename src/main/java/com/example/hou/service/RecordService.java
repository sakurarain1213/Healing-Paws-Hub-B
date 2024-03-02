package com.example.hou.service;


import com.example.hou.entity.Class;
import com.example.hou.entity.Record;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hsin
 * @since 2023-04-08
 */
public interface RecordService /*extends IService<Record> */{

    public String startClass() ;

    public String endClass();

    //直接给记录表打上两个标记即可   概括方法即在两个标记之间进行总结即可

/*
录音表要实现的service主要有以下几个：
列表查询：根据一定的条件（例如时间范围、录音人等）查询录音列表。
录音上传：将录音文件上传到指定的存储设备中，并将上传信息记录到录音表中。
录音编辑：对录音文件进行编辑、剪辑、拆分等操作。 现在只有文本编辑处理
           录音删除：根据录音ID删除录音记录及相关信息。
           录音播放：根据录音ID播放录音文件。
           录音详情查询：根据录音ID查询录音文件的详细信息。
           录音转换：将录音文件转换成指定格式或采样率。
           录音下载：将录音文件下载到本地或指定存储设备。
录音转写：将录音文件转写成文字文件或文本信息。这个在前端完成

        录音备份：将录音文件备份到指定的存储设备中，确保数据的安全性和可靠性。
 */

    //测试用一下下面的接口
    public Record recordUpload(MultipartFile file) throws Exception;

    //核心函数  得到一节课的记录的概括   无参
    public  List<Class> recordGetService();


}
