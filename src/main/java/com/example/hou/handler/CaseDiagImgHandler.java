package com.example.hou.handler;

import com.example.hou.entity.Case;
import com.example.hou.util.FileUtil;
import com.example.hou.util.GlobalConstant;
import org.springframework.web.multipart.MultipartFile;

public class CaseDiagImgHandler implements CaseFileHandler{
    private static CaseDiagImgHandler instance;
    private MultipartFile src;
    private Case cse;

    private CaseDiagImgHandler(MultipartFile src, Case cse){
        this.src = src;
        this.cse = cse;
    }

    public static CaseDiagImgHandler getInstance(MultipartFile src, Case cse){
        if(instance == null){
            synchronized (CaseDiagImgHandler.class) {
                if(instance == null){
                    instance = new CaseDiagImgHandler(src, cse);
                }
            }
        }
        return instance;
    }

    @Override
    public void handleFile() {
        if (src == null || cse == null)throw new RuntimeException();
        System.out.println("=====CaseDiagImgHandler======");

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

        cse.setDiagnosisImg(filename);
        System.out.println(cse);

    }
}
