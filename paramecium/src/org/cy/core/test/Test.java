package org.cy.core.test;

import org.cy.core.jdbc.JdbcTemplate;
import org.cy.core.jdbc.TransactionManager;
import org.cy.core.jdbc.dialect.MySqlDialect;

public class Test{
	
	public static void main(String[] args) throws Exception {
		TransactionManager transactionManager = new TransactionManager();
		JdbcTemplate template = new MySqlDialect(transactionManager.getConnection());
		template.executeDML("insert into t_logger_info(log_info,log_date) values('aaaa','1982-02-06')");
		template.executeDDL("create table test(id int,name varchar(100))");
		template.executeDDL("drop table test");
		transactionManager.close();
	}
	
}
