package org.cy.core.transaction;

import java.sql.SQLException;

import org.cy.core.log.Log;
import org.cy.core.log.LoggerFactory;

/**
 * 功 能 描 述:<br>
 * 事务管理器
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-1下午05:22:58
 * <br>项 目 信 息:paramecium:org.cy.core.transaction.TransactionManager.java
 */
public class TransactionManager {
	
	private final static Log logger = LoggerFactory.getLogger();
	
	private final static ThreadLocal<Transaction> transactionThreadLocal = new ThreadLocal<Transaction>();
	
	/**
	 * 获得当前事务
	 * @return
	 * @throws SQLException
	 */
	public static Transaction getCurrentTransaction() {
		before();
		Transaction transaction = transactionThreadLocal.get();
		return transaction;
	}
	
	/**
	 * 开启一段事务
	 * @throws SQLException
	 */
	public static void before() {
		Transaction transaction = transactionThreadLocal.get();
		if(transaction==null){
			try {
				transactionThreadLocal.set(new Transaction());
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error(e.fillInStackTrace());
			}
		}
	}
	
	/**
	 * 结束本次事务
	 * @throws SQLException
	 */
	public static void end() {
		Transaction transaction = transactionThreadLocal.get();
		if(transaction!=null){
			try {
				if(transaction.isException()){
					transaction.rollback();
				}else{
					transaction.commit();
				}
				transaction.close();
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error(e.fillInStackTrace());
			}
			transactionThreadLocal.remove();
		}
	}
	
}
