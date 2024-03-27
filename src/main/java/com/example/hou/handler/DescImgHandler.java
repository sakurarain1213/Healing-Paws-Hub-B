package com.example.hou.handler;

import com.example.hou.entity.Case;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class DescImgHandler extends FileHandler<Case> {
    public DescImgHandler(MultipartFile src, Case cse){
        super(src, cse);
    }

    @Override
    public void preHandle() {
        System.out.println("=====CaseDescImgHandler======");
        List<Integer> size = new ArrayList<>();
        try {
            BufferedImage buf = ImageIO.read(src.getInputStream());
            int width = buf.getWidth();
            size.add(width);

            int height = buf.getHeight();
            size.add(height);

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("CaseDescImgHandler: " + e.getMessage());
        }

        cse.setDescImgSize(size);
    }

    @Override
    public void fillCase(String filename) {
        cse.setDescriptionImg(filename);
        System.out.println(cse);
    }


}
