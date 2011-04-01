package org.cy.core.transaction;

import java.sql.SQLException;

/**
 * 功 能 描 述:<br>
 * 事务管理器
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-1下午05:22:58
 * <br>项 目 信 息:paramecium:org.cy.core.transaction.TransactionManager.java
 */
public class TransactionManager {
	
	private static ThreadLocal<Transaction> transactionThreadLocal;
	
	/**
	 * 开启一段事务
	 * @throws SQLException
	 */
	public static void before() throws SQLException {
		Transaction transaction = transactionThreadLocal.get();
		if(transaction==null){
			transactionThreadLocal.set(new Transaction());
		}
	}
	
	public static void end() throws SQLException {
		Transaction transaction = transactionThreadLocal.get();
		if(transaction!=null){
			if(transaction.isException()){
				transaction.rollback();
			}else{
				transaction.commit();
			}
			transaction.close();
		}
	}
	
}
