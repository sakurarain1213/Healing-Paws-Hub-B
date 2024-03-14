package com.example.hou.handler;

import com.example.hou.entity.Case;
import com.example.hou.util.FileUtil;
import com.example.hou.util.GlobalConstant;
import org.springframework.web.multipart.MultipartFile;

/**
 * 单例类
 */
public class CaseDescImgHandler implements CaseFileHandler{
    private static CaseDescImgHandler instance;
    private MultipartFile src;
    private Case cse;

    private CaseDescImgHandler(MultipartFile src, Case cse){
        this.src = src;
        this.cse = cse;
    }

    public static CaseDescImgHandler getInstance(MultipartFile src, Case cse){
        if(instance == null){
            synchronized (CaseDescImgHandler.class) {
                if(instance == null){
                    instance = new CaseDescImgHandler(src, cse);
                }
            }
        }else {
            instance.src = src;
            instance.cse = cse;
        }
        return instance;
    }

    @Override
    public void handleFile() {
        if (src == null || cse == null)throw new RuntimeException();
        System.out.println("=====CaseDescImgHandler======");
//        System.out.println(System.currentTimeMillis()); //1710344498295
//        System.out.println(src.getContentType());
//        System.out.println(src.getOriginalFilename());

//        获取文件类型后缀
        String tmp = src.getOriginalFilename();
        int idx = tmp.indexOf(".");
//        System.out.println(tmp.substring(idx));
//        System.out.println(src.getOriginalFilename().split("\\.")[1]);

        StringBuffer buf = new StringBuffer();
        buf.append(System.currentTimeMillis());
        buf.append(tmp.substring(idx));

//        String filename = src.getOriginalFilename();
        String filename = buf.toString();
        System.out.println(filename);

        String fullpath = GlobalConstant.prefix + filename;
        FileUtil.transferFile(src, fullpath);

        cse.setDescriptionImg(filename);
        System.out.println(cse);

    }
}
