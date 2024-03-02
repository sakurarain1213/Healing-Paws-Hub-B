package com.example.hou.controller;



import com.alibaba.fastjson.JSONObject;
import com.example.hou.entity.LogUser;
import com.example.hou.entity.Record;
import com.example.hou.entity.Class;
import com.example.hou.result.Result;

import com.example.hou.service.RecordService;
import com.example.hou.util.ResultUtil;
import com.gearwenxin.client.ernie.ErnieBotClient;
import com.gearwenxin.entity.response.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hsin
 * @since 2023-04-08
 */
/*

总览：大部分接口都传json格式
注意url的写法  47.103.113.75或者http://47.103.113.75 都可以
但是http不能加s  服务器不支持

//上述方法被替换为
47.103.113.75:8080/record/upload 添加记录  需要用户名和文件
注意只有这个接口不传json   要用POST 传递form-data  信息
前端应该写成
   const formData = new FormData();
      formData.append("file", this.file);
      formData.append("username", this.username);
      axios
        .post("http:// 47.103.113.75:8080/record/upload", formData)
        .then((response) => {
...


开始和结束课堂的标记   需要token  返回标准结果提示
47.103.113.75:8080/record/start
localhost:8080/record/start

47.103.113.75:8080/record/end
localhost:8080/record/end


//只需要传token
47.103.113.75:8080/record/get   查询全部记录
localhost:8080/record/get
{
    "result": "课堂主题：喂，你好你好喂，关键词：\n\n概要：。",
    "totalrecord": 8,
    "username": "王"
}






接口0  头像功能  文件上传思路：判断合法 随机命名放入服务器  得到地址放入数据库
[添加筛选 统计的实现   包括三大词汇按天统计]  调整东八区时间

接口1  每节课（前端发送一个起止40min的时间和用户  查询）  返回三大词汇次数总计OK    1.1-1.3   返回一节课三大词汇具体时间和内容

接口2   按每天返回  用户  查询）  返回三大词汇次数总计OK     2.1-2.3   返回

接口3  教学建议板块  输出多维向量
*/
@RestController
@Slf4j
@RequestMapping("/record")
public class RecordController {

    @Autowired
    RecordService recordService;//不要忘记注入

    @Resource  //gpt的服务
    private ErnieBotClient ernieBotClient;

    @PreAuthorize("@syex.hasAuthority('sys:queryUser')")
    @PostMapping("/start")
    public Result startclass()  {

        String s = recordService.startClass();
        if (s!=null) {
            return ResultUtil.success(s);
        } else {
            return ResultUtil.error("开始失败");
        }
    }

    @PreAuthorize("@syex.hasAuthority('sys:queryUser')")
    @PostMapping("/end")
    public Result endclass()  {
        String s = recordService.endClass();
        if (s!=null) {
            return ResultUtil.success(s);
        } else {
            return ResultUtil.error("结束失败");
        }
    }


    //  需要鉴权
    @PreAuthorize("@syex.hasAuthority('sys:queryUser')")
    @PostMapping("/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file)   throws Exception {
        //注意这个方法 不要传json  要用form-data
        Record r = recordService.recordUpload(file);
        if (r!=null) {
            return ResultUtil.success(r);
        } else {
            return ResultUtil.error("缺少文件或用户名或上传失败");
        }
    }

    @PreAuthorize("@syex.hasAuthority('sys:queryUser')")
    @RequestMapping("/get")
    //因为返回的是一个list  所以消息需要根据新的格式自定义
    public Result  recordGet() {
        List<Class> l = recordService.recordGetService();
        if (l!=null) {
            return ResultUtil.success(l);
        }
        else {
            // 返回一个包含错误信息的Mono<String>
            return ResultUtil.error("缺少用户与时间查询条件或查询结果为空");
        }

    }


}

