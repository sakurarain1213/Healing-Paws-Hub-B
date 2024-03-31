package com.example.hou.handler;

import com.example.hou.entity.AffairNode;
import org.springframework.web.multipart.MultipartFile;

public class AffairNodeVdoHandler extends FileHandler<AffairNode> {
    public AffairNodeVdoHandler(MultipartFile src, AffairNode cse){
        super(src, cse);
    }

    @Override
    public void preHandle() {
        System.out.println("=======AffairNodeVdoHandler=======");
    }

    @Override
    public void fillCase(String filename) {
        cse.setContentVideo(filename);
    }
}
