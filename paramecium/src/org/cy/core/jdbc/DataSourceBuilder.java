package org.cy.core.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;
/**
 * 功 能 描 述:<br>
 * 默认数据源的链接信息构建器
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-3-30下午02:15:28
 * <br>项 目 信 息:paramecium:org.cy.core.jdbc.DataSourceBuilder.java
 */
public abstract class DataSourceBuilder {
	private static Logger logger = Logger.getLogger(DataSourceBuilder.class.getName());
	static Boolean autoCommit = true;
	static PrintWriter printWriter;
	static Integer loginTimeout = 5;
	static String driverClassName;
	static String url;
	static String username;
	static String password;
	
	static{
		Properties properties = new Properties();
		InputStream inputStream = System.class.getResourceAsStream("/config.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			logger.info("config.properties not fund!");
		}
		String autoCommitStr = properties.getProperty("autoCommit");
		autoCommit = autoCommitStr != null ? Boolean.valueOf(autoCommitStr) : true;
		String loginTimeoutStr = properties.getProperty("loginTimeout");
		loginTimeout = loginTimeoutStr != null ? Integer.valueOf(loginTimeoutStr) : 5;
		driverClassName = properties.getProperty("driverClassName");
		url = properties.getProperty("url");
		username = properties.getProperty("username");
		password = properties.getProperty("password");
		if(password==null){
			password = "";
		}
		if(driverClassName!=null){
			try {
				Class.forName(driverClassName);
			} catch (ClassNotFoundException e) {
				logger.info(driverClassName+" not fund!");
			}
		}
		JdbcTemplateFactory.dbType = properties.getProperty("dbType");
	}
	
	public Connection createConnection(){
		Connection connection = null;
		DriverManager.setLoginTimeout(loginTimeout);
		if(url!=null&&username!=null){
			try {
				connection = DriverManager.getConnection(url, username, password);
				connection.setAutoCommit(autoCommit);
			} catch (SQLException e) {
				logger.info("connection not create!");
			}
		}
		return connection;
	}
	
}
