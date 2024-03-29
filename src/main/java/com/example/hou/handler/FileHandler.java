package com.example.hou.handler;

import com.example.hou.util.FileUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * 添加不同case字段的文件路径
 */
public abstract class FileHandler<T> {
    protected MultipartFile src;
//    protected Case cse;
    protected T cse;

    public FileHandler(MultipartFile src, T cse){
        this.src = src;
        this.cse = cse;
    }

    public abstract void preHandle();

    public abstract void fillCase(String filename);

    public void handleFile() {
        if (src == null || cse == null)throw new RuntimeException();
//        System.out.println(System.currentTimeMillis()); //1710344498295
//        System.out.println(src.getContentType());
//        System.out.println(src.getOriginalFilename());
        preHandle();

//        获取文件类型后缀
//        String tmp = src.getOriginalFilename();
//        int idx = tmp.indexOf(".");
//        StringBuffer buf = new StringBuffer();
//        buf.append(UUID.randomUUID().toString());
//        buf.append(tmp.substring(idx));

//        String filename = buf.toString();
//        System.out.println(filename);

//        String fullpath = GlobalConstant.prefix + filename;
//        FileUtil.transferFile(src, fullpath);

        String filename = FileUtil.fileUpload(src);
//        String filename = "test.jpg";
        if (filename == null){
            System.out.println("fileUpload error");
            throw new RuntimeException("fileUpload error");
        }

        fillCase(filename);
    }

}
