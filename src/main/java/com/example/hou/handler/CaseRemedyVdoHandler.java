package com.example.hou.handler;

import com.example.hou.entity.Case;
import com.example.hou.util.FileUtil;
import com.example.hou.util.GlobalConstant;
import org.springframework.web.multipart.MultipartFile;

public class CaseRemedyVdoHandler implements CaseFileHandler{
    private static CaseRemedyVdoHandler instance;
    private MultipartFile src;
    private Case cse;

    private CaseRemedyVdoHandler(MultipartFile src, Case cse){
        this.src = src;
        this.cse = cse;
    }

    public static CaseRemedyVdoHandler getInstance(MultipartFile src, Case cse){
        if(instance == null){
            synchronized (CaseRemedyVdoHandler.class) {
                if(instance == null){
                    instance = new CaseRemedyVdoHandler(src, cse);
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
        System.out.println("=====CaseRemedyVdoHandler======");

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

        cse.setRemedyVideo(filename);
        System.out.println(cse);

    }
}
