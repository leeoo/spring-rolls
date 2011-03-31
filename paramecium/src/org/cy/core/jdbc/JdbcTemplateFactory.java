package org.cy.core.jdbc;

import java.sql.Connection;

import org.cy.core.jdbc.dialect.Db2Dialect;
import org.cy.core.jdbc.dialect.DerbyDialect;
import org.cy.core.jdbc.dialect.H2Dialect;
import org.cy.core.jdbc.dialect.HSqlDialect;
import org.cy.core.jdbc.dialect.InformixDialect;
import org.cy.core.jdbc.dialect.MySqlDialect;
import org.cy.core.jdbc.dialect.OracleDialect;
import org.cy.core.jdbc.dialect.PostgresDialect;
import org.cy.core.jdbc.dialect.SqlServer2005Dialect;
import org.cy.core.jdbc.dialect.SqlServerDialect;
import org.cy.core.jdbc.dialect.SqliteDialect;
/**
 * 功 能 描 述:<br>
 * 通过配置文件获取数据库类型
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-3-31下午01:14:37
 * <br>项 目 信 息:paramecium:org.cy.core.jdbc.JdbcTemplateFactory.java
 */
public class JdbcTemplateFactory {
	public static String dbType;
	
	public static JdbcTemplate getJdbcTemplate(Connection connection) {
		if(dbType.equals("mysql")){
			return new MySqlDialect(connection);
		}else if(dbType.equals("hsql")){
			return new HSqlDialect(connection);
		}else if(dbType.equals("h2")){
			return new H2Dialect(connection);
		}else if(dbType.equals("postgresql")){
			return new PostgresDialect(connection);
		}else if(dbType.equals("sqlite")){
			return new SqliteDialect(connection);
		}else if(dbType.equals("derby")){
			return new DerbyDialect(connection);
		}else if(dbType.equals("oracle")){
			return new OracleDialect(connection);
		}else if(dbType.equals("sqlserver")||dbType.equals("sqlserver2000")){
			return new SqlServerDialect(connection);
		}else if(dbType.equals("sqlserver2005")||dbType.equals("sqlserver2008")){
			return new SqlServer2005Dialect(connection);
		}else if(dbType.equals("db2")){
			return new Db2Dialect(connection);
		}else if(dbType.equals("informix")){
			return new InformixDialect(connection);
		}
		return null;
	}
	
}
