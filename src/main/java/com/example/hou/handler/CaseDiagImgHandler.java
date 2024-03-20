package com.example.hou.handler;

import com.example.hou.entity.Case;
import org.springframework.web.multipart.MultipartFile;

public class CaseDiagImgHandler extends CaseFileHandler{
    public CaseDiagImgHandler(MultipartFile src, Case cse){
        super(src, cse);
    }

    @Override
    public void preHandle() {
        System.out.println("=====CaseDiagImgHandler======");
    }

    @Override
    public void fillCase(String filename) {
        cse.setDiagnosisImg(filename);
        System.out.println(cse);
    }

}
