package com.example.hou.service.impl;

import com.example.hou.service.ChatService;
import com.gearwenxin.client.ernie.ErnieBotClient;
import com.gearwenxin.client.stable.StableDiffusionXLClient;
import com.gearwenxin.entity.response.ChatResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @program: juanBao
 * @description:
 * @author: 作者
 * @create: 2024-01-04 20:20
 *
 * 把controller层的方法封装移动到统一放服务层来实现
 *
 */
@Service
public class ChatServiceImpl implements ChatService {

    @Resource
    private ErnieBotClient ernieBotClient;

    @Resource
    private StableDiffusionXLClient stableDiffusionXLClient;

    public Mono<ChatResponse> chatSingle(String msg) {
        return ernieBotClient.chatSingle(msg);
    }

}
