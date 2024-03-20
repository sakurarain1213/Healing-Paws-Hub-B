package com.example.hou.handler;

import com.example.hou.entity.Case;
import org.springframework.web.multipart.MultipartFile;

public class CaseCheckVdoHandler extends CaseFileHandler{
    public CaseCheckVdoHandler(MultipartFile src, Case cse){
        super(src, cse);
    }

    @Override
    public void preHandle() {
        System.out.println("=====CaseCheckVdoHandler======");
    }

    @Override
    public void fillCase(String filename) {
        cse.setCheckItemVideo(filename);
        System.out.println(cse);
    }

}
