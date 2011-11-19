package com.demo.test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.UUID;

import org.paramecium.ioc.ApplicationContext;
import org.paramecium.jdbc.datasource.MultiDataSourceFactory;

import com.demo.entity.system.Role;
import com.demo.service.system.RoleService;


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
	}
	
	static class TestRunner extends Thread {
		
		RoleService roleService =  (RoleService) ApplicationContext.getNotSecurityBean("roleService");
		
		public void save(){
			Role role = new Role();
			while(true){
				role.setName("haha");
				role.setRolename(UUID.randomUUID().toString());
				try {
					roleService.save(role);
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




