package com.demo.test;

import java.sql.Connection;
import java.sql.Statement;

import org.paramecium.jdbc.datasource.MultiDataSourceFactory;

/**
 * 功 能 描 述:<br>
 * 数据结构初始化
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-11-23下午02:59:24
 * <br>项 目 信 息:paramecium:com.demo.test.Init.java
 */
public class Init {
	
	public static void main(String[] args) throws Exception {
		createTables();
	}
	
	public static void createTables() throws Exception{
		Connection connection = MultiDataSourceFactory.getDataSource("ds1").getConnection();
		Statement st=  connection.createStatement();
		st.execute("CREATE TABLE t_security_user(id INT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(50) NOT NULL UNIQUE, password VARCHAR(50) NOT NULL, enabled bit, name VARCHAR(50))");
		st.execute("CREATE TABLE t_user_role(username VARCHAR(50) NOT NULl,rolename VARCHAR(50) NOT NULL)");
		st.execute("CREATE TABLE t_security_role(id INT PRIMARY KEY AUTO_INCREMENT, rolename VARCHAR(50) NOT NULL UNIQUE,name VARCHAR(50))");
		st.execute("CREATE TABLE t_role_auth(rolename VARCHAR(50) NOT NULL,auth VARCHAR(255) NOT NULL)");
		st.execute("CREATE TABLE performance_test(id INT PRIMARY KEY AUTO_INCREMENT,name VARCHAR(500) NOT NULL,date DATE NOT NULL)");
	}
	
	
	
}




