package com.example.hou.controller;

import com.example.hou.result.Result;
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

    @Resource
    private ErnieBotClient ernieBotClient;

    @Resource
    private StableDiffusionXLClient stableDiffusionXLClient;

    // 单次对话
    @PostMapping("/chat")
    public Mono<ChatResponse> chatSingle(String msg) {
            return ernieBotClient.chatSingle(msg);
    }

    // 连续对话  一次性返回
    @PostMapping(value = "/chats", produces = MediaType.APPLICATION_JSON_VALUE)//string 变 json的注释
    public Mono<String> chatCont(String msg) {       //注意这里改了返回值的类型    如果要回复  参见/chat

        //需要对话的ID   后续封装成函数即可   id由用户名+编号  决定
        String chatUID = "test-user-1001";
        //return ernieBotClient.chatCont(msg, chatUID);
        //现在返回的json太冗余   精简处理
        // Mono<ChatResponse> 是响应式编程模型  Mono 是一个表示单个异步值的容器
        Mono<ChatResponse> responseMono = ernieBotClient.chatCont(msg, chatUID);

        return responseMono.map(chatResponse -> {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("id", chatResponse.getId());
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
