/*
 Navicat Premium Data Transfer

 Source Server         : test本地MySQL
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : sc

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 24/08/2023 16:12:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for lesson
-- ----------------------------
DROP TABLE IF EXISTS `lesson`;
CREATE TABLE `lesson`  (
  `lesson_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '课程表主键',
  `user_id` int(0) NOT NULL COMMENT '教师表关联主键',
  `username` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '教师名字',
  `course_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '课程名',
  `grade_class` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '年级班级',
  `start_time` datetime(0) DEFAULT NULL COMMENT '开始时间（不用时间戳因为有2038问题）',
  `end_time` datetime(0) DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`lesson_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `record_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '记录主键',
  `username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '实际操作的简化，跳过课程表，只让人和录音直接关联',
  `lesson_id` int(0) DEFAULT NULL COMMENT '课程主键',
  `txt_file` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci COMMENT '文本内容（不考虑存路径 直接存文件本身）',
  `mp3_file` blob COMMENT '语音内容',
  `start_time` datetime(0) DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '结束时间',
  `score` blob COMMENT '评分  因为要细化 但有类  就存成类 （字节数组） 前端拿到即可',
  `iswuru` int(0) DEFAULT NULL COMMENT '侮辱判断',
  `wuru` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '具体',
  `isguli` int(0) DEFAULT NULL COMMENT '鼓励判断',
  `guli` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '具体',
  `istiwen` int(0) DEFAULT NULL COMMENT '提问判断',
  `tiwen` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '具体',
  `ps` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 151 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES (1, '1', NULL, '测试', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `record` VALUES (2, '2', NULL, '测试加一', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `record` VALUES (13, '3', NULL, '测试嗷嗷', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `record` VALUES (20, '6', NULL, '中文测试english test', NULL, NULL, '2023-04-09 17:29:21', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `record` VALUES (21, '6', NULL, '中文测试english test', NULL, NULL, '2023-04-09 12:12:12', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `record` VALUES (22, '6', NULL, '长句测试，ai听课后，生成了16页的数据报告，所有分析指向课堂效率、教学方式、学习方法等课堂实质问题。\r\n\r\n上课的过程中，ai采集教师整堂课的言语和行为，全班学生的表现，以及讲台上所有板书和课件呈现的内容。\r\n\r\n“三个方面，九个维度”，分别从课堂效率、课堂公平、课堂民主三个层面，从有学、有效、有趣、分配、程序、互动、安全、自主、合作等九个维度，深度剖析课堂。\r\n\r\n总体分析\r\n\r\nai统计了这节用时43分的课，各项活动的时长分布。\r\n教师讲授：23分37秒\r\n师生互动：6分55秒\r\n个人任务：4分14秒\r\n小组活动：5分2秒\r\n总体而言，课堂上，讲解、演示等教师行为时长占比约为54%，发言、小组讨论、提出问题等学生行为的时长占比为46%。\r\n\r\nai判断，在练习型、讲授型、对话型、混合型等四种教学模式，这节数学课属于混合型。\r\n  \r\n词云图功能\r\n \r\n\r\n\r\n学生分析\r\n学生发言内容统计？？    摄像头与录音\r\n\r\n学生发言人次为58人次；发言总时长为6分33秒；单次发言平均时长为6秒；发言总字数为1289字。\r\n\r\n根据学生回答问题时的语言表示，将学生的发言分级，简单回复为r1，回忆性回复为r2，推理、解释性回复为r3。\r\n在这节课中，r2层级发言最多，占比超80%，r3层级发言约为13%，r1层级最少，仅有5%。说明学生的发言更多地指向对学习目标的知道、理解、应用。\r\n \r\n\r\n\r\n教师分析\r\n\r\n教师提问数量（课堂中带有疑问语气的句子或问题）94个；\r\n课堂话轮数（一般以“教师提问—学生回答——教师反馈”作为一个话轮）76轮；\r\n教师讲授语速平均每分钟237.6字……\r\n', NULL, NULL, '2023-04-09 17:42:38', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `record` VALUES (23, '6', NULL, '中文测试english test', NULL, '2023-04-06 23:59:58', '2023-04-10 23:59:59', NULL, 0, NULL, 0, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (24, '6', NULL, '中文测试english test', NULL, '2023-04-10 23:59:50', '2023-04-10 23:59:59', NULL, 0, NULL, 0, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (25, '6', NULL, '傻逼sdhabi', NULL, '2023-04-10 23:59:50', '2023-04-10 23:59:59', NULL, 1, NULL, 0, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (51, '6', NULL, '我想请你说说看。', NULL, '2023-04-11 10:09:40', NULL, NULL, 0, NULL, 0, NULL, 1, NULL, NULL);
INSERT INTO `record` VALUES (52, '6', NULL, '答得很好，再接再厉。', NULL, '2023-04-11 10:09:40', NULL, NULL, 0, NULL, 1, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (53, '6', NULL, '你这个蠢蛋，蠢蛋，蠢蛋。', NULL, '2023-04-11 10:09:40', NULL, NULL, 1, '蠢蛋', 0, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (54, '6', NULL, '真的傻逼。', NULL, '2023-04-11 10:09:40', NULL, NULL, 0, NULL, 0, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (55, '6', NULL, '安怿骢我想问一下，文中的黄老爷是不是傻瓜？', NULL, '2023-04-11 11:27:44', NULL, NULL, 0, NULL, 0, NULL, 1, NULL, NULL);
INSERT INTO `record` VALUES (56, '6', NULL, '安怿骢，我想问问你，你是傻瓜吗？', NULL, '2023-04-11 11:34:55', NULL, NULL, 1, '傻瓜', 0, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (99, 'Lierick', NULL, '同学们好，我们开始上课。', NULL, '2023-04-13 10:09:40', NULL, NULL, 0, NULL, 1, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (100, 'Lierick', NULL, '我想请你说说看昨天的学习内容。', NULL, '2023-04-13 10:09:43', NULL, NULL, 0, NULL, 0, NULL, 1, NULL, NULL);
INSERT INTO `record` VALUES (101, 'Lierick', NULL, '答得很好，再接再厉。', NULL, '2023-04-13 10:09:47', NULL, NULL, 0, NULL, 1, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (102, 'Lierick', NULL, '你这个蠢蛋。', NULL, '2023-04-13 10:09:50', NULL, NULL, 1, '蠢蛋', 0, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (103, 'Lierick', NULL, '安怿骢我想问一下，文中的黄老爷是不是傻瓜？', NULL, '2023-04-13 10:10:51', NULL, NULL, 0, NULL, 0, NULL, 1, NULL, NULL);
INSERT INTO `record` VALUES (104, 'Lierick', NULL, '安怿骢，我想问问你，你是傻瓜吗？', NULL, '2023-04-13 10:10:56', NULL, NULL, 1, '傻瓜', 0, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (105, 'Lierick', NULL, '同学们好，我们开始上课。', NULL, '2023-04-13 10:09:40', NULL, NULL, 0, NULL, 1, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (106, 'Lierick', NULL, '我想请你说说看昨天的学习内容。', NULL, '2023-04-13 10:09:43', NULL, NULL, 0, NULL, 0, NULL, 1, NULL, NULL);
INSERT INTO `record` VALUES (107, 'Lierick', NULL, '答得很好，再接再厉。', NULL, '2023-04-13 10:09:47', NULL, NULL, 0, NULL, 1, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (108, 'Lierick', NULL, '你这个蠢蛋。', NULL, '2023-04-13 10:09:50', NULL, NULL, 1, '蠢蛋', 0, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (109, 'Lierick', NULL, '安怿骢我想问一下，文中的黄老爷是不是傻瓜？', NULL, '2023-04-13 10:10:51', NULL, NULL, 0, NULL, 0, NULL, 1, NULL, NULL);
INSERT INTO `record` VALUES (110, 'Lierick', NULL, '安怿骢，我想问问你，你是傻瓜吗？', NULL, '2023-04-13 10:10:56', NULL, NULL, 1, '傻瓜', 0, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (111, 'Lierick', NULL, '同学们好，我们开始上课。', NULL, '2023-04-13 10:09:40', NULL, NULL, 0, NULL, 1, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (112, 'Lierick', NULL, '我想请你说说看昨天的学习内容。', NULL, '2023-04-13 10:09:43', NULL, NULL, 0, NULL, 0, NULL, 1, NULL, NULL);
INSERT INTO `record` VALUES (113, 'Lierick', NULL, '答得很好，再接再厉。', NULL, '2023-04-13 10:09:47', NULL, NULL, 0, NULL, 1, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (114, 'Lierick', NULL, '你这个蠢蛋。', NULL, '2023-04-13 10:09:50', NULL, NULL, 1, '蠢蛋', 0, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (115, 'Lierick', NULL, '安怿骢我想问一下，文中的黄老爷是不是傻瓜？', NULL, '2023-04-13 10:10:51', NULL, NULL, 0, NULL, 0, NULL, 1, NULL, NULL);
INSERT INTO `record` VALUES (116, 'Lierick', NULL, '安怿骢，我想问问你，你是傻瓜吗？', NULL, '2023-04-13 10:10:56', NULL, NULL, 1, '傻瓜', 0, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (117, 'Lierick', NULL, '同学们好，我们开始上课。', NULL, '2023-04-13 10:09:40', NULL, NULL, 0, NULL, 1, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (118, 'Lierick', NULL, '我想请你说说看昨天的学习内容。', NULL, '2023-04-13 10:09:43', NULL, NULL, 0, NULL, 0, NULL, 1, NULL, NULL);
INSERT INTO `record` VALUES (119, 'Lierick', NULL, '答得很好，再接再厉。', NULL, '2023-04-13 10:09:47', NULL, NULL, 0, NULL, 1, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (120, 'Lierick', NULL, '你这个蠢蛋。', NULL, '2023-04-13 10:09:50', NULL, NULL, 1, '蠢蛋', 0, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (121, 'Lierick', NULL, '安怿骢我想问一下，文中的黄老爷是不是傻瓜？', NULL, '2023-04-13 10:10:51', NULL, NULL, 0, NULL, 0, NULL, 1, NULL, NULL);
INSERT INTO `record` VALUES (122, 'Lierick', NULL, '安怿骢，我想问问你，你是傻瓜吗？', NULL, '2023-04-13 10:10:56', NULL, NULL, 1, '傻瓜', 0, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (123, 'Lierick', NULL, '同学们好，我们开始上课。', NULL, '2023-04-13 10:09:40', NULL, NULL, 0, NULL, 1, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (124, 'Lierick', NULL, '我想请你说说看昨天的学习内容。', NULL, '2023-04-13 10:09:43', NULL, NULL, 0, NULL, 0, NULL, 1, NULL, NULL);
INSERT INTO `record` VALUES (125, 'Lierick', NULL, '答得很好，再接再厉。', NULL, '2023-04-13 10:09:47', NULL, NULL, 0, NULL, 1, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (126, 'Lierick', NULL, '你这个蠢蛋。', NULL, '2023-04-13 10:09:50', NULL, NULL, 1, '蠢蛋', 0, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (127, 'Lierick', NULL, '安怿骢我想问一下，文中的黄老爷是不是傻瓜？', NULL, '2023-04-13 10:10:51', NULL, NULL, 0, NULL, 0, NULL, 1, NULL, NULL);
INSERT INTO `record` VALUES (128, 'Lierick', NULL, '安怿骢，我想问问你，你是傻瓜吗？', NULL, '2023-04-13 10:10:56', NULL, NULL, 1, '傻瓜', 0, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (129, 'iraina', NULL, '测试一句话，不行？', NULL, '2023-08-23 10:09:40', NULL, NULL, 0, NULL, 0, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (130, 'iraina', NULL, '测试一句话，需要结束标点。', NULL, '2023-08-23 10:09:41', NULL, NULL, 0, NULL, 0, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (131, 'iraina', NULL, 'test我想问一下，test是不是傻瓜？', NULL, '2023-08-23 10:10:51', NULL, NULL, 0, NULL, 0, NULL, 1, NULL, NULL);
INSERT INTO `record` VALUES (132, 'iraina', NULL, ' ，我想问问你，你是傻瓜吗？', NULL, '2023-08-23 10:10:56', NULL, NULL, 1, '傻瓜', 0, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (133, 'iraina', NULL, 'test我想问一下，test是不是傻瓜？', NULL, '2023-08-23 10:10:51', NULL, NULL, 0, NULL, 0, NULL, 1, NULL, NULL);
INSERT INTO `record` VALUES (134, 'iraina', NULL, ' ，我想问问你，你是傻瓜吗？', NULL, '2023-08-23 10:10:56', NULL, NULL, 1, '傻瓜', 0, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (149, '6', NULL, '你就是傻瓜，你是不是傻瓜，你是傻瓜傻子。', NULL, '2023-08-23 19:28:51', NULL, NULL, 1, '傻瓜', 0, NULL, 0, NULL, NULL);
INSERT INTO `record` VALUES (150, '6', NULL, ' ', NULL, '2023-08-23 19:28:51', '2023-08-23 21:44:45', NULL, 0, NULL, 0, NULL, 0, NULL, NULL);

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `user_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `gender` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '性别',
  `phone` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '电话',
  `email` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '邮箱',
  `avatar` blob COMMENT '头像',
  `test` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '测试列',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1244 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 'admin', '123456', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (2, 'a', '1', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (3, '4', '4', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (11, '1', '123', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (67, '2', '2', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (1225, '123456', '123456', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (1234, 'Lierick', 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (1237, '0', '0', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (1238, '5', '111111', 'men', '12', '123@qq.com', NULL, NULL);
INSERT INTO `user_info` VALUES (1239, '7', '1234', 'men', NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (1242, '3', '11111', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (1243, '6', '123', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for z_user
-- ----------------------------
DROP TABLE IF EXISTS `z_user`;
CREATE TABLE `z_user`  (
  `uid` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户名',
  `password` char(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码',
  `salt` char(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '盐值',
  `phone` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '电话号码',
  `email` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '电子邮箱',
  `gender` int(0) DEFAULT NULL COMMENT '性别:0-女，1-男',
  `avatar` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '头像',
  `created_user` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '日志-创建人',
  `created_time` datetime(0) DEFAULT NULL COMMENT '日志-创建时间',
  `modified_user` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '日志-最后修改执行人',
  `modified_time` datetime(0) DEFAULT NULL COMMENT '日志-最后修改时间',
  `is_delete` int(0) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of z_user
-- ----------------------------
INSERT INTO `z_user` VALUES (1, 'admin', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `z_user` VALUES (2, '2', '665F83C0F4659B39C1CABF9600461E81', '4D1B4F0F-05E8-425E-9ABA-DFF853FDAC6E', NULL, NULL, NULL, NULL, '2', '2023-04-06 13:30:53', '2', '2023-04-06 13:30:53', 0);

SET FOREIGN_KEY_CHECKS = 1;
