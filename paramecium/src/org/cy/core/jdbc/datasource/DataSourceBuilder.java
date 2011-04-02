package org.cy.core.jdbc.datasource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.cy.core.jdbc.JdbcTemplateFactory;
import org.cy.core.log.Log;
import org.cy.core.log.LoggerFactory;
/**
 * 功能描述(Description):<br><b>
 * 默认数据源的链接信息构建器
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-1下午08:11:27</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.cy.core.jdbc.datasource.DataSourceBuilder.java</b>
 */
public class DataSourceBuilder extends DefaultDataSource{
	
	private final static Log logger = LoggerFactory.getLogger(DataSourceBuilder.class);
	public static boolean sqlIsFormat = false;
	
	static{
		Properties properties = new Properties();
		InputStream inputStream = System.class.getResourceAsStream("/database.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			logger.error(e.fillInStackTrace());
		}
		String autoCommitStr = properties.getProperty("autoCommit");
		autoCommit = autoCommitStr != null ? Boolean.valueOf(autoCommitStr) : true;
		String sqlIsFormatStr = properties.getProperty("sqlIsFormat");
		sqlIsFormat = sqlIsFormatStr != null ? Boolean.valueOf(sqlIsFormatStr) : false;
		String loginTimeoutStr = properties.getProperty("loginTimeout");
		loginTimeout = loginTimeoutStr != null ? Integer.valueOf(loginTimeoutStr) : 5;
		driverClassName = properties.getProperty("driverClassName");
		url = properties.getProperty("url");
		username = properties.getProperty("username");
		password = properties.getProperty("password");
		if(password==null){
			password = "";
		}
		if(driverClassName!=null){
			try {
				Class.forName(driverClassName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				logger.error(e.fillInStackTrace());
			}
		}
		JdbcTemplateFactory.dbType = properties.getProperty("dbType");
		if(JdbcTemplateFactory.dbType!=null&&!JdbcTemplateFactory.dbType.isEmpty()){
			JdbcTemplateFactory.dbType = JdbcTemplateFactory.dbType.toLowerCase();
		}
	}

	public static DataSource getDataSource() {
		return new DataSourceBuilder();
	}

}
