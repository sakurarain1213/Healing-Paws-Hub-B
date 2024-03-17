package com.example.hou.handler;

import com.example.hou.entity.Case;
import org.springframework.web.multipart.MultipartFile;

public class CaseRemedyImgHandler extends CaseFileHandler{
    public CaseRemedyImgHandler(MultipartFile src, Case cse){
        super(src, cse);
    }

    @Override
    public void preHandle() {
        System.out.println("=====CaseRemedyImgHandler======");
    }

    @Override
    public void fillCase(String filename) {
        cse.setRemedyImg(filename);
        System.out.println(cse);
    }

}
