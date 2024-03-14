package com.example.hou.handler;

import com.example.hou.entity.Case;
import com.example.hou.util.FileUtil;
import com.example.hou.util.GlobalConstant;
import org.springframework.web.multipart.MultipartFile;

public class CaseDiagVdoHandler implements CaseFileHandler{
    private static CaseDiagVdoHandler instance;
    private MultipartFile src;
    private Case cse;

    private CaseDiagVdoHandler(MultipartFile src, Case cse){
        this.src = src;
        this.cse = cse;
    }

    public static CaseDiagVdoHandler getInstance(MultipartFile src, Case cse){
        if(instance == null){
            synchronized (CaseDiagVdoHandler.class) {
                if(instance == null){
                    instance = new CaseDiagVdoHandler(src, cse);
                }
            }
        }
        return instance;
    }

    @Override
    public void handleFile() {
        if (src == null || cse == null)throw new RuntimeException();
        System.out.println("=====CaseDiagVdoHandler======");

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

        cse.setDiagnosisVideo(filename);
        System.out.println(cse);

    }
}
