package com.example.hou.util;

import com.example.hou.entity.LogUser;
import com.example.hou.entity.Record;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileUtil {

    //开发环境同目录文件
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

    //服务器版本的单文件上传方法 要求static
    public static String fileUpload(MultipartFile file){
        if (file == null || file.isEmpty()) {
            return null;   //缺少文件
        }
        // 后缀
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.lastIndexOf(".") == -1){
            return null;
        }      // 是否包含后缀   无后缀异常
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        //todo 考虑把目标后缀作为函数参数检测
//        完成文档内要求的所有格式判断
        if (!"jpg".equalsIgnoreCase(fileExtension) && !"png".equalsIgnoreCase(fileExtension) &&
            !"bmp".equalsIgnoreCase(fileExtension) && !"gif".equalsIgnoreCase(fileExtension) &&
            !"asx".equalsIgnoreCase(fileExtension) && !"asf".equalsIgnoreCase(fileExtension) &&
            !"mpg".equalsIgnoreCase(fileExtension) && !"wmv".equalsIgnoreCase(fileExtension) &&
            !"3gp".equalsIgnoreCase(fileExtension) && !"mp4".equalsIgnoreCase(fileExtension) &&
            !"mov".equalsIgnoreCase(fileExtension) && !"avi".equalsIgnoreCase(fileExtension) &&
            !"flv".equalsIgnoreCase(fileExtension) && !"rmvb".equalsIgnoreCase(fileExtension)
            ) {
            return null; // 后缀不正确异常
        }


        //通过token  拿到用户信息    考虑去除
        String name="123";
        /*
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 检查用户是否已经登录
        if (authentication.isAuthenticated()) {
            Object o = authentication.getPrincipal();
            if (o instanceof LogUser) {
                LogUser logUser = (LogUser) o;
                name = logUser.getUser().getUserName();
            }
        }
        */
        String userName = name;  //json 强制要求一个final
        String filePath;
        //上传

        try {
            // 生成新文件名：UUID + 系统时间 + 用户名 + 原始文件的后缀
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String formattedDate = dateFormat.format(new Date());
            String newFileName = UUID.randomUUID().toString() + "_" + formattedDate + "_"
                    + userName + "."+fileExtension;
            /*originalFilename.substring(originalFilename.lastIndexOf(".")*/

            // 如果目录不存在，创建目录  todo  考虑根据文件类型分文件夹


            File directory = new File("/www/wwwroot/mediaFile");
            if (!directory.exists()) {
                directory.mkdirs();
                System.out.println("不存在路径，现在已经创建了路径");
            }
            //  构建目标文件路径  保存文件到目标路径
            //debug
            //47.103.113.75:8080/??
            File destFile = new File( "/www/wwwroot/mediaFile/" + newFileName);
            System.out.println("这个文件是"+newFileName);
            // 将上传的文件保存
            file.transferTo(destFile);
             /*这个版本额外控制 手动实现缓冲区大小 写入流逻辑 进度控制  但是性能不如transfer 不简洁
            try (InputStream inputStream = file.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(destFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            */
            return GlobalConstant.urlAccessFilePrefix + newFileName;   //成功
        } catch (IOException e) {
            e.printStackTrace();
            //return "文件上传失败";

            System.out.println("文件上传失败");
            return null;
        }

    }

    public static String deleteFile(String url) {
        //url是http格式的  例如http://150.158.110.63:8080/images/123.png  现在要提取出文件名123.png 然后拼接文件夹路径实现删除
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        // 构建文件完整路径
        File file = new File("/www/wwwroot/mediaFile/" + fileName);
        // 检查文件是否存在
        if (file.exists()) {
            // 删除文件
            boolean isDeleted = file.delete();
            if (isDeleted) {
                System.out.println("文件 " + fileName + " 删除成功"); return "ok";
            } else {
                System.out.println("文件 " + fileName + " 删除失败"); return null;
            }
        } else {
            System.out.println("文件 " + fileName + " 不存在");return null;
        }
    }

}


//    TODO 传String， 返回multipartfile



