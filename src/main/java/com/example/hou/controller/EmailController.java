package com.example.hou.controller;

import cn.hutool.core.util.RandomUtil;
import com.example.hou.entity.LoginUserParam;
import com.example.hou.entity.SysUser;
import com.example.hou.result.Result;
import com.example.hou.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @program: Healing-Paws-Hub-B
 * @description:   邮箱模块修改 只需要在邮件中点击包含token的链接即可完成注册 不需要验证码
 *                  总之是分两步注册  先存redis  再取redis  完成注册
 * @author: 作者
 * @create: 2024-03-08 09:52
 */
@RestController
@RequestMapping("/email")
public class EmailController {


    @Autowired
    private SecurityUserService userService;


    // 这个是 mail 依赖提供给我们的发送邮件的接口
    @Autowired
    private JavaMailSender mailSender;

    // 获取发件人邮箱
    @Value("${spring.mail.username}")
    private String sender;

    // 获取发件人昵称
    @Value("${spring.mail.nickname}")
    private String nickname;

    @Value("${server.ip}")
    private String IP;

    //引入redis
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 测试通过  传账号和密码的json  注意后续统一一下返回格式result
     */
    @PostMapping("/register")
    public String getCode(@RequestBody LoginUserParam request) throws MessagingException {
        String email = request.getUserName();//注意前端json的变量名
        String password = request.getPassword();

        // 加密密码   注意注册时候的加密方法要和登录时一样 (已经统一)
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        // 生成唯一令牌
        String token = UUID.randomUUID().toString();

        // 将令牌和加密后的密码存储到 Redis，并设置过期时间为5分钟   注意先用分割：的string实现 序列化对象为json的方法要参考login
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(token, email + ":" + encodedPassword, 5, TimeUnit.MINUTES);

        // 创建包含令牌的URL链接  注意具体/子路径和端口号
        String url = "http://"+IP+":8080/email/confirm?token=" + token;



        //SimpleMailMessage message = new SimpleMailMessage();  这是纯文本的写法 考虑html的美观邮件写法
        //message.setFrom(nickname + '<' + sender + '>');
        //message.setTo(email);
        //message.setSubject("欢迎访问PawsHub项目");

        // 使用 hutool-all 生成 6 位随机数验证码
        //String code = RandomUtil.randomNumbers(6);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(nickname + '<' + sender + '>');
        helper.setTo(email);
        helper.setSubject("欢迎访问PawsHub项目");

        String content = "<p>【注册】请点击以下链接完成注册：<a href=\"" + url + "\">" + url + "</a> 。 链接五分钟内有效，逾期作废。</p>" +
                "<p>------------------------------</p>" +
                "<p>本邮件自动发送，无需回复</p>" +
                "<p>paws-hub项目</p>";

        helper.setText(content, true);

        mailSender.send(message);

        return "发送成功！";
    }


//当前问题：邮箱得到的链接要改成本地的localhost才能测试   bug是在提取username时好像为null 尝试单元测试
    @GetMapping("/confirm")
    public Result confirm(@RequestParam("token") String token) {
        // 检查令牌是否存在并且未过期
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String value = ops.get(token);

        if (value == null) {
            return new Result(-100, "链接已过期或无效", null);
        } else {
            // 分割电子邮件地址和密码
            String[] parts = value.split(":");
            String email = parts[0];
            String password = parts[1];//注意已经加密过

            // 创建新的用户对象  注意表内属性名字
            SysUser newUser = new SysUser();
            newUser.setAccount(email);//注意这里是account
            newUser.setPassword(password);

            // 设置其他字段
            newUser.setEnabled(true);
            newUser.setAccountNotExpired(true);
            newUser.setAccountNotLocked(true);
            newUser.setCredentialsNotExpired(true);
            newUser.setCreateTime(new Date());
            newUser.setUpdateTime(new Date());

            return userService.createUser(newUser);
            /*
            // 完成用户注册
            SysUser newUser = userService.createUser(email);

            // 删除redis的令牌
            ops.getOperations().delete(token);

            if (newUser != null) {
                return "注册成功！";
            } else {
                return "注册失败，可能是因为电子邮件地址已被使用。";
            }

            */
        }
    }

}