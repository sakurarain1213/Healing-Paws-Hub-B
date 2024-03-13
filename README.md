# Healing-Paws-Hub-B

# 工程简介
赛博宠物医院学习平台后端
# 技术栈
springboot+mybatisplus  
mysql+redis+MongoDB  
...
# 项目结构
主体工程： com.example.hou

- config 配置类  
- controller 控制层  
- entity 实体类  
- mapper 映射层  
- result 模板返回工具类  
- service和impl 主要服务和实现层  
- util 其它工具类  



# 更新日志

+ 3.2 增加项目框架 pom.xml 数据库未配置 无法直接运行
  请增加resources下的yml配置、pom.xml配置、启动类HouApplication配置
+ 3.3 01 部署mysql+MongoDB+redis数据库 CRUD测试通过 可直接运行启动类
+ 3.6 01 实现简单接口
+ 3.8 支持邮箱验证码认证 case接口实现
+ 3.12 支持邮箱链接认证注册 redis限时缓存 发布到服务器
# 延伸阅读

部署：Tencent cloud
管理：宝塔面板
配置：  
Nginx 1.18.0  
MySQL 5.7.43  
PHP-7.4.33  
Redis 7.2.3  
Java项目一键部署 3.5  
phpMyAdmin 5.0  

模块：  
见pom配置文件  

本地管理：  
navicat  
Another Redis Desktop Manager  
MongoDB Compass

Xshell  
Xftp  

公网ip  http://150.158.110.63/
域名绑定 无

jdk目录
/usr/java/jdk1.8.0_371/bin/java

技术支持：copilot 某宝版本
