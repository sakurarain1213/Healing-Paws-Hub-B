package com.example.hou.controller;

import com.example.hou.entity.LogUser;
import com.example.hou.entity.MultiFileVo;
import com.example.hou.entity.SysUser;
import com.example.hou.result.Result;
import com.example.hou.util.GlobalConstant;
import com.example.hou.util.ResultUtil;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/file")
public class FileController {
    public static String BASE_DIR = "/www/wwwroot/mediaFile/";

    @PostMapping("/uploadMultiChunk")
    public Result uploadMultiChunkedFile(@NonNull @ModelAttribute MultiFileVo multiFileVo){
        MultipartFile[] mediaFiles = multiFileVo.getFiles();
        if (mediaFiles == null || mediaFiles.length == 0) return ResultUtil.error("缺少必需参数");

        System.out.println(mediaFiles.length);

        for(MultipartFile f : mediaFiles){
            System.out.println(f.getOriginalFilename());
        }
        System.out.println("==========");


        Integer[] elfs = multiFileVo.getElfs();
        if(elfs == null || elfs.length == 0) return ResultUtil.error("缺少必需参数");

        for(int elf : elfs){
            System.out.println(elf);
        }
        System.out.println("==========");


        mkdir();

//        获取userName
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated())return ResultUtil.error("未登录");

        LogUser loginUser = (LogUser) authentication.getPrincipal();
        String userName = Optional.ofNullable(loginUser)
                .map(LogUser::getUser)
                .map(SysUser::getUserName)
                .orElse(null);

        if (userName == null){
            userName = "default";
        }



        List<String> retList = new ArrayList<>();
        int start = 0;
        for(int i = 0; i < elfs.length; i++){
            int end = elfs[i];

            if(end < start)return ResultUtil.error("存在elf不合法");;

            String originalFilename = mediaFiles[start].getOriginalFilename();
            // 生成新文件名：UUID + 系统时间 + 用户名 + 原始文件的后缀
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

            if (!checkValidExtension(fileExtension)) return ResultUtil.error("文件格式不支持");


            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String formattedDate = dateFormat.format(new Date());
            String newFileName = UUID.randomUUID().toString() + "_" + formattedDate + "_"
                    + userName + "."+fileExtension;



//            File destFile = new File(newFileName);
            String fullPath = BASE_DIR + newFileName;
            System.out.println("fullPath: " + fullPath);
            System.out.println("newFileName: " + newFileName);

//            完成一个大文件的上传
            try(FileOutputStream fileOutputStream = new FileOutputStream(fullPath);
                BufferedOutputStream outputStream = new BufferedOutputStream(fileOutputStream)){

                byte[] buffer = new byte[1024];
                for(int j = start; j <= end; j++){
                    InputStream ips = mediaFiles[j].getInputStream();

                    int byteNum = 0;
                    while((byteNum = ips.read(buffer)) != -1){
                        outputStream.write(buffer, 0, byteNum);
                    }


                    ips.close();
                }

            }catch (Exception e){
                e.printStackTrace();
                return ResultUtil.error("文件上传失败");
            }

            retList.add(GlobalConstant.urlAccessFilePrefix + newFileName);

            start = end + 1;
        }


        return ResultUtil.success(retList);
    }

    public void mkdir(){
        File directory = new File("/www/wwwroot/mediaFile");
        if (!directory.exists()) {
            directory.mkdirs();
            System.out.println("不存在路径，现在已经创建了路径");
        }
    }

    public boolean checkValidExtension(String fileExtension){
        return  "jpg".equalsIgnoreCase(fileExtension) || "png".equalsIgnoreCase(fileExtension) ||
                "bmp".equalsIgnoreCase(fileExtension) || "gif".equalsIgnoreCase(fileExtension) ||
                "asx".equalsIgnoreCase(fileExtension) || "asf".equalsIgnoreCase(fileExtension) ||
                "mpg".equalsIgnoreCase(fileExtension) || "wmv".equalsIgnoreCase(fileExtension) ||
                "3gp".equalsIgnoreCase(fileExtension) || "mp4".equalsIgnoreCase(fileExtension) ||
                "mov".equalsIgnoreCase(fileExtension) || "avi".equalsIgnoreCase(fileExtension) ||
                "flv".equalsIgnoreCase(fileExtension) || "rmvb".equalsIgnoreCase(fileExtension);
    }
}
