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
+ 3.14 服务器部署ES 8.9.0  部署kibana可视化 kibana访问地址：http://150.158.110.63:5601  需要写配置类才不影响启动
+ 3.19 ES8配置完成 用户权限管理完成 权限注解可使用  考虑添加springCloud alibaba全家桶 考虑jdk17 考虑docker/k8s 考虑rocketMQ/kafka
+ 3.22 接口Question Exam 基本实现  服务器全端口非中国大陆ip屏蔽  
+ 3.26 配置monstache同步mongodb到es   数据库被扫描 增强安全性
+ 3.29 Exam新接口和测试 case和disease接口实现 department和item接口 开发affair中 
+ 3.31 权限选择接口完成 权限命名统一 登录规范返回 部署版
+ 4.1 文件工具类删除方法 个人信息与头像方法 部署版
+ 4.2 affair permission的redis同步 
+ 4.3 user可更新  首次login检查
+ 4.6 favorite收藏功能 token刷新时效暂略 department item高性能多字段模糊查询发布
+ 4.9 redis每分钟自动清除缓存（包括token）的问题 考虑绑定ip为127.0.0.1本地访问和改密和换版本  部署flask微服务支持GPT调用 百度千帆appBuilder   item和depart支持按id查  item的usage存md的源码  
+ 4.10 affair接口优化 注册时设置默认头像
+ 4.11 department和item增加图片字段 标准化返回 模糊搜索问题优化（ES的中文分词器生效）
+ 4.12 GPT模型文本切片训练完成
+ 4.17 优化分页返回  增加rabbitMQ处理限时考试逻辑

开会内容：
department和item增加pic的Size
item需要根据List<Affair> getRecommendAffairs类比 添加一个推荐接口
文件系统 需要一张上传文件的记录 用id查所有文件的url 以便删除和复用
考虑已学习的记录表 类比收藏
添加已学习和学习次数的查询（等价 只是不能取消）  推荐算法可以安装收藏次数和学习次数来推荐
添加收藏次数的查询（等价）
question的list要求传disease的name而不是id

# 延伸阅读

部署：Tencent cloud
管理：宝塔面板
配置：
Java项目一键部署 3.5  
Nginx	1.20.2
PHP	    7.4.33

MySQL	5.7.44
phpMyAdmin 5.0  
MongoDB	4.0.10
Redis	7.0.11

elasticsearch	8.9.0
kibana	1.0

rabbitmq	3.12.4

Docker管理器	3.9.3
Python项目管理器	2.5.1


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
