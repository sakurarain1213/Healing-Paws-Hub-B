package com.example.hou.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.hou.anyic.WebIATWS;

import com.example.hou.entity.LogUser;
import com.example.hou.entity.Record;
import com.example.hou.mapper.RecordMapper;
import com.example.hou.service.RecordService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hsin
 * @since 2023-04-08
 */
@Service
public class RecordServiceImpl /*extends ServiceImpl<RecordMapper, Record> */implements RecordService {
    @Autowired
    RecordMapper recordMapper;//不要忘记注入

    //打标记的方法
    @Override
    public String startClass() {
        //通过token  拿到用户信息
        String name="";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 检查用户是否已经登录
        if (authentication.isAuthenticated()) {
            Object o = authentication.getPrincipal();
            if (o instanceof LogUser) {
                LogUser logUser = (LogUser) o;
                name = logUser.getUser().getUserName();
            }
        }
        String userName = name;  //json 强制要求一个final
        Date now= new Date();
        Record r = new Record();//临时插入变量
        r.setStartTime(now);
        r.setUsername(userName);
        r.setPs("<start>");
        recordMapper.insert(r);
        return "开始class标记成功";
    }
    @Override
    public String endClass(){
        String name="";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            Object o = authentication.getPrincipal();
            if (o instanceof LogUser) {
                LogUser logUser = (LogUser) o;
                name = logUser.getUser().getUserName();
            }
        }
        String userName = name;
        Date now= new Date();
        Record r = new Record();
        r.setStartTime(now);
        r.setUsername(userName);
        r.setPs("<end>");
        recordMapper.insert(r);
        return "结束class标记成功，请调用总结方法，得到最近一节课的总结";

    }


    @Override
    public Record recordUpload(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            return null;
            //缺少文件
        }
        // 检查后缀
        String originalFilename = file.getOriginalFilename();

        //通过token  拿到用户信息
        String name=" ";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 检查用户是否已经登录
        if (authentication.isAuthenticated()) {
            Object o = authentication.getPrincipal();
            if (o instanceof LogUser) {
                LogUser logUser = (LogUser) o;
                name = logUser.getUser().getUserName();
            }
        }
        String userName = name;  //json 强制要求一个final
        String filePath;

        //以下是服务器版本
        File destFile;
        try {
            // 生成新的文件名：UUID + 系统时间 + 用户名 + 原始文件后缀
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String formattedDate = dateFormat.format(new Date());
            String newFileName = UUID.randomUUID().toString() + "_" + formattedDate + "_"
                    + userName + ".pcm";
            /*originalFilename.substring(originalFilename.lastIndexOf(".")*/

            // 如果目录不存在，创建目录
            File directory = new File("/www/wwwroot/iat");

            if (!directory.exists()) {
                directory.mkdirs();
                System.out.println("不存在路径  现在已经创建了路径     ");
            }
            //  构建目标文件路径  保存文件到目标路径
            filePath = "/www/wwwroot/iat/" + newFileName;  //47.103.113.75:8080/??
            destFile = new File(filePath);
            System.out.println("这个路径是  filePath");
            // 将上传的文件保存
            try (InputStream inputStream = file.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(destFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            //return "文件上传失败";
            System.out.println("文件上传失败");
            return null;
        }

        // return "文件上传成功  但是解析部分还在测试嘤";

        //开始解析文件文本  并保存到数据库
        Date date = new Date();
        long time = date.getTime();//+ 8 * 3600000;
        date.setTime(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH mm ss "); //"yyyy-MM-dd HH:mm:ss"
        String format = dateFormat.format(date);
        //得到时分秒  format
        String Pcmfile = filePath;//保存到路径   .pcm 格式名已经有了
        WebIATWS pcm = new WebIATWS(Pcmfile);   //核心函数

        String ans = pcm.getWenben(pcm);

        System.out.println("转换的文本如下     "+ans);

        //时间格式转化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime = new Date(); // 获取当前时间
        currentTime.setTime(currentTime.getTime()); // 调整为东八区时间

        Record r = new Record();//临时插入变量
        r.setStartTime(currentTime);
        r.setUsername(userName);
        r.setTxt(ans);
        recordMapper.insert(r);
        /*
         结束调用
         */
        return r;

    }

    @Override//通过时间范围和username拿语音记录
    //查询 应该返回对象List 而不再是string
    public List<Record> recordGetService() {//传入的前端请求对象

        String name="";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            Object o = authentication.getPrincipal();
            if (o instanceof LogUser) {
                LogUser logUser = (LogUser) o;
                name = logUser.getUser().getUserName();
            }
        }
        String userName = name;
        //token  拿到用户信息

        //先根据标记找到起始位置
        // 创建 QueryWrapper 对象
        QueryWrapper<Record> qw = new QueryWrapper<>();
        qw.eq("username", userName) // 筛选用户名相匹配的行
                .eq("ps", "<start>") // 筛选 ps 列等于 start 的行
                .orderByDesc("record_id") // 按 id 列降序排列，以获取最后一个匹配的行
                .last("LIMIT 1"); // 限制结果返回一行
        Record startRecord = recordMapper.selectOne(qw);
        Date starttime=startRecord.getStartTime();


        QueryWrapper<Record> qw2 = new QueryWrapper<>();
        qw2.eq("username", userName) // 筛选用户名相匹配的行
                .eq("ps", "<end>") // 筛选 ps 列等于 <end> 的行
                .orderByDesc("record_id") // 按 record_id 列降序排列
                .last("LIMIT 1"); // 限制结果返回一行
        Record endRecord = recordMapper.selectOne(qw2);
        Date endtime=endRecord.getStartTime();


        //System.out.println(starttime+"    "+ endtime+"              ");

        QueryWrapper<Record> q = new QueryWrapper<>();
        q
                .eq("username", userName)
                .between("start_time", starttime, endtime)

        ;


        return recordMapper.selectList(q);

    }



}
