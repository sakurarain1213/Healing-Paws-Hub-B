package com.example.hou.service.impl;

import com.example.hou.entity.Class;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.hou.anyic.WebIATWS;

import com.example.hou.entity.LogUser;
import com.example.hou.entity.Record;
import com.example.hou.mapper.ClassMapper;
import com.example.hou.mapper.RecordMapper;
import com.example.hou.service.ClassService;
import com.example.hou.service.RecordService;
import com.gearwenxin.client.ernie.ErnieBotClient;
import com.gearwenxin.entity.response.ChatResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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

    @Resource
    private ErnieBotClient ernieBotClient;
    @Autowired
    private ClassService classService;
    @Autowired
    private ClassMapper classMapper;

    //打标记的方法
    //每次 开始  要对class增加一个id   然后把此id加入record
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



        int newId = classService.maxClassID() + 1;
        System.out.println(newId);
        // 创建新的ClassEntity对象并设置属性
        Class newClass = new Class();
        newClass.setId(newId);
        newClass.setTxt("anything is ok");
        // 设置其他必要属性
        // 将新记录插入到class表中
        classMapper.insert(newClass);

        r.setClassId(newId);
        r.setStartTime(now);
        r.setUsername(userName);
        r.setPs("<start>");
        recordMapper.insert(r);
        return "开始class标记成功";
    }
    @Override
    public String endClass(){
        //核心步骤  在结束的时候   把至今的文本  存进class表的txt   然后调用GPT  ，把总结存进class

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


        int Id = classService.maxClassID();  //当前课程的id

        r.setClassId(Id);
        r.setStartTime(now);
        r.setUsername(userName);
        r.setPs("<end>");
        recordMapper.insert(r);

        //收集和处理文本
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_id", Id ).eq("username", userName);
        List<Record> records = recordMapper.selectList(queryWrapper);

        StringBuilder textBuilder = new StringBuilder();
        for (Record record : records) {
            if (record.getTxt() != null && !record.getTxt().isEmpty()) {
                textBuilder.append(record.getTxt());
            }
        }
        // 存储拼接的文本到 class 表
        Class classEntity = classMapper.selectById(Id);
        if (classEntity != null) {
            classEntity.setTxt(textBuilder.toString());
            classMapper.updateById(classEntity);
        }

        /*处理异步调用并在结果可用时执行后续操作是一个常见的编程挑战，
        特别是在涉及到外部服务调用（如在您的例子中使用 ErnieBot）时。
        在 Java 中，您可以使用多种方式来处理异步结果，
        例如使用回调、Future、CompletableFuture 或 Reactor 中的 Mono。*/

        // 可选：处理拼接后的文本（例如调用 GPT 进行总结）



        String order = "概括[]内的内容，"
                + "场景是课堂，可以忽略语气词。"
                + "要求提炼课堂主题，关键词，概要，三者缺一不可！"
                + "并在概要中把涉及到的关键词句用<>标识。"
                + "[" + textBuilder.toString() + "]";

        Mono<ChatResponse> responseMono = ernieBotClient.chatSingle(order);
        responseMono.subscribe(chatResponse -> {
            // 从 chatResponse 获取摘要信息
            String summary = chatResponse.getResult();
            // 将摘要信息存储到数据库
            int classId = classService.maxClassID();
            Class c = classMapper.selectById(classId);
            if (c != null) {
                c.setSummary(summary);
                classMapper.updateById(c);
            }
            // 这里执行任何其他需要的操作
        }, error -> {
            // 错误处理
            System.err.println("Error: " + error.getMessage());
        });


        return "结束class标记成功，请调用class的总结方法，得到最近一节课的总结";

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



        int Id = classService.maxClassID();  //当前课程的id

        r.setClassId(Id);  //当前课程的id
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
    public List<Class> recordGetService() {//传入的前端请求对象

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
        /*
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
        */
        //现在  先根据userName  在record表里找最近五次的class_id  降序
        //然后根据这组id   去class表里   收集每个class 存入list<Class>  最后返回
            QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("DISTINCT class_id")  // 选择不重复的 class_id
                    .eq("username", userName)
                    .orderByDesc("class_id")
                    .last("LIMIT 5");
            List<Record> records = recordMapper.selectList(queryWrapper);

            /*List<Integer> classIds = records.stream()   先不管了  时间紧  先直接取课程最后 不管用户
                .map(Record::getClassId)
                .distinct() // 确保 class_id 是独特的
                .collect(Collectors.toList());*/


        int Id = classService.maxClassID();

        List<Class> classEntities = new ArrayList<>();
        for (int id =Id;id>Id-4;id--) {
            Class classEntity = classMapper.selectById(id);
            if (classEntity != null) {
                classEntities.add(classEntity);
                System.out.println("已经获得类");
            }
        }

        return classEntities;

    }



}
