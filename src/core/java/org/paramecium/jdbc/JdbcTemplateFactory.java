package org.paramecium.jdbc;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.paramecium.jdbc.dialect.Db2Dialect;
import org.paramecium.jdbc.dialect.DerbyDialect;
import org.paramecium.jdbc.dialect.H2Dialect;
import org.paramecium.jdbc.dialect.HSqlDialect;
import org.paramecium.jdbc.dialect.InformixDialect;
import org.paramecium.jdbc.dialect.MySqlDialect;
import org.paramecium.jdbc.dialect.OracleDialect;
import org.paramecium.jdbc.dialect.PostgresDialect;
import org.paramecium.jdbc.dialect.SqlServer2kDialect;
import org.paramecium.jdbc.dialect.SqlServer2kxDialect;
import org.paramecium.jdbc.dialect.SqliteDialect;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
/**
 * 功能描述(Description):<br><b>
 * 通过配置文件获取数据库类型
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-3下午09:34:19</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.jdbc.JdbcTemplateFactory.java</b>
 */
public class JdbcTemplateFactory {
	
	private final static Log logger = LoggerFactory.getLogger();
	public final static ConcurrentMap<String,String> dbTypes = new ConcurrentHashMap<String,String>();

	static{
		try {
			Class.forName("org.paramecium.jdbc.datasource.MultiDataSourceFactory");
		} catch (ClassNotFoundException e) {
			logger.error(e);
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
			return new SqlServer2kDialect();
		}else if(dbType.equalsIgnoreCase("sqlserver2005")||dbType.equalsIgnoreCase("sqlserver2008")){
			return new SqlServer2kxDialect();
		}else if(dbType.equalsIgnoreCase("db2")){
			return new Db2Dialect();
		}else if(dbType.equalsIgnoreCase("informix")){
			return new InformixDialect();
		}
		return null;
	}
	
}
