package org.cy.core.transaction;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.cy.core.jdbc.datasource.MultiDataSourceFactory;
import org.cy.core.log.Log;
import org.cy.core.log.LoggerFactory;

/**
 * 功 能 描 述:<br>
 * 事务管理器，支持多数据源
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-1下午05:22:58
 * <br>项 目 信 息:paramecium:org.cy.core.transaction.TransactionManager.java
 */
public class TransactionManager {
	
	private final static Log logger = LoggerFactory.getLogger();
	
	private final static ThreadLocal<Map<String,Transaction>> transactionThreadLocal = new ThreadLocal<Map<String,Transaction>>();
	
	/**
	 * 获得当前事务
	 * @return
	 * @throws SQLException
	 */
	public static Transaction getCurrentTransaction(final String dataSourceName) {
		begin();
		Map<String,Transaction> transactionMap = transactionThreadLocal.get();
		return transactionMap.get(dataSourceName);
	}
	
	/**
	 * 设置全局出现异常
	 */
	public static void globalException() {
		Map<String,Transaction> transactionMap = transactionThreadLocal.get();
		if(transactionMap!=null){
			for(Transaction transaction : transactionMap.values()){
				transaction.setException();
			}
		}
	}
	
	/**
	 * 设置只读
	 */
	public static void readOnly() {
		Map<String,Transaction> transactionMap = transactionThreadLocal.get();
		if(transactionMap!=null){
			for(Transaction transaction : transactionMap.values()){
				try {
					transaction.setReadOnly(true);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e);
				}
			}
		}
	}

	/**
	 * 设置事务级别
	 */
	public static void setLevel(int level) {
		Map<String,Transaction> transactionMap = transactionThreadLocal.get();
		if(transactionMap!=null){
			for(Transaction transaction : transactionMap.values()){
				try {
					transaction.setTransactionIsolation(level);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e);
				}
			}
		}
	}
	
	/**
	 * 开启一段事务，收集所有配置的可用数据源
	 * @throws SQLException
	 */
	public static void begin() {
		Map<String,Transaction> transactionMap = transactionThreadLocal.get();
		if(transactionMap==null){
			try {
				transactionMap = new HashMap<String, Transaction>();
				for(String dataSourceName:MultiDataSourceFactory.getDataSourceNames()){
					transactionMap.put(dataSourceName, new Transaction(dataSourceName));
					transactionThreadLocal.set(transactionMap);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
			}
		}
	}
	
	/**
	 * 结束本次事务
	 * @throws SQLException
	 */
	public static void end() {
		Map<String,Transaction> transactionMap = transactionThreadLocal.get();
		if(transactionMap!=null){
			for(Transaction transaction : transactionMap.values()){
				try {
					if(transaction.isException()){
						transaction.rollback();
					}else{
						transaction.commit();
					}
					transaction.over();
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e);
				}
			}
			transactionThreadLocal.remove();
		}
	}
	
}
