package com.demo.service.system;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.net.URI;
import java.sql.Connection;
import java.sql.Statement;

import org.paramecium.ioc.annotation.Service;
import org.paramecium.jdbc.datasource.MultiDataSourceFactory;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;

@Service
public class TableInitService {
	
	private final static Log logger = LoggerFactory.getLogger();
	
	public void init(){
		if (java.awt.Desktop.isDesktopSupported()) {
			try {
				URI uri = java.net.URI.create("http://127.0.0.1");
				Desktop dp = java.awt.Desktop.getDesktop();
				if (dp.isSupported(Action.BROWSE)) {
					dp.browse(uri);
				}
			} catch (java.lang.NullPointerException e) {
			} catch (java.io.IOException e) {
			}
		}
		/*try {
			createTables();
			logger.debug("DEMO用H2数据库内存表创建成功!");
		} catch (Exception e) {
			logger.error(e);
		}*/
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
		st.execute("CREATE TABLE t_log(id INT PRIMARY KEY AUTO_INCREMENT,log VARCHAR(5000) NOT NULL,date DATE NOT NULL,type int)");
		logger.debug("t_log创建成功!");
	}

}
