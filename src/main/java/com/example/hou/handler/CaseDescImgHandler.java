package com.example.hou.handler;

import com.example.hou.entity.Case;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.multipart.MultipartFile;


public class CaseDescImgHandler extends CaseFileHandler{
    public CaseDescImgHandler(MultipartFile src, Case cse){
        super(src, cse);
    }

    @Override
    public void preHandle() {
        System.out.println("=====CaseDescImgHandler======");
    }

    @Override
    public void fillCase(String filename) {
        cse.setDescriptionImg(filename);
        System.out.println(cse);
    }


}
