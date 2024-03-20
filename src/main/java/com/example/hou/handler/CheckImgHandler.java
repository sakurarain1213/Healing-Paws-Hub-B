package com.example.hou.handler;

import com.example.hou.entity.Case;
import org.springframework.web.multipart.MultipartFile;


public class CheckImgHandler extends FileHandler<Case> {
    public CheckImgHandler(MultipartFile src, Case cse){
        super(src, cse);
    }

    @Override
    public void preHandle() {
        System.out.println("=====CaseCheckImgHandler======");
    }

    @Override
    public void fillCase(String filename) {
        cse.setCheckItemImg(filename);
        System.out.println(cse);
    }


}
