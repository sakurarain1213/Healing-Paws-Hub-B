package com.example.hou.controller;

import com.example.hou.entity.LogUser;
import com.example.hou.result.Result;
import com.example.hou.service.ChatService;
import com.example.hou.service.UserInfoService;
import com.gearwenxin.client.ernie.ErnieBotClient;
import com.gearwenxin.client.stable.StableDiffusionXLClient;
import com.gearwenxin.entity.enums.SamplerType;
import com.gearwenxin.entity.request.ImageBaseRequest;
import com.gearwenxin.entity.request.PromptRequest;
import com.gearwenxin.entity.response.ChatResponse;
import com.gearwenxin.entity.response.ImageResponse;
import com.gearwenxin.entity.response.PromptResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: juanBao
 * @description:
 * @author: 作者
 * @create: 2023-12-31 01:26


localhost:8080/test/chat
47.103.113.75:8080/test/chat

 注意  要在body中的  x-www  urlencoded中   键：msg   值 ：“需要问的问题”   得到
{
"id": "as-iujnte6sa0",
"object": "chat.completion",
"created": 1703961053,
"sentence_id": null,
"is_end": null,
"is_truncated": false,
"result": "你好，我是文心一言，英文名是ERNIE Bot，可以协助你完成范围广泛的任务并提供有关各种主题的信息，比如回答问题，提供定义和解释及建议。如果你有任何问题，请随时向我提问。",
"need_clear_history": false,
"usage": {
"prompt_tokens": 3,
"completion_tokens": 48,
"total_tokens": 51,
"plugins": null
},
"ban_round": null,
"error_code": null,
"eb_code": null,
"error_msg": null,
"function_call": null
}



localhost:8080/test/chats
 有记忆功能
{
"result": "阿巴阿巴阿巴",
"chatid": "as-2gp1siu1aa",
"username": "王"
}




localhost:8080/test/stream/chat
 流式返回   逐字返回  适合做动态效果   但是要在form-data  里用msg 和值   发送
只需要msg



 长对话流返回
在form-data  传递两个参数
msgUid:自己定义  随便取   可以是用户+对话编号
msg


 */



@RestController
@RequestMapping("/test")   //用于放行  不鉴权
public class ChatController {

    // 要调用的模型的客户端
    @Autowired
    ChatService chatService;


    @Resource
    private ErnieBotClient ernieBotClient;

    @Resource
    private StableDiffusionXLClient stableDiffusionXLClient;

    // 单次对话
    //这个接口已经加入匿名配置  不用传token
    @PostMapping("/chat")
    public Mono<ChatResponse> chatSingle(String msg) {
            return ernieBotClient.chatSingle(msg);
    }

    // 连续对话  一次性返回
    //需要token
    @PreAuthorize("@syex.hasAuthority('sys:queryUser')")
    @PostMapping(value = "/chats", produces = MediaType.APPLICATION_JSON_VALUE)//string 变 json的注释
    public Mono<String> chatCont(String msg) {       //注意这里改了返回值的类型    如果要回复  参见/chat

        //需要对话的ID   后续封装成函数即可   id由用户名+编号  决定
        String chatUID = "test-user-1001";
        //return ernieBotClient.chatCont(msg, chatUID);
        //现在返回的json太冗余   精简处理
        // Mono<ChatResponse> 是响应式编程模型  Mono 是一个表示单个异步值的容器

        String testmsg="从我们酒店出发，到八达岭长城需要一个多小时。在这点时间里，先让小陈给各位朋友讲讲。八达岭长城是万里长城中最精华的一部分，它位于北京市延庆县军都山的关沟古道。相信大家一定知道，长城是我们中国古代最伟大的防御工程，而八达岭长城就是万里长城的重要部分，因深厚的文化历史内涵而著名。八达岭长城建于明代，公元1505年。但由于战乱不断，又缺乏修缮，到近代，这段长城早就是满目疮痍。直到1984年，邓小平同志发起“爱我中华，修我长城”的倡议，长城又焕然一新。现在已被列入《世界文化遗产名录》和“北京旅游世界之最”中第一名。" +
                "　好，各位朋友，说着说着，我们就要到了。下车以后，咱们先在门口集中，等小陈买了票再带各" +
                "位进去。各位游客，来来来。现在在我们面前的这段城墙就是重新修缮的。全长3741米，面积达1.9万平方米。感兴趣的`游客朋友不妨触摸一下城墙，也许你还能感受到古代战场上的滚滚硝烟和漫山遍野的喊杀声。" +
                "在这里，小陈想问问各位游客，有谁知道“八达岭”名字的来历吗？关于这个名字的来历，有好" +
                "几种说法，我个人最喜欢的是由“八大岭”谐音而得名一说。因这一带山峦层叠，地势险峻，据说所" +
                "建的长城在这里要转八道弯，越过八座大的山岭，当年兴建这段长城很艰难，工期迟迟完不成，曾先" +
                "后有八个监工为其而死。最神奇的是，最后通过仙人的点化，采取“修城八法”，即“虎带笼头羊背" +
                "鞍，燕子衔泥猴搭肩，龟驮石条兔引路，喜鹊搭桥冰铺栈”，才把建筑材料运送到山上。所以人们就" +
                "把这段长城称为“八大岭长城”，后来地名就谐音成“八达岭”。是不是挺有意思的？"  ;


        //order  命令提示 要求可以补充
        String order="概括[]内的内容，"+
                "场景是课堂，可以忽略语气词。"+
                "要求提炼课堂主题，关键词，概要，三者缺一不可！"+
                "并在概要中把涉及到的关键词句用<>标识。"+

                "["+testmsg+"]"
                ;
        Mono<ChatResponse> responseMono = ernieBotClient.chatCont(order, chatUID);


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
        String finalName = name;  //json 强制要求一个final


        return responseMono.map(chatResponse -> {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("chatid", chatResponse.getId());
            jsonObj.put("username", finalName);
            jsonObj.put("result", chatResponse.getResult());
            return jsonObj.toString();
        });
    }

    // 流式返回,单次对话
    @GetMapping(value = "/stream/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatSingleStream(@RequestParam String msg) {
        return ernieBotClient.chatSingleOfStream(msg)
                .map(response -> "data: " + response.getResult() + "\n\n");
    }

    // 流式返回,连续对话
    @GetMapping(value = "/stream/chats", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatContStream(@RequestParam String msg, @RequestParam String msgUid) {
        return ernieBotClient.chatContOfStream(msg, msgUid)
                .map(response -> "data: " + response.getResult() + "\n\n");
    }


    // 文生图
    @PostMapping("/image")
    public Mono<ImageResponse> chatImage() {
        ImageBaseRequest imageBaseRequest = ImageBaseRequest.builder()
                // 提示词
                .prompt("一个头发中分并且穿着背带裤的人")
                // 大小
                .size("1024x1024")
                // 反向提示词（不包含什么）
                .negativePrompt("鸡")
                // 生成图片数量（1-4）
                .n(1)
                // 迭代轮次（10-50）
                .steps(20)
                // 采样方式
                .samplerIndex(SamplerType.Euler_A.getValue())
                .userId("1001")
                .build();

        return stableDiffusionXLClient.chatImage(imageBaseRequest);
    }



    /*
    // 要调用的模型的客户端（示例为文心4.0）
    @Resource
    private ErnieBot4Client ernieBot4Client;
    @Resource
    private StableDiffusionXLClient stableDiffusionXLClient;

    // 单次对话
    @PostMapping("/chat")
    public Mono<ChatResponse> chatSingle(String msg) {
        return ernieBot4Client.chatSingle(msg);
    }

    // 连续对话
    @PostMapping("/chats")
    public Mono<ChatResponse> chatCont(String msg) {
        String chatUID = "test-user-1001";
        return ernieBot4Client.chatCont(msg, chatUID);
    }

    // 流式返回，单次对话
    @GetMapping(value = "/stream/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatSingleStream(@RequestParam String msg) {
        return ernieBot4Client.chatSingleOfStream(msg)
                .map(response -> "data: " + response.getResult() + "\n\n");
    }

    // 流式返回，连续对话
    @GetMapping(value = "/stream/chats", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatContStream(@RequestParam String msg, @RequestParam String msgUid) {
        return ernieBot4Client.chatContOfStream(msg, msgUid)
                .map(response -> "data: " + response.getResult() + "\n\n");
    }

    // Prompt模板   有bug
    /*
    @PostMapping("/prompt")
    public Mono<PromptResponse> chatSingle() {
        Map<String, String> map = new HashMap<>();
        map.put("article", "我看见过波澜壮阔的大海，玩赏过水平如镜的西湖，却从没看见过漓江这样的水。漓江的水真静啊，静得让你感觉不到它在流动。");
        map.put("number", "20");
        PromptRequest promptRequest = new PromptRequest();
        promptRequest.setId('1');
        promptRequest.setParamMap(map);

        return promptBotClient.chatPrompt(promptRequest);
    }
     */
    // 文生图
    /*
    @PostMapping("/image")
    public Mono<ImageResponse> chatImage() {
        ImageBaseRequest imageBaseRequest = ImageBaseRequest.builder()
                // 提示词
                .prompt("一个头发中分并且穿着背带裤的人")
                // 大小
                .size("1024x1024")
                // 反向提示词（不包含什么）
                .negativePrompt("鸡")
                // 生成图片数量（1-4）
                .n(1)
                // 迭代轮次（10-50）
                .steps(20)
                // 采样方式
                .samplerIndex(SamplerType.Euler_A.getValue())
                .userId("1001")
                .build();

        return stableDiffusionXLClient.chatImage(imageBaseRequest);
    }

*/

}
