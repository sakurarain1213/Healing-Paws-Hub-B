package com.example.hou.handler;

import com.example.hou.entity.Case;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


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

        List<Integer> size = new ArrayList<>();
        try {
            BufferedImage buf = ImageIO.read(src.getInputStream());
            int width = buf.getWidth();
            size.add(width);

            int height = buf.getHeight();
            size.add(height);

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("CaseCheckImgHandler: read size error");
        }

        cse.setCheckImgSize(size);

        System.out.println(cse);
    }


}
