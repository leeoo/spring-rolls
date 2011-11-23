package com.demo.service;

import java.sql.Connection;
import java.sql.Statement;

import org.paramecium.jdbc.datasource.MultiDataSourceFactory;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.security.IpAddressVoter;

/**
 * 功 能 描 述:<br>
 * 优先启动
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-10-18下午02:23:20
 * <br>项 目 信 息:paramecium:com.demo.service.PriorityService.java
 */
public class PriorityService {
	
	private final static Log logger = LoggerFactory.getLogger();
	
	public void init(){
		logger.debug("应用层初始化进行中...");
		IpAddressVoter.setInclude(true);
		IpAddressVoter.put("127.0.0.1");
		try {
			createTables();
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	public static void createTables() throws Exception{
		Connection connection = MultiDataSourceFactory.getDataSource("ds1").getConnection();
		Statement st=  connection.createStatement();
		st.execute("CREATE TABLE t_security_user(id INT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(50) NOT NULL UNIQUE, password VARCHAR(50) NOT NULL, enabled bit, name VARCHAR(50))");
		logger.debug("t_security_user创建成功!");
		st.execute("CREATE TABLE t_user_role(username VARCHAR(50) NOT NULl,rolename VARCHAR(50) NOT NULL)");
		logger.debug("t_user_role创建成功!");
		st.execute("CREATE TABLE t_security_role(id INT PRIMARY KEY AUTO_INCREMENT, rolename VARCHAR(50) NOT NULL UNIQUE,name VARCHAR(50))");
		logger.debug("t_security_role创建成功!");
		st.execute("CREATE TABLE t_role_auth(rolename VARCHAR(50) NOT NULL,auth VARCHAR(255) NOT NULL)");
		logger.debug("t_role_auth创建成功!");
		st.execute("CREATE TABLE performance_test(id INT PRIMARY KEY AUTO_INCREMENT,name VARCHAR(500) NOT NULL,date DATE NOT NULL)");
		logger.debug("performance_test创建成功!");
	}
	
}
