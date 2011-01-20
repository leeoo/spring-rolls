/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50149
Source Host           : localhost:3306
Source Database       : spring

Target Server Type    : MYSQL
Target Server Version : 50149
File Encoding         : 65001

Date: 2011-01-20 15:22:00
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `t_logger_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_logger_info`;
CREATE TABLE `t_logger_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `cnname` varchar(50) DEFAULT NULL,
  `ip` varchar(50) DEFAULT NULL,
  `organ_name` varchar(50) DEFAULT NULL,
  `log_date` datetime DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `somewhere` varchar(100) DEFAULT NULL,
  `something` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_logger_info
-- ----------------------------
INSERT INTO `t_logger_info` VALUES ('1', 'admin', '系统管理员', '127.0.0.1', '新华保险公司总部', '2010-11-16 16:32:47', 'http://localhost/example/logger/list/1.htm', '日志管理', '查看列表');
INSERT INTO `t_logger_info` VALUES ('2', 'admin', '系统管理员', '127.0.0.1', '新华保险公司总部', '2010-11-16 16:32:42', 'http://localhost/example/logger/list/1.htm', '日志管理', '查看列表');
INSERT INTO `t_logger_info` VALUES ('3', 'admin', '系统管理员', '127.0.0.1', null, '2010-11-17 11:04:23', 'http://localhost/example/logger/list/1.htm', '日志管理', '查看列表');
INSERT INTO `t_logger_info` VALUES ('4', 'admin', '系统管理员', '127.0.0.1', null, '2010-11-17 11:04:25', 'http://localhost/example/logger/list/1.htm', '日志管理', '查看列表');
INSERT INTO `t_logger_info` VALUES ('5', 'admin', '系统管理员', '127.0.0.1', null, '2010-11-17 11:35:53', 'http://localhost/example/logger/list/1.htm', '日志管理', '查看列表');
INSERT INTO `t_logger_info` VALUES ('6', 'admin', '系统管理员', '127.0.0.1', null, '2010-11-17 11:35:44', 'http://localhost/example/logger/list/1.htm', '日志管理', '查看列表');
INSERT INTO `t_logger_info` VALUES ('8', 'admin', '系统管理员', '127.0.0.1', null, '2010-11-17 13:41:57', 'http://localhost/example/logger/list/1.htm', '日志管理', '查看列表');
INSERT INTO `t_logger_info` VALUES ('9', 'admin', '系统管理员', '127.0.0.1', null, '2010-11-17 13:43:07', 'http://localhost//example/logger/list/2.htm', '日志管理', '查看列表');
INSERT INTO `t_logger_info` VALUES ('10', 'admin', '系统管理员', '127.0.0.1', null, '2010-11-17 13:43:04', 'http://localhost/example/logger/list/1.htm', '日志管理', '查看列表');
INSERT INTO `t_logger_info` VALUES ('11', 'admin', '系统管理员', '127.0.0.1', null, '2010-11-17 13:43:08', 'http://localhost//example/logger/list/1.htm', '日志管理', '查看列表');
INSERT INTO `t_logger_info` VALUES ('12', 'admin', '系统管理员', '127.0.0.1', null, '2010-11-17 13:42:33', 'http://localhost//example/logger/list/2.htm', '日志管理', '查看列表');
INSERT INTO `t_logger_info` VALUES ('13', 'admin', '系统管理员', '127.0.0.1', null, '2010-11-17 13:42:34', 'http://localhost//example/logger/list/1.htm', '日志管理', '查看列表');
INSERT INTO `t_logger_info` VALUES ('14', 'admin', '系统管理员', '127.0.0.1', null, '2010-11-17 13:43:24', 'http://localhost/example/logger/list/1.htm', '日志管理', '查看列表');
INSERT INTO `t_logger_info` VALUES ('15', 'admin', '系统管理员', '127.0.0.1', null, '2010-11-17 13:42:28', 'http://localhost/example/logger/list/1.htm', '日志管理', '查看列表');
INSERT INTO `t_logger_info` VALUES ('16', 'admin', '系统管理员', '127.0.0.1', null, '2010-11-17 13:50:38', 'http://localhost//example/logger/sqllist/1.htm', '日志管理', '查看列表');
INSERT INTO `t_logger_info` VALUES ('20', 'admin', '系统管理员', '127.0.0.1', null, '2010-11-17 13:49:38', 'http://localhost/example/logger/list/1.htm', '日志管理', '查看列表');
INSERT INTO `t_logger_info` VALUES ('21', 'admin', '系统管理员', '127.0.0.1', null, '2010-11-17 13:51:00', 'http://localhost/example/logger/sqllist/1.htm', '日志管理', '查看列表');
INSERT INTO `t_logger_info` VALUES ('22', 'admin', '系统管理员', '127.0.0.1', null, '2010-11-17 13:50:46', 'http://localhost//example/logger/sqllist/1.htm', '日志管理', '查看列表');
INSERT INTO `t_logger_info` VALUES ('27', 'admin', '系统管理员', '127.0.0.1', null, '2010-11-17 13:51:04', 'http://localhost/example/logger/sqllist/1.htm', '日志管理', '查看列表');

-- ----------------------------
-- Table structure for `t_logger_something`
-- ----------------------------
DROP TABLE IF EXISTS `t_logger_something`;
CREATE TABLE `t_logger_something` (
  `keyword` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `enabled` int(11) DEFAULT NULL,
  PRIMARY KEY (`keyword`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_logger_something
-- ----------------------------
INSERT INTO `t_logger_something` VALUES ('inputSomething', '获取日志行为匹配信息', '1');
INSERT INTO `t_logger_something` VALUES ('list', '查看列表', '1');

-- ----------------------------
-- Table structure for `t_logger_somewhere`
-- ----------------------------
DROP TABLE IF EXISTS `t_logger_somewhere`;
CREATE TABLE `t_logger_somewhere` (
  `keyword` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `enabled` int(11) DEFAULT NULL,
  PRIMARY KEY (`keyword`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_logger_somewhere
-- ----------------------------
INSERT INTO `t_logger_somewhere` VALUES ('/example/logger', '日志管理', '1');

-- ----------------------------
-- Table structure for `t_logger_sql`
-- ----------------------------
DROP TABLE IF EXISTS `t_logger_sql`;
CREATE TABLE `t_logger_sql` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `log_date` datetime DEFAULT NULL,
  `sql_value` varchar(2000) DEFAULT NULL,
  `sql_type` varchar(10) DEFAULT NULL,
  `call_details` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_logger_sql
-- ----------------------------
INSERT INTO `t_logger_sql` VALUES ('1', '2010-11-17 15:12:59', 'DELETE FROM t_logger_info WHERE id=\'26\'', 'DELETE', 'admin(127.0.0.1)');
INSERT INTO `t_logger_sql` VALUES ('2', '2010-11-17 15:13:01', 'DELETE FROM t_logger_info WHERE id=\'25\'', 'DELETE', 'admin(127.0.0.1)');
INSERT INTO `t_logger_sql` VALUES ('3', '2010-11-18 10:32:00', 'DELETE FROM t_logger_info WHERE id=\'19\'', 'DELETE', 'admin(127.0.0.1)');

-- ----------------------------
-- Table structure for `t_news_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_news_info`;
CREATE TABLE `t_news_info` (
  `id` int(11) NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `auth` varchar(50) DEFAULT NULL,
  `context` longtext,
  `enabled` int(11) DEFAULT NULL,
  `durability` datetime DEFAULT NULL,
  `publish_date` datetime DEFAULT NULL,
  `insert_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `operator_name` varchar(50) DEFAULT NULL,
  `operator_ip` varchar(50) DEFAULT NULL,
  `business_code` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_news_info
-- ----------------------------

-- ----------------------------
-- Table structure for `t_organ`
-- ----------------------------
DROP TABLE IF EXISTS `t_organ`;
CREATE TABLE `t_organ` (
  `id` int(11) NOT NULL,
  `organ_code` varchar(20) NOT NULL,
  `organ_name` varchar(100) NOT NULL,
  `parent_code` varchar(20) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `service_number` varchar(20) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `fax` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `insert_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `operator_name` varchar(50) DEFAULT NULL,
  `operator_ip` varchar(50) DEFAULT NULL,
  `business_code` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_code` (`organ_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_organ
-- ----------------------------
INSERT INTO `t_organ` VALUES ('1', 'NCL', '新华保险公司总部', null, 'NCL', null, '0', null, null, null, null, '2010-10-28 18:09:27', '2010-10-28 18:09:27', '', '', 'ncl');
INSERT INTO `t_organ` VALUES ('501', 'LN', '辽宁省新华保险分公司', 'NCL', 'NCL,LN', null, '0', null, null, null, null, '2010-10-28 18:17:14', '2010-10-28 18:17:14', '', '', 'ncl');
INSERT INTO `t_organ` VALUES ('1001', 'SY', '沈阳市新华保险支公司', 'LN', 'NCL,LN,SY', null, '0', null, null, null, null, '2010-10-29 11:29:26', '2010-10-29 11:29:26', '', '', 'ncl');
INSERT INTO `t_organ` VALUES ('1501', 'FJ', '福建省新华保险分公司', 'NCL', 'NCL,FJ', null, '0', null, null, null, null, '2010-10-29 11:33:19', '2010-10-29 11:33:19', '', '', 'ncl');
INSERT INTO `t_organ` VALUES ('2501', 'FZ', '福州市新华保险支公司', 'FJ', 'NCL,FJ,FZ', null, '0', null, null, null, null, '2010-10-29 11:34:32', '2010-10-29 11:34:32', '', '', 'ncl');
INSERT INTO `t_organ` VALUES ('3001', 'DL', '大连市新华保险支公司', 'LN', 'NCL,LN,DL', null, '0', null, null, null, null, '2010-10-29 11:35:53', '2010-10-29 11:35:53', '', '', 'ncl');
INSERT INTO `t_organ` VALUES ('3501', 'AS', '鞍山市新华保险支公司', 'LN', 'NCL,LN,AS', null, '0', null, null, null, null, '2010-10-29 11:36:14', '2010-10-29 11:36:14', '', '', 'ncl');
INSERT INTO `t_organ` VALUES ('4001', 'HLD', '葫芦岛市新华保险支公司', 'LN', 'NCL,LN,HLD', null, '0', null, null, null, null, '2010-10-29 11:36:36', '2010-10-29 11:36:36', '', '', 'ncl');
INSERT INTO `t_organ` VALUES ('4501', 'JS', '江苏省新华保险分公司', 'NCL', 'NCL,JS', '123', '0', '江苏省新华保险分公司的地方', '111111111', '222555487', 'email@222555487.com', '2010-10-29 16:22:28', '2010-10-29 16:35:47', 'admin', '127.0.0.1', 'ncl');
INSERT INTO `t_organ` VALUES ('5501', 'NJ', '南京市新华保险支公司', 'JS', 'NCL,JS,NJ', '3234', '0', '南京市新华保险支公司的地址', '333333333', '333333333', '3333@12.com', '2010-10-29 16:58:32', '2010-10-29 16:58:32', 'admin', '127.0.0.1', 'ncl');

-- ----------------------------
-- Table structure for `t_system_id`
-- ----------------------------
DROP TABLE IF EXISTS `t_system_id`;
CREATE TABLE `t_system_id` (
  `table_name` varchar(50) NOT NULL,
  `id_value` int(11) NOT NULL,
  PRIMARY KEY (`table_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_system_id
-- ----------------------------
INSERT INTO `t_system_id` VALUES ('t_news_info', '1000');
INSERT INTO `t_system_id` VALUES ('t_system_resource_info', '1000');
INSERT INTO `t_system_id` VALUES ('t_system_role_info', '1000');
INSERT INTO `t_system_id` VALUES ('t_system_user_info', '1500');
INSERT INTO `t_system_id` VALUES ('t_task_info', '2500');
INSERT INTO `t_system_id` VALUES ('t_task_run_history', '1000');

-- ----------------------------
-- Table structure for `t_system_resource_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_system_resource_info`;
CREATE TABLE `t_system_resource_info` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `path` varchar(200) NOT NULL,
  `cnname` varchar(50) DEFAULT NULL,
  `insert_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `operator_name` varchar(50) DEFAULT NULL,
  `operator_ip` varchar(50) DEFAULT NULL,
  `business_code` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `path` (`path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_system_resource_info
-- ----------------------------
INSERT INTO `t_system_resource_info` VALUES ('1', 'RES_EXAMPLE_ALL', '/example/**', '所有资源', null, null, null, null, null);

-- ----------------------------
-- Table structure for `t_system_role_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_system_role_info`;
CREATE TABLE `t_system_role_info` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `cnname` varchar(50) DEFAULT NULL,
  `insert_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `operator_name` varchar(50) DEFAULT NULL,
  `operator_ip` varchar(50) DEFAULT NULL,
  `business_code` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_system_role_info
-- ----------------------------
INSERT INTO `t_system_role_info` VALUES ('1', 'ADMIN', '管理员', '2010-11-16 17:23:48', '2010-11-16 17:23:48', '', '', 'spring');

-- ----------------------------
-- Table structure for `t_system_role_resource`
-- ----------------------------
DROP TABLE IF EXISTS `t_system_role_resource`;
CREATE TABLE `t_system_role_resource` (
  `role_id` int(11) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_system_role_resource
-- ----------------------------
INSERT INTO `t_system_role_resource` VALUES ('1', '1');

-- ----------------------------
-- Table structure for `t_system_user_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_system_user_info`;
CREATE TABLE `t_system_user_info` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `cnname` varchar(50) DEFAULT NULL,
  `pyname` varchar(50) DEFAULT NULL,
  `organ_code` varchar(20) DEFAULT NULL,
  `last_login_date` datetime DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `enabled` int(11) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `account_expired` int(11) DEFAULT NULL,
  `account_locked` int(11) DEFAULT NULL,
  `credentials_expired` int(11) DEFAULT NULL,
  `insert_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `operator_name` varchar(50) DEFAULT NULL,
  `operator_ip` varchar(50) DEFAULT NULL,
  `business_code` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_system_user_info
-- ----------------------------
INSERT INTO `t_system_user_info` VALUES ('1', 'admin', 'ceb4f32325eda6142bd65215f4c0f371', '系统管理员', null, null, '2011-01-20 15:20:35', 'admin@ow.com', '1', null, '1', '1', '1', '2010-11-16 17:23:48', '2010-11-16 17:23:48', '', '', 'spring');
INSERT INTO `t_system_user_info` VALUES ('501', 'caoyang', '8caddd773172975a1e093dac8c18e249', '曹阳', 'caoyang', null, '2011-01-07 17:20:40', 'caoyang@126.com', '1', null, '1', '1', '1', '2010-11-19 14:41:40', '2010-11-19 14:41:40', 'admin', '127.0.0.1', 'spring');

-- ----------------------------
-- Table structure for `t_system_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_system_user_role`;
CREATE TABLE `t_system_user_role` (
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_system_user_role
-- ----------------------------
INSERT INTO `t_system_user_role` VALUES ('1', '1');
INSERT INTO `t_system_user_role` VALUES ('501', '1');

-- ----------------------------
-- Table structure for `t_task_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_task_info`;
CREATE TABLE `t_task_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(50) NOT NULL,
  `next_run_time` datetime DEFAULT NULL,
  `run_unit` int(11) DEFAULT NULL,
  `enabled` int(11) DEFAULT NULL,
  `instance_name` varchar(50) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `insert_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `operator_name` varchar(50) DEFAULT NULL,
  `operator_ip` varchar(50) DEFAULT NULL,
  `business_code` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_task_info
-- ----------------------------

-- ----------------------------
-- Table structure for `t_task_run_history`
-- ----------------------------
DROP TABLE IF EXISTS `t_task_run_history`;
CREATE TABLE `t_task_run_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) NOT NULL,
  `run_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `run_state` int(11) DEFAULT NULL,
  `run_type` int(11) DEFAULT NULL,
  `error_info` varchar(1000) DEFAULT NULL,
  `insert_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `operator_name` varchar(50) DEFAULT NULL,
  `operator_ip` varchar(50) DEFAULT NULL,
  `business_code` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_task_run_history
-- ----------------------------

-- ----------------------------
-- Table structure for `t_template_data`
-- ----------------------------
DROP TABLE IF EXISTS `t_template_data`;
CREATE TABLE `t_template_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data_name` varchar(50) NOT NULL,
  `sql_value` varchar(1000) NOT NULL,
  `is_unique_result` int(11) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `template_id` int(11) NOT NULL,
  `insert_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `operator_name` varchar(50) DEFAULT NULL,
  `operator_ip` varchar(50) DEFAULT NULL,
  `business_code` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_template_data
-- ----------------------------

-- ----------------------------
-- Table structure for `t_template_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_template_info`;
CREATE TABLE `t_template_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `template_path` varchar(100) NOT NULL,
  `html_path` varchar(100) NOT NULL,
  `last_date` datetime DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `insert_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `operator_name` varchar(50) DEFAULT NULL,
  `operator_ip` varchar(50) DEFAULT NULL,
  `business_code` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_template_info
-- ----------------------------
