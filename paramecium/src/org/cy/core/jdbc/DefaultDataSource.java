package org.cy.core.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * 功 能 描 述:<br>
 * 默认数据源实现
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-3-30下午02:16:01
 * <br>项 目 信 息:paramecium:org.cy.core.jdbc.DefaultDataSource.java
 */
public class DefaultDataSource extends DataSourceBuilder implements DataSource{
	
	public Connection getConnection() throws SQLException {
		return createConnection();
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
		return loginTimeout;
	}

	public void setLoginTimeout(int _loginTimeout) {
		loginTimeout = _loginTimeout;
	}

	public PrintWriter getLogWriter() throws SQLException {
		return printWriter;
	}

	public void setLogWriter(PrintWriter _printWriter) throws SQLException {
		printWriter = _printWriter;
	}

}
