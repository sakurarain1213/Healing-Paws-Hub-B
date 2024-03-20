//package com.example.hou;
//
//import com.example.hou.entity.Disease;
//import com.example.hou.entity.LogUser;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.UUID;
//
///**
// * @program: Healing-Paws-Hub-B
// * @description:
// * @author: 作者
// * @create: 2024-03-20 14:53
// */
//@SpringBootTest
//public class MongoDBTest {
//    @Autowired
//    private MongoTemplate template;
//    @Test
//    void f(){
//        Query query = new Query();
//        Criteria criteria = Criteria.where("name").is("骨折");
//        query.addCriteria(criteria);
//        long count = template.count(query, Disease.class);
//        System.out.println(count);
//
//
//    }
//
//
//    @Test
//    void file() throws IOException {
//
//        File file = new File("C:\\Users\\w1625\\Desktop\\1.pdf");
//        FileInputStream input = new FileInputStream(file);
//        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), "application/octet-stream", input);
//
//        String originalFilename = multipartFile.getOriginalFilename();
//        if (originalFilename == null || originalFilename.lastIndexOf(".") == -1){
//            System.out.println("后缀nope");return;}      // 是否包含后缀   无后缀异常
//        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
//        //todo 考虑把目标后缀作为函数参数检测
//        if (!"jpg".equalsIgnoreCase(fileExtension) && !"pdf".equalsIgnoreCase(fileExtension)) {
//            System.out.println("后缀不对");return;
//        }
//
//
//        //通过token  拿到用户信息    考虑去除
//        String name="123";
//
//        String filePath;
//
//
//        //上传
//        File destFile;
//
//            // 生成新文件名：UUID + 系统时间 + 用户名 + 原始文件的后缀
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
//            String formattedDate = dateFormat.format(new Date());
//            String newFileName = UUID.randomUUID().toString() + "_" + formattedDate + "_"
//                    + name + "."+fileExtension;
//            /*originalFilename.substring(originalFilename.lastIndexOf(".")*/
//
//            // 如果目录不存在，创建目录  todo  考虑根据文件类型分文件夹
//            File directory = new File("/www/wwwroot/mediaFile");
//            if (!directory.exists()) {
//                directory.mkdirs();
//                System.out.println("不存在路径，现在已经创建了路径");
//            }
//            filePath = "/www/wwwroot/mediaFile/" + newFileName;  //47.103.113.75:8080/??
//            destFile = new File(filePath);
//
//            multipartFile.transferTo(destFile);
//
//        System.out.println("这个路径是"+filePath);
//    }
//
//}
