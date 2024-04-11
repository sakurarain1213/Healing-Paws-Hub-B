package com.example.hou.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.hou.entity.LoginUserParam;
import com.example.hou.entity.SysUser;
import com.example.hou.mapper.SysUserMapper;
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
    SysUserMapper sysuserMapper;//用于提前判断用户是否已经存在

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
    public Result getCode(@RequestBody String body) throws MessagingException {
        //String email = request.getUserName();//注意前端json的变量名
        //String password = request.getPassword();  避免命名混乱 尝试直接解析json 不利用DTO实体
        JSONObject jsonObject = JSONObject.parseObject(body);
        String email = jsonObject.getString("email");//和前端约定的json格式
        String password = jsonObject.getString("password");

        /*
        先判断一下用户是否存在  优化性能 减少email开销
        */
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", email);
        SysUser sysuser = sysuserMapper.selectOne(queryWrapper);
        if (sysuser != null) {
            return new Result(-100, "用户已存在", null);
        }


        // 加密密码   注意注册时候的加密方法要和登录时一样 (已经统一)
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        // 生成唯一令牌
        String token = UUID.randomUUID().toString();

        // 将令牌和加密后的密码存储到 Redis，并设置过期时间为5分钟   注意先用分割：的string实现 序列化对象为json的方法要参考login
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set("email:"+token, email + ":" + encodedPassword, 5, TimeUnit.MINUTES);
        //第一个参数email: 模拟的是文件夹结构
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

        //尝试采用美观HTML作为邮件内容
        //邮件客户端不支持 header样式，只支持 内联样式
        //Gmail不支持flex布局。邮件中的样式尽量写css2的不要用css3
        String style = "<style>\n" +
                "    body,html,div,ul,li,button,p,img,h1,h2,h3,h4,h5,h6 {\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "    }\n" +
                "\n" +
                "    body,html {\n" +
                "        background: #fff;\n" +
                "        line-height: 1.8;\n" +
                "    }\n" +
                "\n" +
                "    h1,h2,h3,h4,h5,h6 {\n" +
                "        line-height: 1.8;\n" +
                "    }\n" +
                "\n" +
                ".email_warp {\n" +
                "        height: 100vh;\n" +
                "        min-height: 500px;\n" +
                "        font-size: 14px;\n" +
                "        color: #212121;\n" +
                "        display: flex;\n" +
                "        /* align-items: center; */\n" +
                "        justify-content: center;\n" +
                "    }\n" +
                "\n" +
                ".logo {\n" +
                "        width: 100px;\n" +
                "        height: 100px;\n" +
                "         background-image: url('http://150.158.110.63:8080/logo/logo.svg'); \n" +
                "    }\n" +     //注意这里放项目logo的在线资源 旧版浏览器不支持显示
                "\n" +
                "    h1.email-title {\n" +
                "        font-size: 26px;\n" +
                "        font-weight: 500;\n" +
                "        margin-bottom: 15px;\n" +
                "        color: #252525;\n" +
                "    }\n" +
                "\n" +
                "    a.links_btn {\n" +
                "        border: 0;\n" +
                "        background: #4C84FF;\n" +
                "        color: #fff;\n" +
                "        width: 100%;\n" +
                "        height: 50px;\n" +
                "        line-height: 50px;\n" +
                "        font-size: 16px;\n" +
                "        margin: 40px auto;\n" +
                "        box-shadow: 0px 2px 4px 0px rgba(0, 0, 0, 0.15);\n" +
                "        border-radius: 4px;\n" +
                "        outline: none;\n" +
                "        cursor: pointer;\n" +
                "        transition: all 0.3s;\n" +
                "        text-align: center;\n" +
                "        display: block;\n" +
                "        text-decoration: none;\n" +
                "    }\n" +
                "\n" +
                ".warm_tips {\n" +
                "        color: #757575;\n" +
                "        background: #f7f7f7;\n" +
                "        padding: 20px;\n" +
                "    }\n" +
                "\n" +
                ".warm_tips .desc {\n" +
                "        margin-bottom: 20px;\n" +
                "    }\n" +
                "\n" +
                ".qr_warp {\n" +
                "        max-width: 140px;\n" +
                "        margin: 20px auto;\n" +
                "    }\n" +
                "\n" +
                ".qr_warp img {\n" +
                "        max-width: 100%;\n" +
                "        max-height: 100%;\n" +
                "    }\n" +
                "\n" +
                ".email-footer {\n" +
                "        margin-top: 2em;\n" +
                "    }\n" +
                "\n" +
                "#reset-password-email {\n" +
                "        max-width: 500px;\n" +
                "    }\n" +
                "#reset-password-email .accout_email {\n" +
                "        color: #4C84FF;\n" +
                "        display: block;\n" +
                "        margin-bottom: 20px;\n" +
                "    }\n" +
                "</style>";

        String HTMLBody = "<body>\n" +
                "  <section class=\"email_warp\">\n" +
                "    <div id=\"reset-password-email\">\n" +
                "      <div class=\"logo\">\n" +                 //注意这里放项目logo的在线资源
                "               \n" +
                "      </div>\n" +
                "\n" +
                "      <h1 class=\"email-title\">\n" +
                "        尊敬的<span>PawsHub用户</span> ：\n" +
                "      </h1>\n" +
                "      <p>您正在确认如下地址的邮箱账户：</p>\n" +
                "      <b class=\"accout_email\"> "+   email   +"</b>\n" +   //注意这里的自定义email
                "\n" +
                "      <p>确认注册账户，请点击下方按钮。</p>\n" +
                "\n" +
                "<a class=\"links_btn\" href=\"" +
                url +
                "\" target=\"_blank\" onclick=\"window.open(this.href); return false;\">确认 / Verify</a>\n"+
                                         //URL a标签有时候在客户端是不能跳转识别的，还需要加上 target 标签。
                "\n" +
                "      <div class=\"warm_tips\">\n" +
                "        <div class=\"desc\">\n" +
                "          为安全起见，以上按钮为一次性链接，仅在 5 分钟内有效，请您尽快完成操作。\n" +
                "        </div>\n" +
                "\n" +
                "      <p>请注意，如果这不是您本人的操作，请忽略并关闭此邮件。</p>\n" +
                "        <p>如有任何疑问或无法完成注册，请通过如下方式与我们联系：</p>\n" +
                "        <p>邮箱：support@XXX.cn</p>\n" +
                "\n" +
                "        <p>本邮件由系统自动发送，请勿回复。</p>\n" +
                "      </div>\n" +
                "\n" +
                "      <div class=\"email-footer\">\n" +
                "        <p>来自治愈之爪项目组</p>\n" +
                "        <p>Sincerely PawsHub</p>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </section>\n" +
                "</body>";

        //正式内容
        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>注册您的PawsHub账户</title>\n" +
                style+
                "</head>\n" +
                HTMLBody+
                "</html>";


        String temp = "<p>【注册】请点击以下链接完成注册：<a href=\"" + url + "\">" + url + "</a> 。 链接五分钟内有效，逾期作废。</p>" +
                "<p>------------------------------</p>" +
                "<p>本邮件自动发送，无需回复</p>" +
                "<p>paws-hub项目</p>";

        helper.setText(content, true);

        mailSender.send(message);

        return new Result(200,"发送成功！",null);
    }


    //当前问题：邮箱得到的链接要改成本地的localhost才能测试   bug是在提取username时好像为null 通过新建对象解决
    @GetMapping("/confirm")
    public Result confirm(@RequestParam("token") String token) {
        // 检查令牌是否存在并且未过期
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String value = ops.get("email:"+token);//注意模拟目录结构

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

            //默认头像问题  自带一个默认头像文件的URL 文件要提前传到文件夹
            newUser.setAvatar("http://150.158.110.63:8080/images/default_avatar.png");

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