package org.cy.core.jdbc;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
 * 功能描述(Description):<br><b>
 * 通过配置文件获取数据库类型
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-3下午09:34:19</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.cy.core.jdbc.JdbcTemplateFactory.java</b>
 */
public class JdbcTemplateFactory {
	
	public final static ConcurrentMap<String,String> dbTypes = new ConcurrentHashMap<String,String>();

	static{
		try {
			Class.forName("org.cy.core.jdbc.datasource.MultiDataSourceFactory");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static JdbcTemplate getJdbcTemplate(final String dataSourceName) {
		String dbType = dbTypes.get(dataSourceName);
		if(dbType.equalsIgnoreCase("mysql")){
			return new MySqlDialect();
		}else if(dbType.equalsIgnoreCase("hsql")){
			return new HSqlDialect();
		}else if(dbType.equalsIgnoreCase("h2")){
			return new H2Dialect();
		}else if(dbType.equalsIgnoreCase("postgresql")){
			return new PostgresDialect();
		}else if(dbType.equalsIgnoreCase("sqlite")){
			return new SqliteDialect();
		}else if(dbType.equalsIgnoreCase("derby")){
			return new DerbyDialect();
		}else if(dbType.equalsIgnoreCase("oracle")){
			return new OracleDialect();
		}else if(dbType.equalsIgnoreCase("sqlserver")||dbType.equalsIgnoreCase("sqlserver2000")){
			return new SqlServerDialect();
		}else if(dbType.equalsIgnoreCase("sqlserver2005")||dbType.equalsIgnoreCase("sqlserver2008")){
			return new SqlServer2005Dialect();
		}else if(dbType.equalsIgnoreCase("db2")){
			return new Db2Dialect();
		}else if(dbType.equalsIgnoreCase("informix")){
			return new InformixDialect();
		}
		return null;
	}
	
}
