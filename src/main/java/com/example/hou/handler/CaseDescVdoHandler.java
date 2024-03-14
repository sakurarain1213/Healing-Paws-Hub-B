package com.example.hou.handler;

import com.example.hou.entity.Case;
import com.example.hou.util.FileUtil;
import com.example.hou.util.GlobalConstant;
import org.springframework.web.multipart.MultipartFile;

/**
 * 单例类
 */
public class CaseDescVdoHandler implements CaseFileHandler{
    private static CaseDescVdoHandler instance;
    private MultipartFile src;
    private Case cse;

    private CaseDescVdoHandler(MultipartFile src, Case cse){
        this.src = src;
        this.cse = cse;
    }

    public static CaseDescVdoHandler getInstance(MultipartFile src, Case cse){
        if(instance == null){
            synchronized (CaseDescVdoHandler.class) {
                if(instance == null){
                    instance = new CaseDescVdoHandler(src, cse);
                }
            }
        }else {
            instance.src = src;
            instance.cse = cse;
        }
        return instance;
    }

    @Override
    public void handleFile() {
        if (src == null || cse == null)throw new RuntimeException();
        System.out.println("=====CaseDescVdoHandler======");

//        获取文件类型后缀
        String tmp = src.getOriginalFilename();
        int idx = tmp.indexOf(".");

        StringBuffer buf = new StringBuffer();
        buf.append(System.currentTimeMillis());
        buf.append(tmp.substring(idx));

        String filename = buf.toString();
        System.out.println(filename);

        String fullpath = GlobalConstant.prefix + filename;
        FileUtil.transferFile(src, fullpath);

        cse.setDescriptionVideo(filename);
        System.out.println(cse);

    }
}
