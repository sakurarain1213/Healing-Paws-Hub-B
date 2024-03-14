package com.example.hou.handler;

import com.example.hou.entity.Case;
import org.springframework.web.multipart.MultipartFile;

/**
 * 策略模式添加不同case字段的文件路径
 */
public interface CaseFileHandler {
    void handleFile();

}
