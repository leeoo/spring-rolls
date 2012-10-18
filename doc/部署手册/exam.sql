/*
Navicat MySQL Data Transfer

Source Server         : mysql_local
Source Server Version : 50525
Source Host           : localhost:3306
Source Database       : exam

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2012-10-18 17:50:59
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_exam
-- ----------------------------
INSERT INTO `t_exam` VALUES ('1', '阿斯顿是', '100', '1', '1', '1', '1', '-1', '2012-10-18 17:06:54', '2012-10-18 17:15:08', '10', '1', '');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_examinee
-- ----------------------------
INSERT INTO `t_examinee` VALUES ('1', '00001', '小强', '123123', null, null, '2012-10-18 17:08:48', '15');
INSERT INTO `t_examinee` VALUES ('2', '00002', '小美眉', '123123', null, null, '2012-10-18 17:10:49', '15');
INSERT INTO `t_examinee` VALUES ('3', '00003', '大伟哥', '123123', null, null, '2012-10-18 17:14:10', '15');

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_question
-- ----------------------------
INSERT INTO `t_question` VALUES ('1', '真的很不错啊啊啊啊啊啊', '', null, '该题库为理论考试,不能作为速录考试使用！');

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_question_choice
-- ----------------------------
INSERT INTO `t_question_choice` VALUES ('1', '啊啊啊啊啊啊啊啊', '1', '阿斯顿飞', '阿斯顿发大水', '撒地方', '阿斯顿发送到', null, null, null, null, 'C', '', '1');
INSERT INTO `t_question_choice` VALUES ('2', '鲍威尔撒的发生的阿斯顿飞', '1', '2温柔撒旦', '萨达', '为二位阿斯顿', '萨芬', null, null, null, null, 'A', '', '1');
INSERT INTO `t_question_choice` VALUES ('3', '把惹我发的噶阿斯顿飞我阿斯顿飞想', '1', '瓦下次v', '额外地方', '阿斯顿飞俺是', '萨菲手动发送', null, null, null, null, 'D', '', '1');
INSERT INTO `t_question_choice` VALUES ('4', '巴萨的稳定发生地', '1', '而撒地方', '阿斯顿飞往', '阿道夫', '三大发生地', null, null, null, null, 'D', '', '1');

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
INSERT INTO `t_role_auth` VALUES ('EXAMINEE', '/exam#/choice');
INSERT INTO `t_role_auth` VALUES ('EXAMINEE', '/exam#index');
INSERT INTO `t_role_auth` VALUES ('EXAMINEE', '/exam#score');
INSERT INTO `t_role_auth` VALUES ('EXAMINEE', '/system/message#receive');
INSERT INTO `t_role_auth` VALUES ('EXAMINEE', '/exam#/score-data');
INSERT INTO `t_role_auth` VALUES ('EXAMINEE', '/exam#/examing-data');
INSERT INTO `t_role_auth` VALUES ('EXAMINEE', '/exam#save');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/invigilate#report');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/message#send');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/report#list');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/user#list');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/exam#stop');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/themes#change');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/question#input');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/question#save');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/question_c#/choice_save');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/user#data');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/online#kill');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/config#save');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/config/ip#list');
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
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/report#score');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/report#data');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/question_c#/choice_delete');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/question_c#data');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/question#list');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/system/user#save');
INSERT INTO `t_role_auth` VALUES ('SYSADMIN', '/exam/exam#qdata');
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_score
-- ----------------------------
INSERT INTO `t_score` VALUES ('1', '2012-10-18 17:09:16', '32', '1:B;2:D;3:B;4:B;', '0', '1', '1');
INSERT INTO `t_score` VALUES ('2', '2012-10-18 17:11:08', '33', '1:A;2:C;3:B;4:B;', '0', '2', '1');
INSERT INTO `t_score` VALUES ('3', '2012-10-18 17:14:17', '14', '1:B;2:D;3:D;4:D;', '50', '3', '1');

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
