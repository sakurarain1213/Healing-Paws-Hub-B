package com.example.hou.util;

//本地开发环境文件路径构造方法
public class GlobalConstant {
    public static String prefix = System.getProperty("user.dir") + System.getProperty("file.separator") +
            "media" + System.getProperty("file.separator");

//    http://150.158.110.63:8080/images/加密文件名.后缀名
    public static String urlAccessFilePrefix = "http://150.158.110.63:8080/images/";

    public static String defaultAvatarUrl = "http://150.158.110.63:8080/images/default_avatar.png";
}
