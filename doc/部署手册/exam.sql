/*
Navicat MySQL Data Transfer

Source Server         : mysql_local
Source Server Version : 50525
Source Host           : localhost:3306
Source Database       : exam

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2012-10-11 18:41:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `performance_test`
-- ----------------------------
DROP TABLE IF EXISTS `performance_test`;
CREATE TABLE `performance_test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of performance_test
-- ----------------------------

-- ----------------------------
-- Table structure for `t_config`
-- ----------------------------
DROP TABLE IF EXISTS `t_config`;
CREATE TABLE `t_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pro_key` varchar(50) NOT NULL,
  `pro_value` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_config
-- ----------------------------
INSERT INTO `t_config` VALUES ('1', 'title', '测试DEMO');
INSERT INTO `t_config` VALUES ('2', 'examineeDays', '15');
INSERT INTO `t_config` VALUES ('3', 'themeName', 'gray');

-- ----------------------------
-- Table structure for `t_exam`
-- ----------------------------
DROP TABLE IF EXISTS `t_exam`;
CREATE TABLE `t_exam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(1000) NOT NULL,
  `score` int(11) NOT NULL,
  `cn_proportion` int(11) DEFAULT NULL,
  `en_proportion` int(11) DEFAULT NULL,
  `pun_proportion` int(11) DEFAULT NULL,
  `num_proportion` int(11) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `long_time` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  `choice` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_exam_question_id` (`question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_exam
-- ----------------------------
INSERT INTO `t_exam` VALUES ('1', '一次历史性的考试，我很理解你', '100', '2', '3', '2', '1', '1', '2012-09-18 00:00:00', '2012-12-12 00:00:00', '1024', '1', '');
INSERT INTO `t_exam` VALUES ('2', '倒萨发生的发生地方的萨芬', '100', '2', '1', '1', '1', '-1', '2012-09-20 00:00:00', '2012-09-27 00:00:00', '60', '6', '');
INSERT INTO `t_exam` VALUES ('3', '的司法斯蒂芬温柔圣达菲', '100', '1', '1', '1', '1', '-1', '2012-09-18 12:28:02', '2012-09-19 12:28:05', '60', '6', '');
INSERT INTO `t_exam` VALUES ('4', '第三方撒地方水电费的司法斯蒂芬', '100', '1', '1', '1', '1', '-1', '2012-09-19 12:28:38', '2012-09-28 12:28:56', '60', '1', '');
INSERT INTO `t_exam` VALUES ('5', '001撒打发的色温，才是当然。caecdew的萨芬', '100', '2', '2', '1', '1', '-1', '2012-09-28 12:29:55', '2012-09-28 12:29:59', '120', '1', '');
INSERT INTO `t_exam` VALUES ('6', '002倒萨发生的发生地方额阿什顿发生地', '100', '2', '1', '1', '1', '-1', '2012-09-21 12:34:38', '2012-09-29 12:34:44', '120', '1', '');
INSERT INTO `t_exam` VALUES ('7', 'asdfasdfasdfsadf', '100', '1', '1', '1', '1', '-1', '2012-09-19 13:33:35', '2012-09-28 13:33:39', '60', '6', '');
INSERT INTO `t_exam` VALUES ('8', '点撒爱上的人撒地方玩儿撒地方', '100', '1', '1', '1', '1', '-1', '2012-09-21 13:37:21', '2012-09-22 13:37:17', '90', '5', '');
INSERT INTO `t_exam` VALUES ('9', 'asdfasdfasdfasdfaaaa', '100', '1', '1', '1', '1', '-1', '2012-09-21 13:45:43', '2012-09-28 13:45:46', '60', '3', '');
INSERT INTO `t_exam` VALUES ('10', 'asdfasdfadsfasdfasdfasdf', '100', '1', '1', '1', '1', '-1', '2012-09-21 13:46:05', '2012-09-28 13:46:08', '60', '6', '');
INSERT INTO `t_exam` VALUES ('11', '来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧', '100', '1', '1', '1', '1', '-1', '2012-10-08 15:42:07', '2012-10-09 15:42:10', '1', '9', '');
INSERT INTO `t_exam` VALUES ('12', '测试打字测试打字测试打字测试打字', '100', '1', '1', '1', '1', '-1', '2012-10-08 17:24:40', '2012-10-11 17:24:42', '1', '11', '');
INSERT INTO `t_exam` VALUES ('13', '来吧来吧来吧来吧来吧来吧来吧来吧来吧来吧4', '100', '1', '1', '1', '1', '1', '2012-10-10 09:40:47', '2012-10-18 09:40:50', '1', '9', '');
INSERT INTO `t_exam` VALUES ('14', '又是一个新的考试，这次考的内容很有趣，大家来参加吧', '100', '1', '1', '1', '1', '1', '2012-10-11 18:05:35', '2012-10-31 18:05:41', '1', '1', '');
INSERT INTO `t_exam` VALUES ('15', '测试一下哈哈水水水水水水水水水水水水水谁是谁', '100', '1', '1', '1', '1', '0', '2014-07-24 18:19:19', '2016-12-30 18:19:33', '60', '10', '');

-- ----------------------------
-- Table structure for `t_examinee`
-- ----------------------------
DROP TABLE IF EXISTS `t_examinee`;
CREATE TABLE `t_examinee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `card` varchar(20) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `reg_date` datetime NOT NULL,
  `can_days` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_examinee_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_examinee
-- ----------------------------
INSERT INTO `t_examinee` VALUES ('2', '0011', '李逵', '123', null, null, '2012-09-19 09:12:35', '0');
INSERT INTO `t_examinee` VALUES ('7', '00001', '狗篮子', '123', null, null, '2012-09-28 11:29:20', '15');
INSERT INTO `t_examinee` VALUES ('8', '00434', '张晓丽', '123123', null, null, '2012-10-11 09:13:24', '15');
INSERT INTO `t_examinee` VALUES ('9', 'a0001', '小强', '123123', null, null, '2012-10-11 17:10:53', '15');

-- ----------------------------
-- Table structure for `t_log`
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `log` varchar(5000) NOT NULL,
  `date` datetime NOT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_log
-- ----------------------------

-- ----------------------------
-- Table structure for `t_question`
-- ----------------------------
DROP TABLE IF EXISTS `t_question`;
CREATE TABLE `t_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(1000) NOT NULL,
  `choice` bit(1) NOT NULL,
  `audio_path` varchar(255) DEFAULT NULL,
  `text_content` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_question
-- ----------------------------
INSERT INTO `t_question` VALUES ('1', '考试，不能出错了的说法阿斯顿', '', null, '哈傻傻地发');
INSERT INTO `t_question` VALUES ('2', '在集合石一二阿什顿阿什顿', '', null, 's');
INSERT INTO `t_question` VALUES ('3', '阿斯顿发水电费是', '', '111111111', '范德萨发发生大赛的分为第三方撒地方阿斯顿飞娃儿撒旦法斯蒂芬穿西装的撒二恶烷说打法二查询阿斯顿认为阿斯顿飞任务二');
INSERT INTO `t_question` VALUES ('5', '这是一个选择题的题库，考的是2009年的知识', '', null, '该题库为理论考试,不能作为速录考试使用！');
INSERT INTO `t_question` VALUES ('6', '一共10000字，在20分钟内完成，共100分。', '', 'd:/a/b/sdf.mp3', '地方撒阿斯顿发水电费撒发生的');
INSERT INTO `t_question` VALUES ('7', '啊啊啊啊啊啊啊啊水电费111', '', '1348189268535.mp3', '啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111啊啊啊啊啊啊啊啊水电费111');
INSERT INTO `t_question` VALUES ('8', '啊啊啊啊啊啊啊啊水电费2222', '', '1348189290999.mp3', '啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222啊啊啊啊啊啊啊啊水电费2222');
INSERT INTO `t_question` VALUES ('9', '挺好吧，333阿什顿发生地发生的发生1111', '', '1348191714812.mp3', '挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111');
INSERT INTO `t_question` VALUES ('10', '333333453啊啊啊啊啊水电费111', '', null, '挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111挺好吧，333阿什顿发生地发生的发生1111');
INSERT INTO `t_question` VALUES ('11', '测试打字测试打字测试打字测试打字测试打字', '', null, '外交部发言人洪磊在10月8日的例行记者会上，对委内瑞拉总统大选平稳、顺利举行，以及查韦斯总统再次当选表示祝贺，并祝愿委内瑞拉在查韦斯总统的领导下，在国家建设道路上不断取得新成就。\r\n当地时间10月7日晚，在统计超过90％的选票后，委内瑞拉全国选举委员会宣布，初步结果显示现任总统查韦斯将赢得本届总统选举。现年58岁的查韦斯已经执政近14年，并将开始新的6年总统任期。骆驼没有瘦死，但是却在黄金周累死了！据媒体报道，甘肃敦煌鸣沙山月牙泉景区目前有1000峰骆驼专业服务游客，每天从早晨5点半到天黑马不停蹄，一峰骆驼一天可以送7个客人，但还是不能满足景区目前每天近8000名游客选择这个旅游项目的需要，已连续两天出现骆驼因劳累致死。\r\n　　大量的游客，在这个长达8天的中秋国庆黄金周里奔赴各大旅游目的地，长城、故宫、鼓浪屿等均人满为患，华山、三亚海滩等更暴露出各种问题，多条高速公路出现车祸等事故。除了春节，仅剩下的一个长假――国庆黄金周又因与中秋重叠、高速免费等利好再次释放出了巨大的能量。多名专家学者再次呼吁，恢复“五一”黄金周以缓解出游需求。\r\n　　4.25亿人次出游再创新高\r\n　　长达8天的中秋国庆黄金周落下帷幕，出行人数的“井喷”，直接造成全国各地景点几近“爆棚”。据中国旅游研究院最新统计数据显示，8天假日期间全国共接待游客4.25亿人次，比2011年“十一”黄金周增长40.9%；实现旅游收入2105亿元，比2011年“十一”黄金周增长44.4%，游客人均花费支出495元。此外，8天长假全国纳入监测的119个直报景区点共接待游客3424.56万人次，同比增长20.96%。全国假日办方面介绍，今年中秋、国庆长假国内旅游市场需求旺盛，持续高位运行。中秋、国庆假期是国务院出台小型客车免收高速公路费政策执行的第一个长假，很多地方甚至呈现井喷之势。国内热点景区持续爆满，纷纷迎来“最热黄金周”。故宫博物院仅10月2日一天就接待游客数超18万人次，创造历年接待游客最高值。\r\n　　“今年出去旅游简直就是看人潮！”虽然已经有了充分的心理准备，但是10月4日才登鼓浪屿的余先生仍然感叹鼓浪屿上人潮过于汹涌，他表示，由于假期前半段需要加班，所以想着错峰与家人前往厦门度假，没想到虽然错峰了，但是仍然人非常多，“假期后半段的鼓浪屿已经没有新闻报道里那种人潮缓慢移动的现象，但是想要拍个照，背景里一定都是各种各样的游客。”与余先生的感受相似的人不在少数，今年即使错峰出行，也感受到了巨大的人潮压力。“供给不足、管理不善造成全国旅游拥堵。”北京大学旅游开发与规划研究中心主任吴必虎教授在其实名微博中表示，节后盘点2012国庆黄金周，旅游供给不足、政府管理不善是全方位拥堵原因。中国人均旅游资源供给不足、国民可支配假期供给不足、多部门之间协调不足、景区管理水平不足，四不足造成大拥堵。长期以来重视生产、忽视生活的政策需要彻底改变：旅游已成新的生活方式。\r\n　　假期短缺导致出游更集中\r\n　　2008年起，我国正式取消了“五一”黄金周，增加清明、端午、中秋三个小黄金周。结果不仅职工们不再能够享受到五一长假期的充分时间，探亲的机会少了，同时自2008年以后游客都更向“十一”黄金周集中。中国社会科学院旅游研究中心副主任刘德谦教授分析，2008年取消“五一”黄金周的前五年，每年“十一”黄金周游人的年均增幅是12.51%，大致与这五年国内旅游的年均增幅处在相同水平(12.69%)，而2008年取消“五一”黄金周后的这五年，每年“十一”黄金周游人的年增幅大多处在20%以上，如以2008-2011年的数据来计算年均增幅，也已经高达19.93%，而这四年国内旅游的年增幅却处在10%的上下，2008年更降到了6.3%。“从这四年每年‘十一’黄金周游人增幅大大地超过前五年，也大大超过这几年国内旅游的年均增幅来看，游人向‘十一’黄金周的集中也就更为突出，所以今年出现的超饱和的拥挤也就不足为怪了。”\r\n　　刘德谦调查发现，自2008年我国取消“五一”黄金周之后，在每年“十一”黄金周时，国内旅游都出现了超强增长的劲头，每年的人数增长几乎都20%以上，远远超过了前几年的平均增幅，如与2005-2007年的人数增幅相比，2008年的增幅就多出了好几个百分点，2009、2010就更高。很显然，这是许多原来在“五一”黄金周出游的旅游者转向了“十一”黄金周集中。如果再看2003年至2011年国内旅游的人数，可以看出，2008年以来全年国内旅游的人数增幅却在减缓，因此还可以计算出，“十一”黄金周的旅游人数在全年总人数中的比重也在增大。这也就是说，2008年的国内旅游者更加向“十一”黄金周集中。刘德谦进一步指出，2011年“十一”黄金周期间，全国共接待游客3.02亿人次，比上年同期增长18.8%；而2011年全年国内出游人数却只比上年增长13.2%，“由节假日人数的增幅高于全年可以得知，2009年取消‘五一’长假日造成的‘十一’长假日更加拥挤的问题至今仍然未能化解。”\r\n　　■改革猜想猜想一：恢复“五一”黄金周？\r\n　　“黄金周的集中出游实际上是一种理性选择。为什么这么说呢？判断一个行为是否理性两个标准：是否知情和是否自愿。”中国社科院旅游研究中心特约研究员刘思敏表示，黄金周已经10多年了，大众传媒时代黄金周拥堵地球人都知道；其次，黄金周出游不是被迫的，不是迫于亲情、民俗等因素，完全是自愿的。平时不能带薪休假，春节要回老家，中长途旅游的愿望如果不是在长假实现，什么时候才能实现？“而且对比一下，每年人数都在增加，去年去了故宫的，难道不是比今年再去要聪明吗？不要以为老百姓都是傻子只有专家聪明。他们也是经过盘算的，这是种无奈的选择。”\r\n　　刘思敏指出，现在重要的任务是“泄洪”。“五一”长假取消后，“泄洪”的口子少了，建议恢复“五一”黄金周，或者在5月设置一个新的长假，满足人们出游的愿望。当然每年的“十一”黄金周对于一个省旅游的收入占到非常大的比重，有些管理者在潜意识中把收益放在第一位，缺乏与之匹配的接待、服务能力和应对措施。\r\n　　猜想二：实行黄金周错峰休假？\r\n　　有部分分析认为，与其在长假期间旅游需求井喷式爆发，不如将这种需求分散开来，削峰填谷。在分散客流方面，国家对“十一”长假也不妨实行错峰休假。目前我国“十一”黄金周休假时间是指定的7天，休假时间完全是确定的。而今后国家在规定“十一”7天假日的时候，不妨实行错峰休假，将整个假期分为前、后两段。员工与用人单位在个人申请和双方协商的基础上，确定休假日期。如此一来，整个长假的人流基本上被一分为二。即使在10月1日这样的节日形成重叠，因为往返客流形成错峰，也不会给交通带来巨大压力，而旅游景点人满为患的压力也在一定程度上得到减轻。国庆长假推行错峰休假，既分散了客流，又可以保留节日的人文意义。\r\n　　不过，对于错峰休假，多位专家认为意义不大。“错峰休假只是一个想当然的建议，至少在2007年的时候已经有人提过。”刘思敏表示，错峰休假的结果就是有可能导致全国长达半个月的停顿与混乱，简单说，就是比如以长江为界，江南前一周没人做事情，江北的人没法对接，反之亦如是。而一个单位或者机构很难在只有一半人的情况下，实现正常运转，这个社会成本太大了，所以纯粹的想当然。\r\n　　猜想三：落实带薪休假？\r\n　　但是并非所有人都赞成恢复“五一”黄金周，那将又是一个严重拥堵的假期。清华大学教授蔡继明在接受中央电视台《新闻1+1》栏目连线采访时表示，1982年的世界旅游会议以及1985年的世界旅游组织通过的宣言都强调，各国政府要采取切实有效的措施错开放假的时间，特别是要推行带薪休假。其实2008年中央政府之所以对黄金周制度做了一个调整，取消了一个“五一”黄金周，同时增加了清明、中秋、端午，也同时推行了带薪休假制度，就是按照这样一个精神，在减少集中放假这样一个情况下增加人们自由选择休假的时间。这个改革的方向，应该坚持而不应该退回去。\r\n　　蔡继明进一步认为，从2008年到现在仅仅四年的时间，带薪休假至少也应该落实了40%到50%，而且今年温家宝总理在政府工作报告当中又特别明确地提出要落实带薪休假制度，通过全民的努力，这一天会逐渐地向我们走来。不过蔡继明这一主张遭到了多位专家反对，刘思敏指出，带薪休假至少也应该落实了40%到50%，这个数字没有权威出处，且与公众的感知明显不符，“即使假设这个数据靠谱，也正好说明至少有一半的公众没有享受到带薪休假，也就是离带薪休假在中国的普遍落实还很遥远，任何人也没有权利剥夺那50%没有享受到带薪休假权利的中国公民享受黄金周长假的权利。”\r\n　　■专家说法休假制度需要进一步优化\r\n　　假日制度的难题，多年来悬而不解，虽然老百姓并不满意目前的放假安排，但是目前众多建议也没有被相关部门采纳。我国带薪年休假的进一步落实，我国假日制度的优化，也都还需要全国上下的共同努力。\r\n　　中国社会科学院旅游研究中心副主任刘德谦建议，努力保障职工应有的休息权利，推动现有假日制度的落实，并积极研究假日制度的进一步优化。其一，建议全国劳动保障部门行动起来，对有关用人单位的加班加点进行一次深入的调查，并在此研究的基础上提出保障职工休息权利的具体办法，以进一步制止用人单位非法侵占职工平日和假日休息的安排和行为；其二，建议国家有关部门对带薪年休假制度的落实情况进行调研，以发现其当前未能普遍落实的具体原因，并且研究出有关解决的办法(包括对确有困难的行业和中小企业的具体帮助，乃至必要费用的转移支付)，以推动带薪休假制度的进一步落实；其三，建议国家有关部门对我国现行假日制度进行调研，深入了解我国居民对此的意见和想法，以期实现假日制度的不断优化。');

-- ----------------------------
-- Table structure for `t_question_choice`
-- ----------------------------
DROP TABLE IF EXISTS `t_question_choice`;
CREATE TABLE `t_question_choice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(2000) NOT NULL,
  `proportion` int(11) NOT NULL,
  `a_option` varchar(1000) NOT NULL,
  `b_option` varchar(1000) NOT NULL,
  `c_option` varchar(1000) DEFAULT NULL,
  `d_option` varchar(1000) DEFAULT NULL,
  `e_option` varchar(1000) DEFAULT NULL,
  `f_option` varchar(1000) DEFAULT NULL,
  `g_option` varchar(1000) DEFAULT NULL,
  `h_option` varchar(1000) DEFAULT NULL,
  `answer` varchar(20) NOT NULL,
  `multi` bit(1) NOT NULL,
  `question_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Index_question_choice_qid` (`question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_question_choice
-- ----------------------------
INSERT INTO `t_question_choice` VALUES ('1', '阿斯顿发水电费打发斯蒂芬', '5', 'aaaaaa', 'bbb', 'ccc', 'ddd', 'eee', 'fff', 'ggg', 'hhhh', 'A', '', '1');

-- ----------------------------
-- Table structure for `t_role_auth`
-- ----------------------------
DROP TABLE IF EXISTS `t_role_auth`;
CREATE TABLE `t_role_auth` (
  `rolename` varchar(50) NOT NULL,
  `auth` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_auth
-- ----------------------------
INSERT INTO `t_role_auth` VALUES ('EXAMINEE', '/exam#/temp-save');
INSERT INTO `t_role_auth` VALUES ('EXAMINEE', '/exam#/change-layout');
INSERT INTO `t_role_auth` VALUES ('EXAMINEE', '/exam#examing');
INSERT INTO `t_role_auth` VALUES ('EXAMINEE', '/exam#index');
INSERT INTO `t_role_auth` VALUES ('EXAMINEE', '/exam#score');
INSERT INTO `t_role_auth` VALUES ('EXAMINEE', '/system/message#receive');
INSERT INTO `t_role_auth` VALUES ('EXAMINEE', '/exam#/score-data');
INSERT INTO `t_role_auth` VALUES ('EXAMINEE', '/exam#/examing-data');
INSERT INTO `t_role_auth` VALUES ('EXAMINEE', '/exam#save');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/invigilate#report');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/message#send');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/user#list');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/themes#change');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/question#input');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/question#save');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/user#data');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/config#save');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/config/ip#list');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/online#kill');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/message#receive');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system#index');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/examinee#data');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/examinee#input_imp');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/examinee#score_detail');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/exam#data');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/question#data');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/examinee#save');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/examinee#delete');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/online#data');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/question_c#data');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/question#list');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/exam#qdata');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/user#save');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/user#enabled');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/config/ip#save');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/question#delete');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/question_c#delete');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/config/ip#remove');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/online#list');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/question_c#input');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/exam#save');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/question_c#list');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/online#killAll');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/exam#list');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/examinee#score_data');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/invigilate#list');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/user#disabled');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/examinee#input');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/exam#input');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/user#input');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/question_c#save');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/examinee#imp');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/exam#score');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/config/ip#add');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam#/examing-data');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/user#delete');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/file#upload');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/exam#qcdata');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/exam#score_data');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/examinee#list');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/config#index');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/exam#delete');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/examinee#score');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/exam#score_detail');

-- ----------------------------
-- Table structure for `t_score`
-- ----------------------------
DROP TABLE IF EXISTS `t_score`;
CREATE TABLE `t_score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start_date` datetime NOT NULL,
  `long_time` int(11) NOT NULL,
  `context` text NOT NULL,
  `score` int(11) NOT NULL,
  `examinee_id` int(11) NOT NULL,
  `exam_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Index_score_examinee_id` (`examinee_id`),
  KEY `Index_score_exam_id` (`exam_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_score
-- ----------------------------
INSERT INTO `t_score` VALUES ('2', '2012-10-11 17:10:02', '4', 'asdfasdfasdfasdfasdf', '0', '8', '13');
INSERT INTO `t_score` VALUES ('3', '2012-10-11 17:10:12', '5', 'asdfasdfasdfasdf', '0', '8', '12');
INSERT INTO `t_score` VALUES ('4', '2012-10-11 17:11:02', '6', 'asdfasdgsdfagasdfasdfasdfasdrweasdfasdfasdfasdfasdf', '0', '9', '13');
INSERT INTO `t_score` VALUES ('5', '2012-10-11 18:07:09', '26', '傻傻的发', '40', '9', '14');

-- ----------------------------
-- Table structure for `t_security_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_security_role`;
CREATE TABLE `t_security_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(50) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `rolename` (`rolename`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_security_role
-- ----------------------------
INSERT INTO `t_security_role` VALUES ('1', 'SYSADMIN', '后台管理员');
INSERT INTO `t_security_role` VALUES ('2', 'EXAMINEE', '考生');

-- ----------------------------
-- Table structure for `t_security_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_security_user`;
CREATE TABLE `t_security_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_security_user
-- ----------------------------
INSERT INTO `t_security_user` VALUES ('1', 'admin', 'admin', '', '系统管理员');

-- ----------------------------
-- Table structure for `t_sequence`
-- ----------------------------
DROP TABLE IF EXISTS `t_sequence`;
CREATE TABLE `t_sequence` (
  `table_name` varchar(50) NOT NULL,
  `seq_value` int(11) NOT NULL,
  PRIMARY KEY (`table_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sequence
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `username` varchar(50) NOT NULL,
  `rolename` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('admin', 'SYSADMIN');
