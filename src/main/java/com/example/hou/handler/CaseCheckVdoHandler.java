package com.example.hou.handler;

import com.example.hou.entity.Case;
import com.example.hou.util.FileUtil;
import com.example.hou.util.GlobalConstant;
import org.springframework.web.multipart.MultipartFile;

public class CaseCheckVdoHandler implements CaseFileHandler{
    private static CaseCheckVdoHandler instance;
    private MultipartFile src;
    private Case cse;

    private CaseCheckVdoHandler(MultipartFile src, Case cse){
        this.src = src;
        this.cse = cse;
    }

    public static CaseCheckVdoHandler getInstance(MultipartFile src, Case cse){
        if(instance == null){
            synchronized (CaseCheckVdoHandler.class) {
                if(instance == null){
                    instance = new CaseCheckVdoHandler(src, cse);
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
        System.out.println("=====CaseCheckVdoHandler======");

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

        cse.setCheckItemVideo(filename);
        System.out.println(cse);
    }
}
