package com.example.hou.handler;

import com.example.hou.entity.Case;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取图片长宽list需要读取 MultipartFile 的流，在流被消费(文件上传成功后)再读取会失败，必须在preHandle完成读取
 */
public class CheckImgHandler extends FileHandler<Case> {
    public CheckImgHandler(MultipartFile src, Case cse){
        super(src, cse);
    }

    @Override
    public void preHandle() {
        System.out.println("=====CaseCheckImgHandler======");

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
    }

    @Override
    public void fillCase(String filename) {
        cse.setCheckItemImg(filename);
        System.out.println(cse);
    }


}
