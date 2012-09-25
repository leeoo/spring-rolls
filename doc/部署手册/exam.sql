/*
Navicat MySQL Data Transfer

Source Server         : mysql_local
Source Server Version : 50525
Source Host           : localhost:3306
Source Database       : exam

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2012-09-25 17:09:42
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
INSERT INTO `t_config` VALUES ('1', 'title', '国家职业资格在线考评系统');
INSERT INTO `t_config` VALUES ('2', 'examineeDays', '15');
INSERT INTO `t_config` VALUES ('3', 'themeName', 'default');

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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_exam
-- ----------------------------
INSERT INTO `t_exam` VALUES ('1', '一次历史性的考试，我很理解你', '100', '2', '3', '2', '1', '1', '2012-09-18 00:00:00', '2012-12-12 00:00:00', '1024', '1', '');
INSERT INTO `t_exam` VALUES ('2', '倒萨发生的发生地方的萨芬', '100', '2', '1', '1', '1', '1', '2012-09-20 00:00:00', '2012-09-27 00:00:00', '60', '6', '');
INSERT INTO `t_exam` VALUES ('3', '的司法斯蒂芬温柔圣达菲', '100', '1', '1', '1', '1', '-1', '2012-09-18 12:28:02', '2012-09-19 12:28:05', '60', '6', '');
INSERT INTO `t_exam` VALUES ('4', '第三方撒地方水电费的司法斯蒂芬', '100', '1', '1', '1', '1', '1', '2012-09-19 12:28:38', '2012-09-28 12:28:56', '60', '1', '');
INSERT INTO `t_exam` VALUES ('5', '001撒打发的色温，才是当然。caecdew的萨芬', '100', '2', '2', '1', '1', '0', '2012-09-28 12:29:55', '2012-09-28 12:29:59', '120', '1', '');
INSERT INTO `t_exam` VALUES ('6', '002倒萨发生的发生地方额阿什顿发生地', '100', '2', '1', '1', '1', '0', '2012-09-21 12:34:38', '2012-09-29 12:34:44', '120', '1', '');
INSERT INTO `t_exam` VALUES ('7', 'asdfasdfasdfsadf', '100', '1', '1', '1', '1', '1', '2012-09-19 13:33:35', '2012-09-28 13:33:39', '60', '6', '');
INSERT INTO `t_exam` VALUES ('8', '点撒爱上的人撒地方玩儿撒地方', '100', '1', '1', '1', '1', '0', '2012-09-21 13:37:21', '2012-09-22 13:37:17', '90', '5', '');
INSERT INTO `t_exam` VALUES ('9', 'asdfasdfasdfasdfaaaa', '100', '1', '1', '1', '1', '0', '2012-09-21 13:45:43', '2012-09-28 13:45:46', '60', '3', '');
INSERT INTO `t_exam` VALUES ('10', 'asdfasdfadsfasdfasdfasdf', '100', '1', '1', '1', '1', '0', '2012-09-21 13:46:05', '2012-09-28 13:46:08', '60', '6', '');

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_examinee
-- ----------------------------
INSERT INTO `t_examinee` VALUES ('2', '0011', '李逵', '123', null, null, '2012-09-19 09:12:35', '0');
INSERT INTO `t_examinee` VALUES ('5', '0003', '沙悟净', '123', null, null, '2012-09-19 09:25:06', '8');
INSERT INTO `t_examinee` VALUES ('6', '00434', '张晓琳', '123123', null, null, '2012-09-25 11:18:00', '15');

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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_score
-- ----------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_security_role
-- ----------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_security_user
-- ----------------------------

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
