package com.example.hou.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import lombok.Data;

import java.sql.Blob;
import java.time.LocalDateTime;


/*

上传格式
json
{
    "username":"1",
    "password":"123"
}

 */



@Data//@Data 就是lombok 的注解 自动生成了set get

@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user_info")  //@TableName 对应数据库表名
public class UserInfo {

 //重要！！！实践证明 直接在mysql数据库增加列（允许null）
 // 不在entity和后端代码作修改 依然正常运行

   /*public UserInfo(Integer id, String username, String password){
        this.username = username;
        this.id = id;
        this.password = password;

    }*/
    @TableId(value = "user_id",type = IdType.AUTO)  //@TableId 说明这条数据自增长也是对应数据库自增长的
    private Integer id;
    @TableField("username")//这里和数据库表对应  不写注解的话 属性名就就要完全对应数据库列名
    private String username;
    @TableField("password")
    private String password;
    //private LocalDateTime createTime;


 //再加一些列
 /**
  * 电话号码
  */
 //@TableField(strategy = FieldStrategy.IGNORED)//即允许空
 private String phone;


 /**
  * 电子邮箱
  */
 private String email;

 /**
  * 性别:men women
  */
 private String gender;

 /**
  * 头像
  */
 private Blob avatar;//测试一下 不存路径（string） 直接存blob的格式   写一个update方法在service就行
}
