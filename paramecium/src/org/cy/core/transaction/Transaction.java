package org.cy.core.transaction;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.cy.core.jdbc.datasource.DataSourceBuilder;
/**
 * 功 能 描 述:<br>
 * 数据库事务处理
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-1下午05:45:03
 * <br>项 目 信 息:paramecium:org.cy.core.transaction.Transaction.java
 */
public class Transaction {

	private Connection connection = null;
	
	private boolean isException = false;
	
	public Transaction() throws SQLException{
		DataSource dataSource = DataSourceBuilder.getDataSource();
		this.connection = dataSource.getConnection();
	}

	public Connection getConnection() throws SQLException {
		if(this.connection!=null&&!this.connection.isClosed()){
			return this.connection;
		}
		throw new SQLException("EN:connection fail!CN:数据库连接错误!");
	}
	
	private void connectionLife() throws SQLException{
		if(this.connection==null){
			throw new SQLException("EN:connection fail!CN:数据库连接错误!");
		}else if(this.connection.isClosed()){
			throw new SQLException("EN:connection closed!CN:数据库连接已关闭!");
		}
	}
	
	/**
	 * Connection.TRANSACTION_READ_COMMITTED:默认,可以防止脏读<br>
	 * Connection.TRANSACTION_READ_UNCOMMITTED:只保证不会读到非法数据，有可能出现脏读取、重复读取、虚读<br>
	 * Connection.TRANSACTION_REPEATABLE_READ:可以防止脏读和不可重复读取<br>
	 * Connection.TRANSACTION_READ_SERIALIZABLE防止出现脏读取、重复读取、虚读。<br>
	 * Connection.TRANSACTION_NONE<br>
	 * @param level
	 * @throws SQLException
	 */
	public void setTransactionIsolation(int level) throws SQLException{
		connectionLife();
		connection.setTransactionIsolation(level);
	}
	
	public void setReadOnly(boolean readOnly) throws SQLException{
		connectionLife();
		connection.setReadOnly(readOnly);
	}

	public void commit() throws SQLException{
		connectionLife();
		connection.commit();
	}

	public void rollback() throws SQLException{
		connectionLife();
		connection.rollback();
	}
	
	public void close() throws SQLException{
		if(connection!=null&&!connection.isClosed()){
			connection.close();
		}
	}

	public boolean isException() {
		return isException;
	}

	public void setException() {
		if(this.isException == false){
			this.isException = true;
		}
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
}
