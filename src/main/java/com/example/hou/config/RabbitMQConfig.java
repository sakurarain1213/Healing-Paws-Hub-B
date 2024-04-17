package com.example.hou.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: Healing-Paws-Hub-B
 * @description:
 * @author: 作者
 * @create: 2024-04-17 20:14
 */
@Configuration
public class RabbitMQConfig {

    //定义队列和交换机

    //这边暂时只有考试业务的队列
    public static final String EXAM_QUEUE = "exam.queue";
    public static final String EXAM_EXCHANGE = "exam.exchange";
    public static final String EXAM_ROUTING_KEY = "exam.routingkey";

    @Bean
    public Queue examQueue() {
        return new Queue(EXAM_QUEUE, true); // 持久化队列  队列来自amqp.core包
    }

    @Bean
    public DirectExchange examExchange() {
        return new DirectExchange(EXAM_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(EXAM_ROUTING_KEY);
    }
}