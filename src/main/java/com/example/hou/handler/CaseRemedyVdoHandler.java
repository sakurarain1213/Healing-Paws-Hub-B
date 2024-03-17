package com.example.hou.handler;

import com.example.hou.entity.Case;
import org.springframework.web.multipart.MultipartFile;

public class CaseRemedyVdoHandler extends CaseFileHandler{
    public CaseRemedyVdoHandler(MultipartFile src, Case cse){
        super(src, cse);
    }

    @Override
    public void preHandle() {
        System.out.println("=====CaseRemedyVdoHandler======");
    }

    @Override
    public void fillCase(String filename) {
        cse.setRemedyVideo(filename);
        System.out.println(cse);
    }

}
