package com.example.hou.handler;

import com.example.hou.entity.AffairNode;
import org.springframework.web.multipart.MultipartFile;

public class AffairNodeImgHandler extends FileHandler<AffairNode>{
    public AffairNodeImgHandler(MultipartFile src, AffairNode cse){
        super(src, cse);
    }

    @Override
    public void preHandle() {
        System.out.println("=======AffairNodeImgHandler=======");
    }

    @Override
    public void fillCase(String filename) {
        cse.setContentImg(filename);
    }
}
