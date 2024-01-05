/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云DB
 Source Server Type    : MySQL
 Source Server Version : 50743
 Source Host           : 47.103.113.75:3306
 Source Schema         : sc

 Target Server Type    : MySQL
 Target Server Version : 50743
 File Encoding         : 65001

 Date: 05/01/2024 19:55:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `summary` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `txt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO `class` VALUES (1, '测试', '测试');
INSERT INTO `class` VALUES (2, '测试', '测试');
INSERT INTO `class` VALUES (3, '测试', '测试');
INSERT INTO `class` VALUES (4, '测试', '测试');
INSERT INTO `class` VALUES (5, '测试', '测试');
INSERT INTO `class` VALUES (6, '测试', '侧事故');
INSERT INTO `class` VALUES (7, '课堂主题：安卓开发\n\n关键词：安卓开发、学习\n\n概要：老师在课堂上讲解安卓开发，强调学生们要好好学习。随后询问学生们是否可以听到声音。', '今天我们来学习一下安卓开发，大家要好好学，好我讲完了。为你好，为你好，可以听得到声音吗？');
INSERT INTO `class` VALUES (8, '课堂主题：如何写好一篇新闻报道\n\n关键词：新闻报道、写作技巧、结构、语言\n\n概要：本节课讲解如何写好一篇新闻报道，包括写作技巧、文章结构和语言等方面的要点。通过课堂讨论，学生们能够掌握新闻报道的基本要素和写作规范，提高自己的新闻写作水平。', '');
INSERT INTO `class` VALUES (9, '课堂主题：如何有效地学习编程\n\n关键词：编程、学习、有效\n\n概要：在今天的课堂上，我们讨论了如何有效地学习编程。首先，我们需要理解编程的基本概念和原理，掌握常用的编程语言和工具。其次，我们应该注重实践和练习，通过编写实际的代码来加深理解和提高技能。此外，良好的学习习惯和持续的学习动力也是必不可少的。最后，我们要善于利用各种资源，如在线课程、教程和社区论坛，来不断学习和提高自己的编程能力。', '');
INSERT INTO `class` VALUES (10, '主题：化学的重要性\n\n关键词：化学、重要性\n\n概要：这节课的主题讲的是化学的重要性。老师强调化学在生活中的广泛应用，以及它对其他学科的影响。', '同学们好，我们这节课讲化学，化学非常重要。化学真的很重要。');
INSERT INTO `class` VALUES (11, '主题：物理\n\n关键词：物理知识\n\n概要：这节课将讲解物理知识，包括但不限于力学、热学、光学和电磁学等方面的内容。通过学习这些知识，我们可以更好地理解自然界的各种现象，并掌握解决实际问题的能力。', '朋友们好，这节课的主题是物理，我们将讲物理知识讲。');
INSERT INTO `class` VALUES (12, '课堂主题：如何提升学生的阅读理解能力\n\n关键词：阅读理解能力、阅读技巧、批判性思维、词汇量、阅读速度\n\n概要：\n\n老师在课堂上讲解如何提升学生的阅读理解能力。首先强调阅读理解能力的重要性，然后介绍了一些实用的阅读技巧，如预测文章内容、识别主题句等。同时，老师还提到要培养学生的批判性思维，学会从多个角度思考问题。此外，增加词汇量和提高阅读速度也是提升阅读理解能力的关键。最后，老师鼓励学生多进行课外阅读，通过不断练习来提高自己的阅读理解能力。', '');
INSERT INTO `class` VALUES (13, '本文主要讲了一堂关于创新的课堂，探讨了创新的概念、意义以及如何培养创新思维。老师强调了创新的重要性，并提出了通过多角度思考、尝试新方法等途径来培养创新思维。同时，还通过小组讨论的形式，让学生们分享了各自在创新方面的经验和见解。', '');
INSERT INTO `class` VALUES (14, '<概要> 课堂上，老师让学生讨论他们喜欢的科幻小说作家，以及他们对这些作家的认识和欣赏的原因。在讨论过程中，学生不仅分享了各自喜欢的作家和作品，还从不同的角度和层面对科幻小说的定义和特征进行了探讨。\n\n<主题> 科幻小说的定义和特征\n\n<关键词> 科幻小说、作家、作品、定义、特征', '');
INSERT INTO `class` VALUES (15, '课堂主题：如何有效地学习编程\n\n关键词：编程、学习、有效\n\n概要：课堂上讨论了如何有效地学习编程。老师提到了学习编程需要注重基础语法，不断练习和实践，同时还需要有正确的学习心态和坚持不懈的精神。老师还分享了几个有效的学习方法和资源，包括观看教程视频、参与开源项目和加入编程社区等。学生们听得很认真，积极提问，纷纷表示将努力提升自己的编程技能。', '');
INSERT INTO `class` VALUES (16, NULL, '');
INSERT INTO `class` VALUES (17, '课堂主题：提炼课堂主题\n\n关键词：提炼、课堂主题\n\n概要：在课堂场景中，报告人完成了报告，并强调了提炼课堂主题的重要性。提炼课堂主题可以帮助参与者更好地理解课程内容，提高学习效果。', '报告完毕。报告完毕。');
INSERT INTO `class` VALUES (18, '课堂主题：卷宝卷宝很厉害\n\n关键词：卷宝卷宝、很厉害\n\n概要：这节课将探讨卷宝卷宝的厉害之处。', '你好，这节课的主题是卷宝卷宝很厉害结束。');
INSERT INTO `class` VALUES (19, '课堂主题：物理的重要性\n\n关键词：物理、重要\n\n概要：老师在课堂中强调物理非常重要，并且没有提供更多的信息或例子来支持他的观点。', '物理物理很重要，物理非常重要，我说完了。');
INSERT INTO `class` VALUES (20, '课堂主题：三角函数\n\n关键词：函数、三角函数\n\n概要：这节课是数学课，主要讲解了三角函数的知识。老师提到了三角函数有三种类型，并要求学生们回去好好复习。', '这节课是数学课，这节课我们来讲三节。函数，三角函数有三种赛赛。 Agent, 大家回去好好复习节课结束。');
INSERT INTO `class` VALUES (21, '课堂主题：人工智能与人类的未来\n\n关键词：人工智能、人类、未来\n\n概要：在今天的课堂上，我们讨论了人工智能的发展及其对人类未来的影响。人工智能技术正在迅速发展，它已经深入到我们的日常生活中，如语音助手、自动驾驶汽车和智能家居设备等。然而，随着人工智能技术的进步，人们开始担忧它对人类就业、隐私和安全等方面的影响。课堂上，我们讨论了这些担忧，并探讨了人工智能和人类如何和谐共存的问题。', '');
INSERT INTO `class` VALUES (22, NULL, '');
INSERT INTO `class` VALUES (23, NULL, '');
INSERT INTO `class` VALUES (24, '课堂主题：比较《史记》和《汉书》中的人物形象。\n\n关键词：史记、汉书、人物形象、比较\n\n概要：在课堂中，老师引导学生比较了《史记》和《汉书》中的人物形象，探讨了两部史书中的人物刻画的异同点。通过比较，学生深入了解了两个史书中的人物形象特点，加深了对两部史书以及历史的了解。同时，老师还强调了比较阅读的方法和意义，鼓励学生运用这种方法去阅读其他史书。', '');
INSERT INTO `class` VALUES (25, '主题：安卓展示\n\n关键词：安卓、展示、项目细节\n\n概要：这节课是关于安卓展示的课程，要求每组同学依次上台展示他们所做的安全项目。重点在于展示项目的细节。', '嗯，好的，同学们，这节课我们来进行安卓展示。而且请每一组同学依次上台来展示你们所做的安全。有项目展示重点包括你们的项目细节。同学上台展示。');
INSERT INTO `class` VALUES (26, '主题：语音转文字  安卓项目 内容：安卓、展示、项目细节\n\n概要：这节课是关于安卓展示的课程，要求每组同学依次上台展示他们所做的安全项目。重点在于展示项目的细节', 'anything is ok');

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `record_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录主键',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '实际操作的简化，跳过课程表，只让人和录音直接关联',
  `txt` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '文本内容（不考虑存路径？）',
  `mp3_file` blob COMMENT '语音内容',
  `start_time` datetime(0) DEFAULT NULL COMMENT '开始时间',
  `ps` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `class_id` int(11) DEFAULT NULL COMMENT '课程id',
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 338 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES (1, '1', '测试', NULL, NULL, NULL, NULL);
INSERT INTO `record` VALUES (2, '2', '测试加一', NULL, NULL, NULL, NULL);
INSERT INTO `record` VALUES (13, '3', '测试嗷嗷', NULL, NULL, NULL, NULL);
INSERT INTO `record` VALUES (20, '6', '中文测试english test', NULL, NULL, NULL, NULL);
INSERT INTO `record` VALUES (21, '6', '中文测试english test', NULL, NULL, NULL, NULL);
INSERT INTO `record` VALUES (22, '6', '长句测试，ai听课后，生成了16页的数据报告，所有分析指向课堂效率、教学方式、学习方法等课堂实质问题。\r\n\r\n上课的过程中，ai采集教师整堂课的言语和行为，全班学生的表现，以及讲台上所有板书和课件呈现的内容。\r\n\r\n“三个方面，九个维度”，分别从课堂效率、课堂公平、课堂民主三个层面，从有学、有效、有趣、分配、程序、互动、安全、自主、合作等九个维度，深度剖析课堂。\r\n\r\n总体分析\r\n\r\nai统计了这节用时43分的课，各项活动的时长分布。\r\n教师讲授：23分37秒\r\n师生互动：6分55秒\r\n个人任务：4分14秒\r\n小组活动：5分2秒\r\n总体而言，课堂上，讲解、演示等教师行为时长占比约为54%，发言、小组讨论、提出问题等学生行为的时长占比为46%。\r\n\r\nai判断，在练习型、讲授型、对话型、混合型等四种教学模式，这节数学课属于混合型。\r\n  \r\n词云图功能\r\n \r\n\r\n\r\n学生分析\r\n学生发言内容统计？？    摄像头与录音\r\n\r\n学生发言人次为58人次；发言总时长为6分33秒；单次发言平均时长为6秒；发言总字数为1289字。\r\n\r\n根据学生回答问题时的语言表示，将学生的发言分级，简单回复为r1，回忆性回复为r2，推理、解释性回复为r3。\r\n在这节课中，r2层级发言最多，占比超80%，r3层级发言约为13%，r1层级最少，仅有5%。说明学生的发言更多地指向对学习目标的知道、理解、应用。\r\n \r\n\r\n\r\n教师分析\r\n\r\n教师提问数量（课堂中带有疑问语气的句子或问题）94个；\r\n课堂话轮数（一般以“教师提问—学生回答——教师反馈”作为一个话轮）76轮；\r\n教师讲授语速平均每分钟237.6字……\r\n', NULL, NULL, NULL, NULL);
INSERT INTO `record` VALUES (23, '6', '中文测试english test', NULL, '2023-04-06 23:59:58', NULL, NULL);
INSERT INTO `record` VALUES (24, '6', '中文测试english test', NULL, '2023-04-10 23:59:50', NULL, NULL);
INSERT INTO `record` VALUES (25, '6', '傻逼sdhabi', NULL, '2023-04-10 23:59:50', NULL, NULL);
INSERT INTO `record` VALUES (51, '6', '我想请你说说看。', NULL, '2023-04-11 10:09:40', NULL, NULL);
INSERT INTO `record` VALUES (52, '6', '答得很好，再接再厉。', NULL, '2023-04-11 10:09:40', NULL, NULL);
INSERT INTO `record` VALUES (53, '6', '你这个蠢蛋，蠢蛋，蠢蛋。', NULL, '2023-04-11 10:09:40', NULL, NULL);
INSERT INTO `record` VALUES (54, '6', '真的傻逼。', NULL, '2023-04-11 10:09:40', NULL, NULL);
INSERT INTO `record` VALUES (55, '6', '安怿骢我想问一下，文中的黄老爷是不是傻瓜？', NULL, '2023-04-11 11:27:44', NULL, NULL);
INSERT INTO `record` VALUES (56, '6', '安怿骢，我想问问你，你是傻瓜吗？', NULL, '2023-04-11 11:34:55', NULL, NULL);
INSERT INTO `record` VALUES (99, 'Lierick', '同学们好，我们开始上课。', NULL, '2023-04-13 10:09:40', NULL, NULL);
INSERT INTO `record` VALUES (100, 'Lierick', '我想请你说说看昨天的学习内容。', NULL, '2023-04-13 10:09:43', NULL, NULL);
INSERT INTO `record` VALUES (101, 'Lierick', '答得很好，再接再厉。', NULL, '2023-04-13 10:09:47', NULL, NULL);
INSERT INTO `record` VALUES (102, 'Lierick', '你这个蠢蛋。', NULL, '2023-04-13 10:09:50', NULL, NULL);
INSERT INTO `record` VALUES (103, 'Lierick', '安怿骢我想问一下，文中的黄老爷是不是傻瓜？', NULL, '2023-04-13 10:10:51', NULL, NULL);
INSERT INTO `record` VALUES (104, 'Lierick', '安怿骢，我想问问你，你是傻瓜吗？', NULL, '2023-04-13 10:10:56', NULL, NULL);
INSERT INTO `record` VALUES (105, 'Lierick', '同学们好，我们开始上课。', NULL, '2023-04-13 10:09:40', NULL, NULL);
INSERT INTO `record` VALUES (106, 'Lierick', '我想请你说说看昨天的学习内容。', NULL, '2023-04-13 10:09:43', NULL, NULL);
INSERT INTO `record` VALUES (107, 'Lierick', '答得很好，再接再厉。', NULL, '2023-04-13 10:09:47', NULL, NULL);
INSERT INTO `record` VALUES (108, 'Lierick', '你这个蠢蛋。', NULL, '2023-04-13 10:09:50', NULL, NULL);
INSERT INTO `record` VALUES (109, 'Lierick', '安怿骢我想问一下，文中的黄老爷是不是傻瓜？', NULL, '2023-04-13 10:10:51', NULL, NULL);
INSERT INTO `record` VALUES (110, 'Lierick', '安怿骢，我想问问你，你是傻瓜吗？', NULL, '2023-04-13 10:10:56', NULL, NULL);
INSERT INTO `record` VALUES (111, 'Lierick', '同学们好，我们开始上课。', NULL, '2023-04-13 10:09:40', NULL, NULL);
INSERT INTO `record` VALUES (112, 'Lierick', '我想请你说说看昨天的学习内容。', NULL, '2023-04-13 10:09:43', NULL, NULL);
INSERT INTO `record` VALUES (113, 'Lierick', '答得很好，再接再厉。', NULL, '2023-04-13 10:09:47', NULL, NULL);
INSERT INTO `record` VALUES (114, 'Lierick', '你这个蠢蛋。', NULL, '2023-04-13 10:09:50', NULL, NULL);
INSERT INTO `record` VALUES (115, 'Lierick', '安怿骢我想问一下，文中的黄老爷是不是傻瓜？', NULL, '2023-04-13 10:10:51', NULL, NULL);
INSERT INTO `record` VALUES (116, 'Lierick', '安怿骢，我想问问你，你是傻瓜吗？', NULL, '2023-04-13 10:10:56', NULL, NULL);
INSERT INTO `record` VALUES (117, 'Lierick', '同学们好，我们开始上课。', NULL, '2023-04-13 10:09:40', NULL, NULL);
INSERT INTO `record` VALUES (118, 'Lierick', '我想请你说说看昨天的学习内容。', NULL, '2023-04-13 10:09:43', NULL, NULL);
INSERT INTO `record` VALUES (119, 'Lierick', '答得很好，再接再厉。', NULL, '2023-04-13 10:09:47', NULL, NULL);
INSERT INTO `record` VALUES (120, 'Lierick', '你这个蠢蛋。', NULL, '2023-04-13 10:09:50', NULL, NULL);
INSERT INTO `record` VALUES (121, 'Lierick', '安怿骢我想问一下，文中的黄老爷是不是傻瓜？', NULL, '2023-04-13 10:10:51', NULL, NULL);
INSERT INTO `record` VALUES (122, 'Lierick', '安怿骢，我想问问你，你是傻瓜吗？', NULL, '2023-04-13 10:10:56', NULL, NULL);
INSERT INTO `record` VALUES (123, 'Lierick', '同学们好，我们开始上课。', NULL, '2023-04-13 10:09:40', NULL, NULL);
INSERT INTO `record` VALUES (124, 'Lierick', '我想请你说说看昨天的学习内容。', NULL, '2023-04-13 10:09:43', NULL, NULL);
INSERT INTO `record` VALUES (125, 'Lierick', '答得很好，再接再厉。', NULL, '2023-04-13 10:09:47', NULL, NULL);
INSERT INTO `record` VALUES (126, 'Lierick', '你这个蠢蛋。', NULL, '2023-04-13 10:09:50', NULL, NULL);
INSERT INTO `record` VALUES (127, 'Lierick', '安怿骢我想问一下，文中的黄老爷是不是傻瓜？', NULL, '2023-04-13 10:10:51', NULL, NULL);
INSERT INTO `record` VALUES (128, 'Lierick', '安怿骢，我想问问你，你是傻瓜吗？', NULL, '2023-04-13 10:10:56', NULL, NULL);
INSERT INTO `record` VALUES (129, 'iraina', '测试一句话，不行？', NULL, '2023-08-23 10:09:40', NULL, NULL);
INSERT INTO `record` VALUES (130, 'iraina', '测试一句话，需要结束标点。', NULL, '2023-08-23 10:09:41', NULL, NULL);
INSERT INTO `record` VALUES (131, 'iraina', 'test我想问一下，test是不是傻瓜？', NULL, '2023-08-23 10:10:51', NULL, NULL);
INSERT INTO `record` VALUES (132, 'iraina', ' ，我想问问你，你是傻瓜吗？', NULL, '2023-08-23 10:10:56', NULL, NULL);
INSERT INTO `record` VALUES (133, 'iraina', 'test我想问一下，test是不是傻瓜？', NULL, '2023-08-23 10:10:51', NULL, NULL);
INSERT INTO `record` VALUES (134, 'iraina', ' ，我想问问你，你是傻瓜吗？', NULL, '2023-08-23 10:10:56', NULL, NULL);
INSERT INTO `record` VALUES (149, '6', '你就是傻瓜，你是不是傻瓜，你是傻瓜傻子。', NULL, '2023-08-23 19:28:51', NULL, NULL);
INSERT INTO `record` VALUES (150, '王', NULL, NULL, '2023-08-24 02:08:00', '<start>', 6);
INSERT INTO `record` VALUES (151, 'testAAA', '你好，我是安怿骢，你好，再见。', NULL, '2023-09-02 00:12:20', NULL, NULL);
INSERT INTO `record` VALUES (152, 'testAAA', '你好，我是安怿骢，你好，再见。', NULL, '2023-09-02 00:20:26', NULL, NULL);
INSERT INTO `record` VALUES (153, 'testAAA', '你好，我是安怿骢，你好，再见。', NULL, '2023-09-02 08:23:13', NULL, NULL);
INSERT INTO `record` VALUES (154, 'testAAA', '你好，我是安怿骢，你好，再见。', NULL, '2023-09-02 16:25:14', NULL, NULL);
INSERT INTO `record` VALUES (157, '王', '8', NULL, '2024-01-05 00:43:23', NULL, 6);
INSERT INTO `record` VALUES (158, '王', '喂，你好你好喂，拜拜。', NULL, '2024-01-05 00:48:43', NULL, 6);
INSERT INTO `record` VALUES (159, '王', '喂，你好你好喂，拜拜。', NULL, '2024-01-05 00:53:14', NULL, 6);
INSERT INTO `record` VALUES (160, '王', '喂，你好你好喂，拜拜。', NULL, '2024-01-05 01:01:08', NULL, 6);
INSERT INTO `record` VALUES (161, '王', '喂，你好你好喂，拜拜。', NULL, '2024-01-05 01:11:52', NULL, 6);
INSERT INTO `record` VALUES (162, '王', '喂，你好你好喂，拜拜。', NULL, '2024-01-05 01:29:08', NULL, 6);
INSERT INTO `record` VALUES (164, '王', NULL, NULL, '2024-01-05 02:08:14', '<end>', 6);
INSERT INTO `record` VALUES (165, '王', '为你好，为你好，可以听得到声音吗？', NULL, '2024-01-05 02:36:02', NULL, NULL);
INSERT INTO `record` VALUES (166, '王', '为你好，为你好，可以听得到声音吗？', NULL, '2024-01-05 03:33:06', NULL, NULL);
INSERT INTO `record` VALUES (167, '王', NULL, NULL, '2024-01-05 03:45:52', '<start>', NULL);
INSERT INTO `record` VALUES (168, '王', NULL, NULL, '2024-01-05 03:46:02', '<end>', NULL);
INSERT INTO `record` VALUES (169, '王', NULL, NULL, '2024-01-05 04:45:32', '<start>', NULL);
INSERT INTO `record` VALUES (170, '王', NULL, NULL, '2024-01-05 04:46:01', '<start>', NULL);
INSERT INTO `record` VALUES (171, '王', NULL, NULL, '2024-01-05 04:47:15', '<start>', 1);
INSERT INTO `record` VALUES (172, '王', NULL, NULL, '2024-01-05 05:06:21', '<start>', 2);
INSERT INTO `record` VALUES (173, '王', NULL, NULL, '2024-01-05 05:13:07', '<start>', 3);
INSERT INTO `record` VALUES (174, '王', NULL, NULL, '2024-01-05 05:13:10', '<end>', 3);
INSERT INTO `record` VALUES (175, '王', ' Hug.', NULL, '2024-01-05 05:13:15', NULL, 4);
INSERT INTO `record` VALUES (176, '王', NULL, NULL, '2024-01-05 05:14:59', '<start>', 4);
INSERT INTO `record` VALUES (177, '王', NULL, NULL, '2024-01-05 05:15:07', '<end>', 4);
INSERT INTO `record` VALUES (178, '王', '喂喂喂，你好你好，喂喂，你好你好，拜拜拜拜。', NULL, '2024-01-05 05:15:16', NULL, 5);
INSERT INTO `record` VALUES (179, '王', NULL, NULL, '2024-01-05 05:23:40', '<start>', 5);
INSERT INTO `record` VALUES (180, '王', NULL, NULL, '2024-01-05 05:23:56', '<end>', 5);
INSERT INTO `record` VALUES (181, '王', '', NULL, '2024-01-05 05:24:07', NULL, 6);
INSERT INTO `record` VALUES (182, '王', NULL, NULL, '2024-01-05 05:32:40', '<start>', 6);
INSERT INTO `record` VALUES (183, '王', NULL, NULL, '2024-01-05 05:33:03', '<end>', 6);
INSERT INTO `record` VALUES (184, '王', '今天我们来学习一下安卓开发，大家要好好学，好我讲完了。', NULL, '2024-01-05 05:33:05', NULL, 7);
INSERT INTO `record` VALUES (185, '王', NULL, NULL, '2024-01-05 05:35:30', '<start>', 7);
INSERT INTO `record` VALUES (186, '王', NULL, NULL, '2024-01-05 05:35:38', '<end>', 7);
INSERT INTO `record` VALUES (187, '王', '8888888', NULL, '2024-01-05 05:35:40', NULL, NULL);
INSERT INTO `record` VALUES (188, '王', NULL, NULL, '2024-01-05 06:07:47', '<start>', NULL);
INSERT INTO `record` VALUES (189, '王', NULL, NULL, '2024-01-05 06:07:48', '<end>', NULL);
INSERT INTO `record` VALUES (190, '王', NULL, NULL, '2024-01-05 06:09:19', '<start>', NULL);
INSERT INTO `record` VALUES (191, '王', NULL, NULL, '2024-01-05 06:09:20', '<end>', NULL);
INSERT INTO `record` VALUES (192, '王', NULL, NULL, '2024-01-05 06:15:00', '<start>', NULL);
INSERT INTO `record` VALUES (193, '王', NULL, NULL, '2024-01-05 06:15:02', '<end>', NULL);
INSERT INTO `record` VALUES (194, '王', NULL, NULL, '2024-01-05 06:17:04', '<start>', NULL);
INSERT INTO `record` VALUES (195, '王', NULL, NULL, '2024-01-05 06:17:05', '<end>', NULL);
INSERT INTO `record` VALUES (196, '王', NULL, NULL, '2024-01-05 06:17:11', '<start>', NULL);
INSERT INTO `record` VALUES (197, '王', NULL, NULL, '2024-01-05 06:17:12', '<end>', NULL);
INSERT INTO `record` VALUES (198, '王', NULL, NULL, '2024-01-05 06:39:48', '<start>', NULL);
INSERT INTO `record` VALUES (199, '王', NULL, NULL, '2024-01-05 06:39:49', '<end>', NULL);
INSERT INTO `record` VALUES (200, '王', NULL, NULL, '2024-01-05 06:41:10', '<start>', NULL);
INSERT INTO `record` VALUES (201, '王', NULL, NULL, '2024-01-05 06:41:11', '<end>', NULL);
INSERT INTO `record` VALUES (202, '王', NULL, NULL, '2024-01-05 06:56:57', '<start>', NULL);
INSERT INTO `record` VALUES (203, '王', NULL, NULL, '2024-01-05 06:57:09', '<end>', NULL);
INSERT INTO `record` VALUES (204, '王', '同学们你们好，我今天想讲一下化学，化学非常重要啊，我讲完了再见。', NULL, '2024-01-05 06:57:19', NULL, NULL);
INSERT INTO `record` VALUES (205, '王', NULL, NULL, '2024-01-05 06:58:31', '<start>', NULL);
INSERT INTO `record` VALUES (206, '王', NULL, NULL, '2024-01-05 06:58:42', '<start>', 7);
INSERT INTO `record` VALUES (207, '王', NULL, NULL, '2024-01-05 06:58:44', '<end>', NULL);
INSERT INTO `record` VALUES (208, '王', '同学们好，我今天想讲一下关于化学化学非常的重要，是我们生活当中非常重要的东西，我讲完了。', NULL, '2024-01-05 06:58:56', NULL, NULL);
INSERT INTO `record` VALUES (209, '王', NULL, NULL, '2024-01-05 07:00:24', '<start>', NULL);
INSERT INTO `record` VALUES (210, '王', NULL, NULL, '2024-01-05 07:00:32', '<end>', NULL);
INSERT INTO `record` VALUES (211, '王', '同志们好，我今天要讲一下化学，化学很重要，我讲完了拜拜。', NULL, '2024-01-05 07:00:40', NULL, NULL);
INSERT INTO `record` VALUES (212, '王', '为你好，为你好，可以听得到声音吗？', NULL, '2024-01-05 07:01:02', NULL, 7);
INSERT INTO `record` VALUES (213, '王', NULL, NULL, '2024-01-05 07:02:04', '<start>', NULL);
INSERT INTO `record` VALUES (214, '王', NULL, NULL, '2024-01-05 07:02:12', '<end>', NULL);
INSERT INTO `record` VALUES (215, '王', '喂喂喂喂喂喂，你好。', NULL, '2024-01-05 07:02:19', NULL, NULL);
INSERT INTO `record` VALUES (216, '王', NULL, NULL, '2024-01-05 07:02:31', '<end>', 7);
INSERT INTO `record` VALUES (217, '王', NULL, NULL, '2024-01-05 07:03:31', '<start>', NULL);
INSERT INTO `record` VALUES (218, '王', NULL, NULL, '2024-01-05 07:03:39', '<end>', NULL);
INSERT INTO `record` VALUES (219, '王', '今天我们要讲化学，化学很重要，化学非常重要的，化学比物理重要。', NULL, '2024-01-05 07:03:44', NULL, NULL);
INSERT INTO `record` VALUES (220, '王', NULL, NULL, '2024-01-05 07:05:20', '<start>', NULL);
INSERT INTO `record` VALUES (221, '王', NULL, NULL, '2024-01-05 07:05:48', '<end>', NULL);
INSERT INTO `record` VALUES (222, '王', '同学们好，我今天想讲一下化学，化学是非常重要的，化学里面最重要的就是化学方程式，无反应，好，我讲完了，今天的课到此为止，谢谢大家。', NULL, '2024-01-05 07:05:54', NULL, NULL);
INSERT INTO `record` VALUES (223, '王', NULL, NULL, '2024-01-05 07:09:33', '<start>', NULL);
INSERT INTO `record` VALUES (224, '王', '今天讲化学我讲完了。', NULL, '2024-01-05 07:09:42', NULL, NULL);
INSERT INTO `record` VALUES (225, '王', NULL, NULL, '2024-01-05 07:09:45', '<end>', NULL);
INSERT INTO `record` VALUES (226, '王', NULL, NULL, '2024-01-05 07:13:05', '<end>', 7);
INSERT INTO `record` VALUES (227, '王', NULL, NULL, '2024-01-05 07:32:02', '<start>', 8);
INSERT INTO `record` VALUES (228, '王', NULL, NULL, '2024-01-05 07:32:09', '<end>', 8);
INSERT INTO `record` VALUES (229, '王', NULL, NULL, '2024-01-05 07:34:39', '<start>', 9);
INSERT INTO `record` VALUES (230, '王', NULL, NULL, '2024-01-05 07:35:08', '<end>', 9);
INSERT INTO `record` VALUES (231, '王', '化学化学非常重要，我们今天来讲化学反应，化学反应是化学当中非常重要的一部分，然后化学反应当中最重要的就是化学反应中的氧化反应，然后氧化反应，我现在就讲一下氧化反应，重点就是有氧气啊，这节课完毕了，谢谢大家，拜拜。', NULL, '2024-01-05 07:35:28', NULL, 9);
INSERT INTO `record` VALUES (232, '王', NULL, NULL, '2024-01-05 07:57:31', '<start>', 10);
INSERT INTO `record` VALUES (233, '王', '同学们好，我们这节课讲化学，化学非常重要。', NULL, '2024-01-05 07:57:45', NULL, 10);
INSERT INTO `record` VALUES (234, '王', '化学真的很重要。', NULL, '2024-01-05 07:57:47', NULL, 10);
INSERT INTO `record` VALUES (235, '王', NULL, NULL, '2024-01-05 07:57:48', '<end>', 10);
INSERT INTO `record` VALUES (236, '王', NULL, NULL, '2024-01-05 08:16:03', '<start>', 11);
INSERT INTO `record` VALUES (237, '王', '朋友们好，这节课的主题是物理，我们将讲物理知识讲。', NULL, '2024-01-05 08:16:19', NULL, 11);
INSERT INTO `record` VALUES (238, '王', NULL, NULL, '2024-01-05 08:16:20', '<end>', 11);
INSERT INTO `record` VALUES (239, '王', '动力啊，今天重点中立中立有中立是难。', NULL, '2024-01-05 08:16:20', NULL, 11);
INSERT INTO `record` VALUES (240, '王', NULL, NULL, '2024-01-05 08:18:31', '<start>', 12);
INSERT INTO `record` VALUES (241, '王', NULL, NULL, '2024-01-05 08:18:31', '<end>', 12);
INSERT INTO `record` VALUES (242, '王', NULL, NULL, '2024-01-05 08:33:45', '<start>', 13);
INSERT INTO `record` VALUES (243, '王', NULL, NULL, '2024-01-05 08:33:46', '<end>', 13);
INSERT INTO `record` VALUES (244, '王', NULL, NULL, '2024-01-05 08:37:00', '<start>', 14);
INSERT INTO `record` VALUES (245, '王', NULL, NULL, '2024-01-05 08:37:01', '<end>', 14);
INSERT INTO `record` VALUES (246, '王', NULL, NULL, '2024-01-05 08:37:30', '<start>', 15);
INSERT INTO `record` VALUES (247, '王', NULL, NULL, '2024-01-05 08:37:30', '<end>', 15);
INSERT INTO `record` VALUES (248, '王', NULL, NULL, '2024-01-05 08:37:45', '<start>', 16);
INSERT INTO `record` VALUES (249, '王', NULL, NULL, '2024-01-05 08:37:45', '<end>', 16);
INSERT INTO `record` VALUES (250, '王', NULL, NULL, '2024-01-05 08:55:09', '<start>', 17);
INSERT INTO `record` VALUES (251, '王', '报告完毕。', NULL, '2024-01-05 08:55:20', NULL, 17);
INSERT INTO `record` VALUES (252, '王', '报告完毕。', NULL, '2024-01-05 08:55:20', NULL, 17);
INSERT INTO `record` VALUES (253, '王', NULL, NULL, '2024-01-05 08:55:26', '<end>', 17);
INSERT INTO `record` VALUES (254, '王', NULL, NULL, '2024-01-05 09:07:33', '<start>', 18);
INSERT INTO `record` VALUES (255, '王', '你好，这节课的主题是卷宝卷宝很厉害结束。', NULL, '2024-01-05 09:07:50', NULL, 18);
INSERT INTO `record` VALUES (256, '王', NULL, NULL, '2024-01-05 09:07:52', '<end>', 18);
INSERT INTO `record` VALUES (257, '王', NULL, NULL, '2024-01-05 09:17:56', '<start>', 19);
INSERT INTO `record` VALUES (258, '王', '物理物理很重要，物理非常重要，我说完了。', NULL, '2024-01-05 09:18:13', NULL, 19);
INSERT INTO `record` VALUES (259, '王', NULL, NULL, '2024-01-05 09:18:15', '<end>', 19);
INSERT INTO `record` VALUES (260, '王', NULL, NULL, '2024-01-05 09:26:17', '<start>', 20);
INSERT INTO `record` VALUES (261, '王', '这节课是数学课，这节课我们来讲三节。', NULL, '2024-01-05 09:26:35', NULL, 20);
INSERT INTO `record` VALUES (262, '王', '函数，三角函数有三种赛赛。', NULL, '2024-01-05 09:26:41', NULL, 20);
INSERT INTO `record` VALUES (263, '王', ' Agent, 大家回去好好复习节课结束。', NULL, '2024-01-05 09:26:43', NULL, 20);
INSERT INTO `record` VALUES (264, '王', NULL, NULL, '2024-01-05 09:26:50', '<end>', 20);
INSERT INTO `record` VALUES (265, '王', NULL, NULL, '2024-01-05 09:41:10', '<start>', 21);
INSERT INTO `record` VALUES (266, '王', NULL, NULL, '2024-01-05 09:41:43', '<end>', 21);
INSERT INTO `record` VALUES (267, '王', '奥运。', NULL, '2024-01-05 09:41:45', NULL, 21);
INSERT INTO `record` VALUES (268, '王', NULL, NULL, '2024-01-05 09:45:23', '<start>', 22);
INSERT INTO `record` VALUES (269, '王', NULL, NULL, '2024-01-05 09:45:30', '<end>', 22);
INSERT INTO `record` VALUES (270, '王', '', NULL, '2024-01-05 09:45:32', NULL, 22);
INSERT INTO `record` VALUES (271, '王', NULL, NULL, '2024-01-05 09:45:56', '<start>', 23);
INSERT INTO `record` VALUES (272, '王', NULL, NULL, '2024-01-05 09:46:08', '<end>', 23);
INSERT INTO `record` VALUES (273, '王', '没有通知，什么情况？', NULL, '2024-01-05 09:46:08', NULL, 23);
INSERT INTO `record` VALUES (274, '王', '测试一下，测试一下，看看这边有没有通知。', NULL, '2024-01-05 09:46:10', NULL, 23);
INSERT INTO `record` VALUES (275, '王', NULL, NULL, '2024-01-05 10:04:15', '<start>', 24);
INSERT INTO `record` VALUES (276, '王', '', NULL, '2024-01-05 10:04:31', NULL, 24);
INSERT INTO `record` VALUES (277, '王', '', NULL, '2024-01-05 10:04:36', NULL, 24);
INSERT INTO `record` VALUES (278, '王', '', NULL, '2024-01-05 10:04:44', NULL, 24);
INSERT INTO `record` VALUES (279, '王', NULL, NULL, '2024-01-05 10:04:48', '<end>', 24);
INSERT INTO `record` VALUES (280, '王', '', NULL, '2024-01-05 10:04:49', NULL, 24);
INSERT INTO `record` VALUES (281, '王', '', NULL, '2024-01-05 10:04:50', NULL, 24);
INSERT INTO `record` VALUES (282, '王', NULL, NULL, '2024-01-05 10:09:25', '<start>', 25);
INSERT INTO `record` VALUES (283, '王', '嗯，好的，同学们，这节课我们来进行安卓展示。', NULL, '2024-01-05 10:09:42', NULL, 25);
INSERT INTO `record` VALUES (284, '王', '而且请每一组同学依次上台来展示你们所做的安全。', NULL, '2024-01-05 10:09:48', NULL, 25);
INSERT INTO `record` VALUES (285, '王', '有项目展示重点包括你们的项目细节。', NULL, '2024-01-05 10:09:55', NULL, 25);
INSERT INTO `record` VALUES (286, '王', '同学上台展示。', NULL, '2024-01-05 10:09:57', NULL, 25);
INSERT INTO `record` VALUES (287, '王', NULL, NULL, '2024-01-05 10:09:59', '<end>', 25);
INSERT INTO `record` VALUES (288, '王', '我们的人员分工，你们的技术栈以及你们项目的创新点好，接下来请第1组。', NULL, '2024-01-05 10:10:01', NULL, 25);
INSERT INTO `record` VALUES (289, '王', NULL, NULL, '2024-01-05 10:41:09', '<start>', 26);
INSERT INTO `record` VALUES (290, '王', '等一下我好像爱为什么要开权限哦，开了。', NULL, '2024-01-05 10:41:55', NULL, 26);
INSERT INTO `record` VALUES (291, '王', '那我们就这边一直挂着好了，那我就接着讲习题啊，对就是我。', NULL, '2024-01-05 10:42:00', NULL, 26);
INSERT INTO `record` VALUES (292, '王', '我们还有一个通知骑士，就因为我们这个录音模块用的是一个。', NULL, '2024-01-05 10:42:07', NULL, 26);
INSERT INTO `record` VALUES (293, '王', '啊，是一个fore ground的一个service，这是一个前台是一个前台服务员。', NULL, '2024-01-05 10:42:18', NULL, 26);
INSERT INTO `record` VALUES (294, '王', '然后它会有一个不可关闭的同志，然后他然后他可以告诉你就是。', NULL, '2024-01-05 10:42:20', NULL, 26);
INSERT INTO `record` VALUES (295, '王', '这个应用他正在那个正在录制你的声音，然后再就是。', NULL, '2024-01-05 10:42:27', NULL, 26);
INSERT INTO `record` VALUES (296, '王', '以免就是这个，这个可以避免一些隐私问题，那我就先讲一些PPT吧。', NULL, '2024-01-05 10:42:32', NULL, 26);
INSERT INTO `record` VALUES (297, '王', '然后这边是我们的一些界面展示，就因为现在他这边正在录着，所以说。', NULL, '2024-01-05 10:42:41', NULL, 26);
INSERT INTO `record` VALUES (298, '王', '说我就不能展示其他页面。', NULL, '2024-01-05 10:42:43', NULL, 26);
INSERT INTO `record` VALUES (299, '王', '我们前面已经随便随便说的一些东西，然后就可以看到。', NULL, '2024-01-05 10:42:54', NULL, 26);
INSERT INTO `record` VALUES (300, '王', '他既识别出了这个原来的就是原文，然后既然她也对他进行。', NULL, '2024-01-05 10:43:00', NULL, 26);
INSERT INTO `record` VALUES (301, '王', '写了一个总结，就包括那个主题关键词，然后概要的。', NULL, '2024-01-05 10:43:06', NULL, 26);
INSERT INTO `record` VALUES (302, '王', '然后这边就是我们的工作环境。', NULL, '2024-01-05 10:43:10', NULL, 26);
INSERT INTO `record` VALUES (303, '王', '改进就先就先不讲了，然后既然可以先让后端的。', NULL, '2024-01-05 10:43:17', NULL, 26);
INSERT INTO `record` VALUES (304, '王', '先后的行啊，我的工作工作量主要在后端和别的项目。', NULL, '2024-01-05 10:43:23', NULL, 26);
INSERT INTO `record` VALUES (305, '王', '不太一样，我是直接连到了spring，还有他的my SQL数据库的，所以后台数据库的。', NULL, '2024-01-05 10:43:31', NULL, 26);
INSERT INTO `record` VALUES (306, '王', '操作其实也挺复杂的啊，最关键的一点就是我们的核心功能录音录音在后面。', NULL, '2024-01-05 10:43:36', NULL, 26);
INSERT INTO `record` VALUES (307, '王', '才能实现，其实是从前端分片啊，比如说5兆5MB或者是MB。', NULL, '2024-01-05 10:43:42', NULL, 26);
INSERT INTO `record` VALUES (308, '王', '每一个音频文件传到后端，十几KB现在只有十几KB了这个事。', NULL, '2024-01-05 10:43:49', NULL, 26);
INSERT INTO `record` VALUES (309, '王', '可以调整的，我要讲一下前面那个事情，先讲先讲前面。', NULL, '2024-01-05 10:43:56', NULL, 26);
INSERT INTO `record` VALUES (310, '王', '手机上录音的时候，他是通过一个service启动一个录音的县城，然后他在录音，然后在这个录音的现场。', NULL, '2024-01-05 10:44:02', NULL, 26);
INSERT INTO `record` VALUES (311, '王', '他一边录音的同时，它会不断的把数据写入，然后并且每隔一段时间比如说当录音文件的。', NULL, '2024-01-05 10:44:09', NULL, 26);
INSERT INTO `record` VALUES (312, '王', '到一个我们自己设定一个大小就几十KB或者100多KB，他就会将这个录音录音片先发送到。', NULL, '2024-01-05 10:44:14', NULL, 26);
INSERT INTO `record` VALUES (313, '王', '后端然后发送到后端以后这样的话就是不然的话如果最后一次性发送的话，那么。', NULL, '2024-01-05 10:44:22', NULL, 26);
INSERT INTO `record` VALUES (314, '王', '他那个录音就会文件太大，然后他就传很久吗，所以就是边录边传这样话可以减少他那个解析式。', NULL, '2024-01-05 10:44:34', NULL, 26);
INSERT INTO `record` VALUES (315, '王', '然后传到后端以后，再再把这个录音片转成文字后端，然后再把这个文字在。', NULL, '2024-01-05 10:44:35', NULL, 26);
INSERT INTO `record` VALUES (316, '王', '概括，然后再返回这样子。', NULL, '2024-01-05 10:44:37', NULL, 26);
INSERT INTO `record` VALUES (317, '王', '核心的两个功能，一个就是把传过来的文件转文字，这个是用科大讯飞的API实现的。', NULL, '2024-01-05 10:44:47', NULL, 26);
INSERT INTO `record` VALUES (318, '王', '那个就是也是很核心的功能，就是用语言模型，我用的是文星。', NULL, '2024-01-05 10:44:53', NULL, 26);
INSERT INTO `record` VALUES (319, '王', ' API把转过来的文字在概括生成出一个包含有主。', NULL, '2024-01-05 10:44:59', NULL, 26);
INSERT INTO `record` VALUES (320, '王', '主题关键词和主要内容的一个文本再发回给前端，让前端展示就做一个概括。', NULL, '2024-01-05 10:45:06', NULL, 26);
INSERT INTO `record` VALUES (321, '王', '而这里比较关键的是，对文欣妍提示词的头位就是你，不同不同的。', NULL, '2024-01-05 10:45:13', NULL, 26);
INSERT INTO `record` VALUES (322, '王', '命令方式加上同样的一个，呃，一些说话的文本他返回。', NULL, '2024-01-05 10:45:20', NULL, 26);
INSERT INTO `record` VALUES (323, '王', '这结果是不一样的，所以这就很要求你的提示词的严密性和对他的要求的强制性。', NULL, '2024-01-05 10:45:25', NULL, 26);
INSERT INTO `record` VALUES (324, '王', '嗯，这个是这个是反复优化的一个结果啊，等会可以看一下。', NULL, '2024-01-05 10:45:32', NULL, 26);
INSERT INTO `record` VALUES (325, '王', '后端的工作量主要就是这些啊，行那啊。', NULL, '2024-01-05 10:45:37', NULL, 26);
INSERT INTO `record` VALUES (326, '王', '第1个收入的话你们就这个。', NULL, '2024-01-05 10:45:43', NULL, 26);
INSERT INTO `record` VALUES (327, '王', '倒没有考虑到我们其实主要考虑的原因就是这是我们在录音方面我们。', NULL, '2024-01-05 10:45:50', NULL, 26);
INSERT INTO `record` VALUES (328, '王', '主要考虑的问题就是它的一个分片传输就是啊，因为前面也讲到就是说啊。', NULL, '2024-01-05 10:45:56', NULL, 26);
INSERT INTO `record` VALUES (329, '王', '这是对。', NULL, '2024-01-05 10:45:59', NULL, 26);
INSERT INTO `record` VALUES (330, '王', '我们的我们的分配有设计好哦，我们的分片式带那个带时间的就是他对对他有段时间。', NULL, '2024-01-05 10:46:09', NULL, 26);
INSERT INTO `record` VALUES (331, '王', '然后然后它后端进行总结的时候，他会按照时间，就是将不同时间片。', NULL, '2024-01-05 10:46:15', NULL, 26);
INSERT INTO `record` VALUES (332, '王', '的那个语音，它转文字以后，生出来的那些文本片段他会。', NULL, '2024-01-05 10:46:22', NULL, 26);
INSERT INTO `record` VALUES (333, '王', '重新按顺序给他排成一段完整文字以后，然后再进行解析。', NULL, '2024-01-05 10:46:28', NULL, 26);
INSERT INTO `record` VALUES (334, '王', '嗯，然后然后这边是主要的这个。', NULL, '2024-01-05 10:46:35', NULL, 26);
INSERT INTO `record` VALUES (335, '王', '录音service的实现。', NULL, '2024-01-05 10:46:39', NULL, 26);
INSERT INTO `record` VALUES (336, '王', NULL, NULL, '2024-01-05 10:46:41', '<end>', 26);
INSERT INTO `record` VALUES (337, '王', '娜娜现在可以可以可以大家看一下，他现在这个。', NULL, '2024-01-05 10:46:44', NULL, 26);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `permission_id` int(8) NOT NULL AUTO_INCREMENT,
  `permission_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `permission_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, 'sys:queryUser', '查询用户', '/getUser');
INSERT INTO `sys_permission` VALUES (2, 'sys:queryUser2', '测试权限', '/getUser2');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` int(8) NOT NULL AUTO_INCREMENT,
  `account` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账号',
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户密码',
  `last_login_time` datetime(0) DEFAULT NULL COMMENT '上一次登录时间',
  `enabled` tinyint(1) DEFAULT 1 COMMENT '账号是否可用。默认为1（可用）',
  `account_not_expired` tinyint(1) DEFAULT 1 COMMENT '是否过期。默认为1（没有过期）',
  `account_not_locked` tinyint(1) DEFAULT 1 COMMENT '账号是否锁定。默认为1（没有锁定）',
  `credentials_not_expired` tinyint(1) DEFAULT NULL COMMENT '证书（密码）是否过期。默认为1（没有过期）',
  `create_time` datetime(0) DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '888', '王', '$2a$10$hdw4XoJNxuT0nwujYNCrHuQ.g5zCThQ8qhkLANXESKZ1JeSo9xlNO', '2023-08-16 09:45:53', 1, 1, 1, 1, '2023-08-09 17:49:20', '2023-08-09 17:49:22');

-- ----------------------------
-- Table structure for sys_user_permission_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_permission_relation`;
CREATE TABLE `sys_user_permission_relation`  (
  `user_permission_relation_id` int(8) NOT NULL,
  `user_id` int(8) DEFAULT NULL,
  `permission_id` int(8) DEFAULT NULL,
  PRIMARY KEY (`user_permission_relation_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户权限关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_permission_relation
-- ----------------------------
INSERT INTO `sys_user_permission_relation` VALUES (1, 1, 1);
INSERT INTO `sys_user_permission_relation` VALUES (2, 2, 2);

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `gender` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '性别',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '电话',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `avatar` blob COMMENT '头像',
  `test` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '测试列',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1245 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 'admin', '123456', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (2, 'a', '1', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (3, '4', '4', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (11, '1', '123', 'women', '13301234566', '1889900@163.com', NULL, NULL);
INSERT INTO `user_info` VALUES (67, '2', '2', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (1225, '123456', '123456', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (1234, 'Lierick', 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (1237, '0', '0', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (1238, '5', '111111', 'men', '12', '123@qq.com', NULL, NULL);
INSERT INTO `user_info` VALUES (1239, '7', '1234', 'men', NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (1242, '3', '11111', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (1243, '6', '123', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (1244, 'iraina', '12345', NULL, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
