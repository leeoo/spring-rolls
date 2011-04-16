package org.cy.core.jdbc.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

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
public class DefaultDataSource implements DataSource{
	private static boolean isInit = false;
	private final static Log logger = LoggerFactory.getLogger();
	private Collection<Connection> connectionPool = new HashSet<Connection>();
	private PrintWriter printWriter;
	private String driverClassName;
	private String url;
	private String username;
	private String password;
	private int loginTimeout = 5;
	
	public DefaultDataSource(){
		logger.debug("默认数据源被载入!");
		new Thread(new PoolThread()).start();
		logger.debug("默认数据库连接池连接释放线程监控已启动!");
	}
	
	public Connection getConnection(){
		return getConnection4Pool();
	}
	
	private Connection getConnection4Pool(){
		for(Connection connection : connectionPool){
			try {
				if(connection.getAutoCommit()){
					return connection;
				}
			} catch (SQLException e) {
			}
		}
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
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
			}
		}
		connectionPool.add(connection);
		return connection;
	}

	private class PoolThread implements Runnable {
		public void run() {
			while (true) {
				try {
					logger.debug("默认数据源连接池当前数量为："+connectionPool.size());
					Thread.sleep(60 * 1000);// 一分钟清理一次使用完毕的Connection
					Collection<Connection> closedConnections = new HashSet<Connection>();
					for (Connection connection : connectionPool) {
						try {
							if (connection.getAutoCommit()) {
								connection.close();
								closedConnections.add(connection);
								logger.debug("一个长时间未被使用的Connection被关闭!");
							}
						} catch (SQLException e) {
						}
					}
					for(Connection connection : closedConnections){
						connectionPool.remove(connection);
						logger.debug("一个被关闭的Connection从连接池中清除!");
					}
				} catch (Exception ex) {
					logger.error(ex);
				}
			}
		}
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
