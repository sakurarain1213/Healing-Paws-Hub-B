package com.example.hou.handler;

import com.example.hou.entity.Case;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class DiagImgHandler extends FileHandler<Case> {
    public DiagImgHandler(MultipartFile src, Case cse){
        super(src, cse);
    }

    @Override
    public void preHandle() {
        System.out.println("=====CaseDiagImgHandler======");

        List<Integer> size = new ArrayList<>();
        try {
            BufferedImage buf = ImageIO.read(src.getInputStream());
            int width = buf.getWidth();
            size.add(width);

            int height = buf.getHeight();
            size.add(height);

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("CaseDiagImgHandler: read size error");
        }

        cse.setDiagImgSize(size);
    }

    @Override
    public void fillCase(String filename) {
        cse.setDiagnosisImg(filename);
        System.out.println(cse);
    }

}
