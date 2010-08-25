/*
MySQL Data Transfer
Source Host: 127.0.0.1
Source Database: spring
Target Host: 127.0.0.1
Target Database: spring
Date: 2010/1/20 15:49:34
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for t_logger_info
-- ----------------------------
CREATE TABLE `t_logger_info` (
  `id` int(11) NOT NULL auto_increment,
  `log_info` varchar(200) default NULL,
  `log_date` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_news_info
-- ----------------------------
CREATE TABLE `t_news_info` (
  `id` int(11) NOT NULL auto_increment,
  `auth` varchar(20) NOT NULL,
  `context` text NOT NULL,
  `durability` datetime default NULL,
  `enabled` int(1) default NULL,
  `publish_date` datetime default NULL,
  `title` varchar(50) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `title` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_system_resource_info
-- ----------------------------
CREATE TABLE `t_system_resource_info` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(50) NOT NULL,
  `path` varchar(200) NOT NULL,
  `cnname` varchar(50) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `path` (`path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_system_role_info
-- ----------------------------
CREATE TABLE `t_system_role_info` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(50) NOT NULL,
  `cnname` varchar(50) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_system_role_resource
-- ----------------------------
CREATE TABLE `t_system_role_resource` (
  `role_id` int(11) default NULL,
  `resource_id` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_system_user_info
-- ----------------------------
CREATE TABLE `t_system_user_info` (
  `id` int(11) NOT NULL auto_increment,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `cnname` varchar(50) default NULL,
  `email` varchar(50) default NULL,
  `enabled` int(11) default NULL,
  `account_expired` int(11) default NULL,
  `account_locked` int(11) default NULL,
  `credentials_expired` int(11) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_system_user_role
-- ----------------------------
CREATE TABLE `t_system_user_role` (
  `user_id` int(11) default NULL,
  `role_id` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_task_info
-- ----------------------------
CREATE TABLE `t_task_info` (
  `id` int(11) NOT NULL auto_increment,
  `task_name` varchar(50) NOT NULL,
  `next_run_time` datetime default NULL,
  `run_unit` int(11) default NULL,
  `enabled` int(11) default NULL,
  `instance_name` varchar(50) NOT NULL,
  `description` varchar(100) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_task_run_history
-- ----------------------------
CREATE TABLE `t_task_run_history` (
  `id` int(11) NOT NULL auto_increment,
  `task_id` int(11) NOT NULL,
  `run_time` datetime default NULL,
  `run_state` int(11) default NULL,
  `run_type` int(11) default NULL,
  `error_info` varchar(1000) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_template_data
-- ----------------------------
CREATE TABLE `t_template_data` (
  `id` int(11) NOT NULL auto_increment,
  `data_name` varchar(50) NOT NULL,
  `sql_value` varchar(1000) NOT NULL,
  `is_unique_result` int(11) default NULL,
  `description` varchar(100) default NULL,
  `template_id` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_template_info
-- ----------------------------
CREATE TABLE `t_template_info` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(50) NOT NULL,
  `template_path` varchar(100) NOT NULL,
  `html_path` varchar(100) NOT NULL,
  `last_date` datetime default NULL,
  `description` varchar(100) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `t_logger_info` VALUES ('2', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.UserController类list方法', '2009-12-03 15:24:23');
INSERT INTO `t_logger_info` VALUES ('3', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.template.TemplateController类initBinder方法', '2009-12-03 15:24:21');
INSERT INTO `t_logger_info` VALUES ('7', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.ResourceController类list方法', '2009-12-03 15:24:22');
INSERT INTO `t_logger_info` VALUES ('13', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.ResourceController类list方法', '2009-12-03 15:50:02');
INSERT INTO `t_logger_info` VALUES ('14', '登录名:caoyang 姓名:曹阳 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.logger.LoggerController类list方法', '2009-12-03 15:51:43');
INSERT INTO `t_logger_info` VALUES ('15', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.UserController类list方法', '2009-12-03 15:50:43');
INSERT INTO `t_logger_info` VALUES ('16', '登录名:caoyang 姓名:曹阳 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.RoleController类list方法', '2009-12-03 15:51:09');
INSERT INTO `t_logger_info` VALUES ('17', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.RoleController类save方法', '2009-12-03 15:50:11');
INSERT INTO `t_logger_info` VALUES ('18', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.ResourceController类list方法', '2009-12-03 15:49:55');
INSERT INTO `t_logger_info` VALUES ('19', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.ResourceController类list方法', '2009-12-03 15:49:52');
INSERT INTO `t_logger_info` VALUES ('20', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.IndexController类index方法', '2009-12-03 15:50:58');
INSERT INTO `t_logger_info` VALUES ('21', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.ResourceController类list方法', '2009-12-03 15:49:19');
INSERT INTO `t_logger_info` VALUES ('23', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.UserController类save方法', '2009-12-03 15:50:53');
INSERT INTO `t_logger_info` VALUES ('26', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.RoleController类input方法', '2009-12-03 15:50:21');
INSERT INTO `t_logger_info` VALUES ('27', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.RoleController类list方法', '2009-12-03 15:50:40');
INSERT INTO `t_logger_info` VALUES ('28', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.UserController类list方法', '2009-12-03 15:50:53');
INSERT INTO `t_logger_info` VALUES ('29', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.ResourceController类list方法', '2009-12-03 15:50:00');
INSERT INTO `t_logger_info` VALUES ('30', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.RoleController类list方法', '2009-12-03 15:50:04');
INSERT INTO `t_logger_info` VALUES ('31', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.UserController类save方法', '2009-12-03 15:50:53');
INSERT INTO `t_logger_info` VALUES ('32', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.UserController类input方法', '2009-12-03 15:50:47');
INSERT INTO `t_logger_info` VALUES ('33', '登录名:caoyang 姓名:曹阳 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.logger.LoggerController类list方法', '2009-12-03 15:51:43');
INSERT INTO `t_logger_info` VALUES ('34', '登录名:caoyang 姓名:曹阳 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.RoleController类list方法', '2009-12-03 15:51:09');
INSERT INTO `t_logger_info` VALUES ('35', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.RoleController类input方法', '2009-12-03 15:50:09');
INSERT INTO `t_logger_info` VALUES ('36', '登录名:caoyang 姓名:曹阳 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.IndexController类index方法', '2009-12-03 15:51:05');
INSERT INTO `t_logger_info` VALUES ('37', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.RoleController类save方法', '2009-12-03 15:50:40');
INSERT INTO `t_logger_info` VALUES ('38', '登录名:caoyang 姓名:曹阳 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.logger.LoggerController类list方法', '2009-12-03 15:51:33');
INSERT INTO `t_logger_info` VALUES ('39', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.RoleController类input方法', '2009-12-03 15:50:14');
INSERT INTO `t_logger_info` VALUES ('40', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.RoleController类input方法', '2009-12-03 15:50:27');
INSERT INTO `t_logger_info` VALUES ('41', '登录名:caoyang 姓名:曹阳 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.UserController类list方法', '2009-12-03 15:51:08');
INSERT INTO `t_logger_info` VALUES ('42', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.RoleController类list方法', '2009-12-03 15:50:08');
INSERT INTO `t_logger_info` VALUES ('43', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.IndexController类index方法', '2009-12-03 15:49:16');
INSERT INTO `t_logger_info` VALUES ('44', '登录名:caoyang 姓名:曹阳 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.logger.LoggerController类list方法', '2009-12-03 15:51:11');
INSERT INTO `t_logger_info` VALUES ('46', '登录名:tanchang 姓名:谭畅 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.report.ReportController类print方法', '2009-12-03 15:52:55');
INSERT INTO `t_logger_info` VALUES ('47', '登录名:赵磊 姓名:赵磊 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.report.ReportController类print方法', '2009-12-03 15:52:33');
INSERT INTO `t_logger_info` VALUES ('48', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.logger.LoggerController类list方法', '2009-12-03 15:53:09');
INSERT INTO `t_logger_info` VALUES ('49', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.template.TemplateController类list方法', '2009-12-03 15:53:07');
INSERT INTO `t_logger_info` VALUES ('50', '登录名:tanchang 姓名:谭畅 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.report.ReportController类line方法', '2009-12-03 15:52:54');
INSERT INTO `t_logger_info` VALUES ('51', '登录名:tanchang 姓名:谭畅 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.report.ReportController类pie方法', '2009-12-03 15:52:51');
INSERT INTO `t_logger_info` VALUES ('52', '登录名:tanchang 姓名:谭畅 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.search.NewsController类list方法', '2009-12-03 15:52:50');
INSERT INTO `t_logger_info` VALUES ('53', '登录名:caoyang 姓名:曹阳 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.logger.LoggerController类list方法', '2009-12-03 15:52:01');
INSERT INTO `t_logger_info` VALUES ('54', '登录名:tanchang 姓名:谭畅 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.report.ReportController类series方法', '2009-12-03 15:52:55');
INSERT INTO `t_logger_info` VALUES ('55', '登录名:赵磊 姓名:赵磊 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.report.ReportController类pie方法', '2009-12-03 15:52:33');
INSERT INTO `t_logger_info` VALUES ('56', '登录名:赵磊 姓名:赵磊 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.search.NewsController类list方法', '2009-12-03 15:52:29');
INSERT INTO `t_logger_info` VALUES ('57', '登录名:caoyang 姓名:曹阳 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.logger.LoggerController类list方法', '2009-12-03 15:52:01');
INSERT INTO `t_logger_info` VALUES ('58', '登录名:tanchang 姓名:谭畅 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.IndexController类index方法', '2009-12-03 15:52:43');
INSERT INTO `t_logger_info` VALUES ('59', '登录名:caoyang 姓名:曹阳 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.template.TemplateController类initBinder方法', '2009-12-03 15:52:09');
INSERT INTO `t_logger_info` VALUES ('60', '登录名:caoyang 姓名:曹阳 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.template.TemplateController类list方法', '2009-12-03 15:52:09');
INSERT INTO `t_logger_info` VALUES ('61', '登录名:caoyang 姓名:曹阳 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.template.TemplateController类initBinder方法', '2009-12-03 15:52:09');
INSERT INTO `t_logger_info` VALUES ('62', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.template.TemplateController类initBinder方法', '2009-12-03 15:53:07');
INSERT INTO `t_logger_info` VALUES ('63', '登录名:赵磊 姓名:赵磊 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.IndexController类index方法', '2009-12-03 15:52:24');
INSERT INTO `t_logger_info` VALUES ('64', '登录名:caoyang 姓名:曹阳 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.logger.LoggerController类list方法', '2009-12-03 15:51:53');
INSERT INTO `t_logger_info` VALUES ('65', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.IndexController类index方法', '2009-12-03 15:53:01');
INSERT INTO `t_logger_info` VALUES ('66', '登录名:tanchang 姓名:谭畅 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.report.ReportController类print方法', '2009-12-03 15:52:52');
INSERT INTO `t_logger_info` VALUES ('67', '登录名:tanchang 姓名:谭畅 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.report.ReportController类print方法', '2009-12-03 15:52:54');
INSERT INTO `t_logger_info` VALUES ('68', '登录名:caoyang 姓名:曹阳 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.logger.LoggerController类list方法', '2009-12-03 15:51:57');
INSERT INTO `t_logger_info` VALUES ('69', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.logger.LoggerController类list方法', '2009-12-03 15:53:10');
INSERT INTO `t_logger_info` VALUES ('70', '登录名:caoyang 姓名:曹阳 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.security.ResourceController类list方法', '2009-12-03 15:52:10');
INSERT INTO `t_logger_info` VALUES ('71', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.logger.LoggerController类list方法', '2009-12-03 15:53:19');
INSERT INTO `t_logger_info` VALUES ('72', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.logger.LoggerController类list方法', '2009-12-03 15:53:27');
INSERT INTO `t_logger_info` VALUES ('73', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.logger.LoggerController类list方法', '2009-12-03 15:53:48');
INSERT INTO `t_logger_info` VALUES ('74', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.logger.LoggerController类list方法', '2009-12-03 15:53:43');
INSERT INTO `t_logger_info` VALUES ('76', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.logger.LoggerController类list方法', '2009-12-03 15:53:12');
INSERT INTO `t_logger_info` VALUES ('77', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.logger.LoggerController类list方法', '2009-12-03 15:53:40');
INSERT INTO `t_logger_info` VALUES ('78', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.logger.LoggerController类list方法', '2009-12-03 15:53:48');
INSERT INTO `t_logger_info` VALUES ('79', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.task.TaskController类list方法', '2009-12-03 15:53:11');
INSERT INTO `t_logger_info` VALUES ('81', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.logger.LoggerController类list方法', '2009-12-03 15:53:22');
INSERT INTO `t_logger_info` VALUES ('82', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.logger.LoggerController类list方法', '2009-12-03 15:55:11');
INSERT INTO `t_logger_info` VALUES ('84', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.logger.LoggerController类list方法', '2009-12-03 15:55:53');
INSERT INTO `t_logger_info` VALUES ('85', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.logger.LoggerController类list方法', '2009-12-03 15:53:51');
INSERT INTO `t_logger_info` VALUES ('86', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:com.caoyang.example.web.example.logger.LoggerController类list方法', '2009-12-03 15:55:53');
INSERT INTO `t_logger_info` VALUES ('88', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:LoggerController类list方法', '2009-12-04 14:24:40');
INSERT INTO `t_logger_info` VALUES ('90', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:LoggerController类list方法', '2009-12-04 14:24:07');
INSERT INTO `t_logger_info` VALUES ('91', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:LoggerController类list方法', '2009-12-04 14:24:12');
INSERT INTO `t_logger_info` VALUES ('94', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:LoggerController类list方法', '2009-12-04 14:25:25');
INSERT INTO `t_logger_info` VALUES ('95', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:LoggerController类list方法', '2009-12-04 14:25:21');
INSERT INTO `t_logger_info` VALUES ('96', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:TemplateController类list方法', '2009-12-04 14:25:28');
INSERT INTO `t_logger_info` VALUES ('97', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:LoggerController类list方法', '2009-12-04 14:25:34');
INSERT INTO `t_logger_info` VALUES ('98', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:NewsController类list方法', '2009-12-04 14:25:30');
INSERT INTO `t_logger_info` VALUES ('99', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:LoggerController类list方法', '2009-12-04 14:25:31');
INSERT INTO `t_logger_info` VALUES ('100', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:TaskController类list方法', '2009-12-04 14:25:28');
INSERT INTO `t_logger_info` VALUES ('101', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:LoggerController类list方法', '2009-12-04 14:25:35');
INSERT INTO `t_logger_info` VALUES ('102', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:LoggerController类save方法', '2009-12-04 14:25:58');
INSERT INTO `t_logger_info` VALUES ('103', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:NewsController类list方法', '2009-12-04 14:25:35');
INSERT INTO `t_logger_info` VALUES ('104', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:LoggerController类input方法', '2009-12-04 14:25:51');
INSERT INTO `t_logger_info` VALUES ('105', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:LoggerController类list方法', '2009-12-04 14:28:39');
INSERT INTO `t_logger_info` VALUES ('106', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:LoggerController类list方法', '2009-12-04 14:28:33');
INSERT INTO `t_logger_info` VALUES ('107', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:LoggerController类list方法', '2009-12-04 14:28:31');
INSERT INTO `t_logger_info` VALUES ('108', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:LoggerController类list方法', '2009-12-04 14:28:27');
INSERT INTO `t_logger_info` VALUES ('109', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:LoggerController类list方法', '2009-12-04 14:28:56');
INSERT INTO `t_logger_info` VALUES ('110', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:LoggerController类list方法', '2009-12-04 14:28:56');
INSERT INTO `t_logger_info` VALUES ('111', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:LoggerController类list方法', '2009-12-04 14:28:52');
INSERT INTO `t_logger_info` VALUES ('112', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:LoggerController类list方法', '2009-12-04 14:28:44');
INSERT INTO `t_logger_info` VALUES ('113', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:LoggerController类list方法', '2009-12-04 14:28:58');
INSERT INTO `t_logger_info` VALUES ('114', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:LoggerController类list方法', '2009-12-04 14:28:48');
INSERT INTO `t_logger_info` VALUES ('115', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:LoggerController类list方法', '2009-12-04 14:28:41');
INSERT INTO `t_logger_info` VALUES ('116', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:LoggerController类list方法', '2009-12-04 14:29:22');
INSERT INTO `t_logger_info` VALUES ('117', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:LoggerController类list方法', '2009-12-04 14:28:44');
INSERT INTO `t_logger_info` VALUES ('119', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:LoggerController类list方法', '2009-12-04 14:29:24');
INSERT INTO `t_logger_info` VALUES ('120', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:LoggerController类list方法', '2009-12-04 14:29:28');
INSERT INTO `t_logger_info` VALUES ('121', '登录名:admin 姓名:系统管理员 IP:192.168.1.199 访问了:LoggerController类list方法', '2009-12-04 14:29:24');
INSERT INTO `t_logger_info` VALUES ('125', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:TemplateController类list方法', '2009-12-04 20:16:08');
INSERT INTO `t_logger_info` VALUES ('127', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:TemplateController类initBinder方法', '2009-12-04 20:16:08');
INSERT INTO `t_logger_info` VALUES ('128', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:LoggerController类list方法', '2009-12-04 20:16:10');
INSERT INTO `t_logger_info` VALUES ('129', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:LoggerController类list方法', '2009-12-04 20:16:06');
INSERT INTO `t_logger_info` VALUES ('130', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:ResourceController类list方法', '2009-12-04 20:16:09');
INSERT INTO `t_logger_info` VALUES ('131', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:TemplateController类initBinder方法', '2009-12-04 20:16:08');
INSERT INTO `t_logger_info` VALUES ('132', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:http://localhost/example/logger/list/1.htm', '2009-12-15 14:57:53');
INSERT INTO `t_logger_info` VALUES ('133', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:http://localhost/example/index.htm', '2009-12-15 14:57:09');
INSERT INTO `t_logger_info` VALUES ('135', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:http://localhost//example/logger/save.htm', '2009-12-15 14:58:04');
INSERT INTO `t_logger_info` VALUES ('137', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:http://localhost/example/logger/list/1.htm', '2009-12-15 14:59:33');
INSERT INTO `t_logger_info` VALUES ('139', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:http://localhost//example/logger/load.htm', '2009-12-15 14:59:50');
INSERT INTO `t_logger_info` VALUES ('141', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:http://localhost//example/logger/delete/1/134.htm', '2009-12-17 13:42:39');
INSERT INTO `t_logger_info` VALUES ('142', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:http://localhost//example/logger/delete/25/1.htm', '2009-12-17 13:42:48');
INSERT INTO `t_logger_info` VALUES ('144', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:http://localhost//example/logger/delete/1/140.htm', '2009-12-17 13:42:29');
INSERT INTO `t_logger_info` VALUES ('145', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:http://localhost//example/logger/delete/25/4.htm', '2009-12-17 13:42:48');
INSERT INTO `t_logger_info` VALUES ('148', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:http://localhost//example/template/delete/1/7.htm', '2009-12-17 14:38:27');
INSERT INTO `t_logger_info` VALUES ('149', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:http://localhost//example/logger/delete/25/8.htm', '2009-12-17 21:56:38');
INSERT INTO `t_logger_info` VALUES ('150', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:http://localhost//example/logger/delete/25/11.htm', '2009-12-17 21:56:39');
INSERT INTO `t_logger_info` VALUES ('151', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:http://localhost//example/task/save.htm', '2009-12-17 21:57:55');
INSERT INTO `t_logger_info` VALUES ('152', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:http://localhost//example/task/delete/6.htm', '2009-12-17 21:59:28');
INSERT INTO `t_logger_info` VALUES ('153', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:http://localhost//example/task/save.htm', '2009-12-17 21:58:36');
INSERT INTO `t_logger_info` VALUES ('154', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:http://localhost//example/task/save.htm', '2009-12-17 21:58:07');
INSERT INTO `t_logger_info` VALUES ('155', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:http://localhost//example/task/6/run_history/1/delete/185.htm', '2009-12-17 21:59:25');
INSERT INTO `t_logger_info` VALUES ('156', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:http://localhost//example/logger/delete/25/12.htm', '2009-12-17 21:55:49');
INSERT INTO `t_logger_info` VALUES ('157', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:http://localhost/example/task/list.htm', '2009-12-18 10:45:52');
INSERT INTO `t_logger_info` VALUES ('159', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:http://localhost/example/logger/list/1.htm', '2009-12-18 10:45:50');
INSERT INTO `t_logger_info` VALUES ('160', '登录名:admin 姓名:系统管理员 IP:0:0:0:0:0:0:0:1 访问了:http://localhost/example/logger/list/1.htm', '2009-12-18 10:46:02');
INSERT INTO `t_news_info` VALUES ('1', '曹阳', '哈哈哈，我是快乐的小玩家，真的很快乐。', '2010-01-27 00:00:00', '1', '2010-01-20 00:00:00', '第一个测试');
INSERT INTO `t_system_resource_info` VALUES ('1', 'RES_EXAMPLE_USER', '/example/user/**', '账户管理');
INSERT INTO `t_system_resource_info` VALUES ('2', 'RES_EXAMPLE_ROLE', '/example/role/**', '角色管理');
INSERT INTO `t_system_resource_info` VALUES ('3', 'RES_EXAMPLE_RESOURCE', '/example/resource/**', '权限管理');
INSERT INTO `t_system_resource_info` VALUES ('4', 'RES_EXAMPLE_TEMPLATE', '/example/template/**', '模板管理');
INSERT INTO `t_system_resource_info` VALUES ('5', 'RES_EXAMPLE_INDEX', '/example/index.htm', '演示首页');
INSERT INTO `t_system_resource_info` VALUES ('6', 'RES_EXAMPLE_TASK', '/example/task/**', '任务调度');
INSERT INTO `t_system_resource_info` VALUES ('7', 'RES_EXAMPLE_REPORT', '/example/report/**', '报表统计');
INSERT INTO `t_system_resource_info` VALUES ('8', 'RES_EXAMPLE_SEARCH', '/example/search/**', '搜索引擎');
INSERT INTO `t_system_resource_info` VALUES ('9', 'RES_EXAMPLE_LOGGER', '/example/logger/**', '日志管理');
INSERT INTO `t_system_role_info` VALUES ('1', 'ADMIN', '管理员');
INSERT INTO `t_system_role_info` VALUES ('2', 'TEMPLATOR', '模板制作专员');
INSERT INTO `t_system_role_info` VALUES ('3', 'LOOKER', '预览系统');
INSERT INTO `t_system_role_info` VALUES ('4', 'SEO', '搜索引擎优化');
INSERT INTO `t_system_role_resource` VALUES ('2', '4');
INSERT INTO `t_system_role_resource` VALUES ('2', '5');
INSERT INTO `t_system_role_resource` VALUES ('2', '8');
INSERT INTO `t_system_role_resource` VALUES ('3', '5');
INSERT INTO `t_system_role_resource` VALUES ('3', '7');
INSERT INTO `t_system_role_resource` VALUES ('3', '8');
INSERT INTO `t_system_role_resource` VALUES ('1', '1');
INSERT INTO `t_system_role_resource` VALUES ('1', '2');
INSERT INTO `t_system_role_resource` VALUES ('1', '3');
INSERT INTO `t_system_role_resource` VALUES ('1', '4');
INSERT INTO `t_system_role_resource` VALUES ('1', '5');
INSERT INTO `t_system_role_resource` VALUES ('1', '6');
INSERT INTO `t_system_role_resource` VALUES ('1', '7');
INSERT INTO `t_system_role_resource` VALUES ('1', '8');
INSERT INTO `t_system_role_resource` VALUES ('1', '9');
INSERT INTO `t_system_role_resource` VALUES ('4', '5');
INSERT INTO `t_system_role_resource` VALUES ('4', '8');
INSERT INTO `t_system_role_resource` VALUES ('4', '9');
INSERT INTO `t_system_user_info` VALUES ('1', 'admin', 'ceb4f32325eda6142bd65215f4c0f371', '系统管理员', 'admin@springexample.com', '1', '1', '1', '1');
INSERT INTO `t_system_user_info` VALUES ('2', 'user', '47a733d60998c719cf3526ae7d106d13', '模板专员', 'user@example.com', '1', '1', '1', '1');
INSERT INTO `t_system_user_info` VALUES ('3', 'caoyang', '8caddd773172975a1e093dac8c18e249', '曹阳', 'caoyang@126.com', '1', '1', '1', '1');
INSERT INTO `t_system_user_info` VALUES ('4', 'tanchang', 'ed629c177564f7199977bac371d1f714', '谭畅', 'tanchang@163.com', '1', '1', '1', '1');
INSERT INTO `t_system_user_info` VALUES ('5', 'xielei', '1980cdc901388cf43b04f0347a85f690', '谢雷', 'xielei@ow.com', '1', '1', '1', '1');
INSERT INTO `t_system_user_info` VALUES ('6', 'douzhi', 'ccba99c64767285b2f2c5bb8488030e7', '窦志', 'douzhi@ow.com', '1', '1', '1', '1');
INSERT INTO `t_system_user_info` VALUES ('7', 'zhangdi', '45d2478c44b950a487c1bbd3e3f4c38f', '张迪', 'zhangdi@ow.com', '1', '1', '1', '1');
INSERT INTO `t_system_user_info` VALUES ('8', '赵磊', 'e95a7469e2a823e561cc8fc161380d0e', '赵磊', '赵磊@1.com', '1', '1', '1', '1');
INSERT INTO `t_system_user_info` VALUES ('9', '陈涛', '75586ac60001d3375657cf17a3f73237', '陈涛', '陈涛@2.b', '1', '1', '1', '1');
INSERT INTO `t_system_user_role` VALUES ('1', '1');
INSERT INTO `t_system_user_role` VALUES ('2', '2');
INSERT INTO `t_system_user_role` VALUES ('4', '3');
INSERT INTO `t_system_user_role` VALUES ('5', '3');
INSERT INTO `t_system_user_role` VALUES ('7', '3');
INSERT INTO `t_system_user_role` VALUES ('8', '3');
INSERT INTO `t_system_user_role` VALUES ('9', '3');
INSERT INTO `t_system_user_role` VALUES ('6', '3');
INSERT INTO `t_system_user_role` VALUES ('3', '1');
INSERT INTO `t_system_user_role` VALUES ('3', '2');
INSERT INTO `t_system_user_role` VALUES ('3', '3');
INSERT INTO `t_system_user_role` VALUES ('3', '4');
INSERT INTO `t_task_info` VALUES ('3', '任务A', '2009-12-08 17:00:01', '0', '0', 'testTaskA', '任务A');
INSERT INTO `t_task_info` VALUES ('4', '任务B', '2009-11-03 17:00:01', '0', '0', 'testTaskB', '任务B');
INSERT INTO `t_task_info` VALUES ('5', '任务C', '2009-12-08 17:00:01', '0', '0', 'testTaskC', '任务C');
INSERT INTO `t_task_run_history` VALUES ('101', '5', '2009-11-04 10:37:43', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('102', '3', '2009-11-04 10:37:45', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('103', '5', '2009-11-04 17:01:16', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('104', '3', '2009-11-04 17:01:18', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('105', '5', '2009-11-05 17:02:52', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('106', '3', '2009-11-05 17:02:55', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('107', '4', '2009-11-05 17:04:50', '0', '1', '/ by zero');
INSERT INTO `t_task_run_history` VALUES ('108', '4', '2009-11-06 09:27:01', '0', '1', '/ by zero');
INSERT INTO `t_task_run_history` VALUES ('109', '4', '2009-11-06 09:27:08', '0', '1', '/ by zero');
INSERT INTO `t_task_run_history` VALUES ('110', '5', '2009-11-06 09:27:30', '1', '1', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('111', '5', '2009-11-06 09:27:31', '1', '1', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('112', '5', '2009-11-06 09:27:32', '1', '1', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('113', '5', '2009-11-06 18:15:34', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('114', '3', '2009-11-06 18:15:37', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('115', '5', '2009-11-07 17:36:12', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('116', '3', '2009-11-07 17:36:14', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('117', '5', '2009-11-08 18:23:41', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('118', '3', '2009-11-08 18:23:44', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('119', '4', '2009-11-08 20:59:38', '0', '1', '/ by zero');
INSERT INTO `t_task_run_history` VALUES ('120', '4', '2009-11-08 21:00:20', '0', '1', '/ by zero');
INSERT INTO `t_task_run_history` VALUES ('121', '4', '2009-11-09 09:28:03', '0', '1', '/ by zero');
INSERT INTO `t_task_run_history` VALUES ('122', '4', '2009-11-09 09:28:11', '0', '1', '/ by zero');
INSERT INTO `t_task_run_history` VALUES ('124', '5', '2009-11-10 09:18:30', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('125', '3', '2009-11-10 09:18:33', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('126', '4', '2009-11-10 09:29:07', '0', '1', '/ by zero');
INSERT INTO `t_task_run_history` VALUES ('127', '5', '2009-11-10 21:37:02', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('128', '3', '2009-11-10 21:37:04', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('129', '5', '2009-11-11 17:12:19', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('130', '3', '2009-11-11 17:12:22', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('131', '4', '2009-11-11 17:12:42', '0', '1', '/ by zero');
INSERT INTO `t_task_run_history` VALUES ('132', '5', '2009-11-13 09:04:34', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('133', '3', '2009-11-13 09:04:37', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('134', '5', '2009-11-18 10:49:44', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('135', '3', '2009-11-18 10:49:46', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('136', '5', '2009-11-18 10:54:32', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('137', '4', '2009-11-18 10:54:35', '0', '0', '/ by zero');
INSERT INTO `t_task_run_history` VALUES ('138', '3', '2009-11-18 10:54:37', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('139', '5', '2009-11-18 10:55:29', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('140', '3', '2009-11-18 10:55:31', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('141', '5', '2009-11-18 10:58:24', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('142', '3', '2009-11-18 10:58:26', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('143', '5', '2009-11-18 11:05:04', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('144', '3', '2009-11-18 11:05:06', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('146', '3', '2009-11-27 15:24:17', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('148', '3', '2009-11-27 15:41:55', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('149', '5', '2009-12-03 11:22:48', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('150', '3', '2009-12-03 11:22:50', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('151', '5', '2009-12-03 13:41:18', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('152', '3', '2009-12-03 13:41:20', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('153', '5', '2009-12-03 13:43:17', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('154', '3', '2009-12-03 13:43:19', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('155', '5', '2009-12-03 13:45:58', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('156', '3', '2009-12-03 13:46:01', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('157', '5', '2009-12-03 14:10:13', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('158', '3', '2009-12-03 14:10:15', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('159', '5', '2009-12-03 14:17:58', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('160', '3', '2009-12-03 14:18:00', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('161', '5', '2009-12-03 14:18:35', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('162', '3', '2009-12-03 14:18:38', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('163', '5', '2009-12-03 14:38:20', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('164', '3', '2009-12-03 14:38:23', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('165', '5', '2009-12-03 14:50:52', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('166', '3', '2009-12-03 14:50:54', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('167', '5', '2009-12-03 14:54:33', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('168', '3', '2009-12-03 14:54:36', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('169', '5', '2009-12-03 15:00:52', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('170', '3', '2009-12-03 15:00:55', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('171', '5', '2009-12-03 15:04:26', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('172', '3', '2009-12-03 15:04:28', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('173', '5', '2009-12-03 15:05:42', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('174', '3', '2009-12-03 15:05:45', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('175', '5', '2009-12-04 14:01:39', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('176', '3', '2009-12-04 14:01:41', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('177', '5', '2009-12-04 20:15:06', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('178', '3', '2009-12-04 20:15:09', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('179', '5', '2009-12-07 13:49:13', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('180', '3', '2009-12-07 13:49:16', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('181', '5', '2009-12-07 15:11:49', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('182', '3', '2009-12-07 15:11:52', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('183', '5', '2009-12-07 22:30:55', '1', '0', '执行成功');
INSERT INTO `t_task_run_history` VALUES ('184', '3', '2009-12-07 22:30:58', '1', '0', '执行成功');
INSERT INTO `t_template_data` VALUES ('-1', 'haha', 'select * from bb', '0', 'hahahahaha', '1');
INSERT INTO `t_template_data` VALUES ('3', 'users', 'select * from t_system_user_info', '0', '用户', '5');
INSERT INTO `t_template_data` VALUES ('4', 'roles', 'select * from t_system_role_info', '0', '角色数据', '6');
INSERT INTO `t_template_info` VALUES ('5', '首页', '/template/user.ftl', '/demo/index.html', '2009-11-10 09:30:58', '用户信息');
INSERT INTO `t_template_info` VALUES ('6', '角色', '/template/role.ftl', '/demo/role.html', '2009-10-27 16:04:11', '角色静态页');
