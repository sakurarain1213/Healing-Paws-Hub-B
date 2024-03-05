package com.example.hou.service;

import com.gearwenxin.entity.response.ChatResponse;
import reactor.core.publisher.Mono;

public interface ChatService {

    public Mono<ChatResponse> chatSingle(String msg);


}
