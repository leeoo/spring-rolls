package org.paramecium.jdbc.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;

/**
 * 功能描述(Description):<br><b>
 * 不包含连接池,消耗数据库连接，容易使数据库报错(大多是连接不够用的错误)
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-12-1下午07:47:20</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.jdbc.datasource.NotPoolDataSource.java</b>
 */
public class NotPoolDataSource implements DataSource{
	
	private final static Log logger = LoggerFactory.getLogger();
	private PrintWriter printWriter;
	private static boolean isInit = false;//判断是否实例化后被使用过
	private String driverClassName;
	private String url;
	private String username;
	private String password;
	private int loginTimeout = 5;
	
	public Connection getConnection(){
		Connection connection = null;
		if(url!=null&&username!=null){
			try {
				if(!isInit){
					try {
						Class.forName(driverClassName);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						logger.error(e);
					}
					isInit= true;
				}
				connection = DriverManager.getConnection(url, username, password);
				DriverManager.setLoginTimeout(getLoginTimeout());
				connection.setAutoCommit(true);
				connection.setReadOnly(false);
			} catch (SQLException e) {
				logger.error(e);
				if(connection!=null){
					try {
						connection.close();
					} catch (SQLException e1) {
						logger.error(e);
					}
				}
				connection = null;
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

	public PrintWriter getLogWriter() throws SQLException {
		return printWriter;
	}

	public void setLogWriter(PrintWriter _printWriter) throws SQLException {
		this.printWriter = _printWriter;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLoginTimeout() {
		return loginTimeout;
	}

	public void setLoginTimeout(int loginTimeout) {
		this.loginTimeout = loginTimeout;
	}

}
