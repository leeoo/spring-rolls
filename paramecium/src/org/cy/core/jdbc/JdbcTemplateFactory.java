package org.cy.core.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.cy.core.jdbc.datasource.MultiDataSourceFactory;
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
	
	public static JdbcTemplate getJdbcTemplate(String dataSourceName,Connection connection) {
		String dbType = dbTypes.get(dataSourceName);
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
	
	public static JdbcTemplate getJdbcTemplate(Connection connection){
		return getJdbcTemplate(MultiDataSourceFactory.getDataSourceNames().iterator().next(), connection);
	}

	public static JdbcTemplate getJdbcTemplate(String dataSourceName){
		try {
			return getJdbcTemplate(dataSourceName, MultiDataSourceFactory.getDataSource(dataSourceName).getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
