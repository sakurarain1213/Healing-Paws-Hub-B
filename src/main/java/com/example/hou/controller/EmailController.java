package com.example.hou.controller;

import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @program: Healing-Paws-Hub-B
 * @description:
 * @author: 作者
 * @create: 2024-03-08 09:52
 */
@RestController
@RequestMapping("/email")
public class EmailController {

    // 这个是 mail 依赖提供给我们的发送邮件的接口
    @Autowired
    private JavaMailSender mailSender;

    // 获取发件人邮箱
    @Value("${spring.mail.username}")
    private String sender;

    // 获取发件人昵称
    @Value("${spring.mail.nickname}")
    private String nickname;


    //引入redis
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 获取验证码
     * @param email 收件人
     * @return 验证码信息
     */
    @GetMapping("/code")
    public String getCode(@RequestParam("email")String email){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(nickname + '<' + sender + '>');
        message.setTo(email);
        message.setSubject("欢迎访问paws-hub");

        // 使用 hutool-all 生成 6 位随机数验证码
        String code = RandomUtil.randomNumbers(6);

        String content = "【验证码】您的验证码为：" + code + " 。 验证码五分钟内有效，逾期作废。\n\n\n" +
                "------------------------------\n\n\n" +
                "欢迎访问:\n\n" +
                "paws-hub项目\n\n";

        message.setText(content);

        mailSender.send(message);


        // 将验证码存储到 Redis，并设置过期时间为5分钟
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(email, code, 5, TimeUnit.MINUTES);

        return "发送成功！";
    }



    @PostMapping("/verify")
    public String verifyCode(@RequestParam("email") String email, @RequestParam("code") String code) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String correctCode = ops.get(email);




        if (correctCode == null) {
            return "验证码已过期或邮箱错误";
        } else if (!correctCode.equals(code)) {
            return "验证码错误";
        } else {
            //操作数据库
            // 在这里判断邮箱是否已经注册过
            //在这里新建用户行   前端的要求是只用邮箱注册邮箱登录  进去之后可以显示自己的昵称



            // 删除redis的验证码
            ops.getOperations().delete(email);
            return "yes";
        }
    }

}