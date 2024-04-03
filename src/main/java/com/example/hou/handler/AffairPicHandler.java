package com.example.hou.handler;

import com.example.hou.entity.Affair;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class AffairPicHandler extends FileHandler<Affair>{
    public AffairPicHandler(MultipartFile src, Affair cse){
        super(src, cse);
    }

    @Override
    public void preHandle() {
        System.out.println("=====AffairPicHandler======");

        List<Integer> size = new ArrayList<>();
        try {
            BufferedImage buf = ImageIO.read(src.getInputStream());
            int width = buf.getWidth();
            size.add(width);

            int height = buf.getHeight();
            size.add(height);

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("AffairPicHandler: read size error");
        }

        cse.setPicSize(size);
    }

    @Override
    public void fillCase(String filename) {
        cse.setPic(filename);
    }
}
