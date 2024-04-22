package com.example.hou;

import com.example.hou.entity.SysUser;
import com.example.hou.result.Result;
import com.example.hou.service.SecurityUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Date;

/**
 * @program: Healing-Paws-Hub-B
 * @description:
 * @author: 作者
 * @create: 2024-03-12 14:26
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegisterTest {

    @Autowired
    private SecurityUserService userService;

    @Test
   void testRegister() {
                  /*
            String value = "w1625154105@163.com:$2a$10$zpIj12VJUwVQAHAFJHjaaOwDl9ZkzYb21oA7a6s.Q6AN9PC7BX4Ka";

            // 分割电子邮件地址和密码
            String[] parts = value.split(":");
            String email = parts[0];
            String password = parts[1];//注意已经加密过

            System.out.println(email);
            System.out.println("-------------------------");
            System.out.println(password);

            // 创建新的用户对象
            SysUser newUser = new SysUser();
            newUser.setAccount(email);
            newUser.setPassword(password);

            // 设置其他字段
            newUser.setEnabled(true);
            newUser.setAccountNotExpired(true);
            newUser.setAccountNotLocked(true);
            newUser.setCredentialsNotExpired(true);
            newUser.setCreateTime(new Date());
            newUser.setUpdateTime(new Date());

           Result r=userService.createUser(newUser);
           System.out.println(r.getMsg());

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
