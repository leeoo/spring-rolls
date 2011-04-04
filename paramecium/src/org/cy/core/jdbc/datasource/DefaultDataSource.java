package org.cy.core.jdbc.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.cy.core.log.Log;
import org.cy.core.log.LoggerFactory;

/**
 * 功能描述(Description):<br><b>
 * 默认数据源实现
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-1下午08:11:11</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.cy.core.jdbc.datasource.DefaultDataSource.java</b>
 */
public abstract class DefaultDataSource implements DataSource{
	
	private final static Log logger = LoggerFactory.getLogger();
	static PrintWriter printWriter;
	static String driverClassName;
	static String url;
	static String username;
	static String password;
	
	public Connection getConnection(){
		Connection connection = null;
		if(url!=null&&username!=null){
			try {
				connection = DriverManager.getConnection(url, username, password);
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error(e);
			}
		}
		return connection;
	}
	
	public Connection getConnection(String arg0, String arg1)throws SQLException {
		throw new UnsupportedOperationException("EN:The method can't using!;CN:不能使用该方法!;");
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException("EN:The method can't using!;CN:不能使用该方法!;");
	}

	public int getLoginTimeout() {
		return 5;
	}

	public void setLoginTimeout(int _loginTimeout) {
	}

	public PrintWriter getLogWriter() throws SQLException {
		return printWriter;
	}

	public void setLogWriter(PrintWriter _printWriter) throws SQLException {
		printWriter = _printWriter;
	}

}
