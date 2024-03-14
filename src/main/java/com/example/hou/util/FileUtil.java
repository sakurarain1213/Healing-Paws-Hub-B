package com.example.hou.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class FileUtil {

    public static void transferFile(MultipartFile src, String dst){
        try{
            File dest = new File(dst);
            System.out.println(dest.getParentFile());

            if(!dest.getParentFile().exists()){
                System.out.println("make file");
                dest.getParentFile().mkdirs();
            }
            src.transferTo(dest);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
