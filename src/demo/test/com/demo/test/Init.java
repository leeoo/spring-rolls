package com.demo.test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.UUID;

import org.paramecium.commons.DateUtils;
import org.paramecium.ioc.ApplicationContext;
import org.paramecium.jdbc.datasource.MultiDataSourceFactory;


public class Init {
	
	static int j = 0;;

	public static void main(String[] args) throws Exception {
		for(int i =0;i <4500;i++){
			new TestRunner().start();
		}
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
	
	static class TestRunner extends Thread {
		
		PerformanceService performanceService =  (PerformanceService) ApplicationContext.getNotSecurityBean("performanceService");
		
		public void save(){
			Performance performance = new Performance();
			while(true){
				performance.setName(UUID.randomUUID().toString());
				performance.setDate(DateUtils.getCurrentDateTime());
				try {
					performanceService.save(performance);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		public void run() {
			save();
		}
		
	}
	
}




