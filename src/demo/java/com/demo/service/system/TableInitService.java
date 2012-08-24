package com.demo.service.system;

import java.sql.Connection;
import java.sql.Statement;

import org.paramecium.ioc.annotation.Service;
import org.paramecium.jdbc.datasource.MultiDataSourceFactory;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;

/**
 * 功 能 描 述:<br>
 * 对于内存表的初始化
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2012-3-20下午02:40:35
 * <br>项 目 信 息:paramecium:com.demo.service.system.TableInitService.java
 */
@Service
public class TableInitService {
	
	private final static Log logger = LoggerFactory.getLogger();
	
	public void init(){
		try {
			createTablesByH2$MySql();
			logger.debug("DEMO用H2数据库内存表创建成功!");
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	public static void createTablesByH2$MySql() throws Exception{
		Connection connection = MultiDataSourceFactory.getDataSource("ds1").getConnection();
		Statement st=  connection.createStatement();
		st.execute("CREATE TABLE t_security_user(id INT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(50) NOT NULL UNIQUE, password VARCHAR(50) NOT NULL, enabled BIT, name VARCHAR(50))");
		logger.debug("t_security_user创建成功!");
		st.execute("CREATE TABLE t_user_role(username VARCHAR(50) NOT NULl,rolename VARCHAR(50) NOT NULL)");
		logger.debug("t_user_role创建成功!");
		st.execute("CREATE TABLE t_security_role(id INT PRIMARY KEY AUTO_INCREMENT, rolename VARCHAR(50) NOT NULL UNIQUE,name VARCHAR(50))");
		logger.debug("t_security_role创建成功!");
		st.execute("CREATE TABLE t_role_auth(rolename VARCHAR(50) NOT NULL,auth VARCHAR(255) NOT NULL)");
		logger.debug("t_role_auth创建成功!");
		st.execute("CREATE TABLE performance_test(id INT PRIMARY KEY AUTO_INCREMENT,name VARCHAR(500) NOT NULL,date DATE NOT NULL)");
		logger.debug("performance_test创建成功!");
		st.execute("CREATE TABLE t_log(id INT PRIMARY KEY AUTO_INCREMENT,log VARCHAR(5000) NOT NULL,date DATETIME NOT NULL,type INT)");
		logger.debug("t_log创建成功!");
		st.execute("CREATE TABLE t_sequence(table_name VARCHAR(50) PRIMARY KEY,seq_value INT NOT NULL)");
		logger.debug("t_sequence创建成功!");
		st.close();
		MultiDataSourceFactory.getDataSource("ds1").replace(connection);
	}
	
	public static void createTablesBySqlServer() throws Exception{
		Connection connection = MultiDataSourceFactory.getDataSource("ds1").getConnection();
		Statement st=  connection.createStatement();
		st.execute("CREATE TABLE t_security_user(id INT PRIMARY KEY IDENTITY (1, 1), username VARCHAR(50) NOT NULL, password VARCHAR(50) NOT NULL, enabled BIT, name VARCHAR(50))");
		logger.debug("t_security_user创建成功!");
		st.execute("CREATE TABLE t_user_role(username VARCHAR(50) NOT NULl,rolename VARCHAR(50) NOT NULL)");
		logger.debug("t_user_role创建成功!");
		st.execute("CREATE TABLE t_security_role(id INT PRIMARY KEY IDENTITY (1, 1), rolename VARCHAR(50) NOT NULL,name VARCHAR(50))");
		logger.debug("t_security_role创建成功!");
		st.execute("CREATE TABLE t_role_auth(rolename VARCHAR(50) NOT NULL,auth VARCHAR(255) NOT NULL)");
		logger.debug("t_role_auth创建成功!");
		st.execute("CREATE TABLE performance_test(id INT PRIMARY KEY IDENTITY (1, 1),name VARCHAR(500) NOT NULL,date DATE NOT NULL)");
		logger.debug("performance_test创建成功!");
		st.execute("CREATE TABLE t_log(id INT PRIMARY KEY IDENTITY (1, 1),log VARCHAR(5000) NOT NULL,date DATETIME NOT NULL,type INT)");
		logger.debug("t_log创建成功!");
		st.execute("CREATE TABLE t_sequence(table_name VARCHAR(50) PRIMARY KEY,seq_value INT NOT NULL)");
		logger.debug("t_sequence创建成功!");
		st.close();
		connection.commit();//sqlserver2008必须有此
		MultiDataSourceFactory.getDataSource("ds1").replace(connection);
	}


}
