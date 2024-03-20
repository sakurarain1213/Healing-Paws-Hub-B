package com.example.hou.handler;

import com.example.hou.entity.Case;
import org.springframework.web.multipart.MultipartFile;


public class DescVdoHandler extends FileHandler<Case> {
    public DescVdoHandler(MultipartFile src, Case cse){
        super(src, cse);
    }

    @Override
    public void preHandle() {
        System.out.println("=====CaseDescVdoHandler======");
    }

    @Override
    public void fillCase(String filename) {
        cse.setDescriptionVideo(filename);
        System.out.println(cse);
    }

}
