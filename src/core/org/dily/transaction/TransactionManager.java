package org.dily.transaction;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.dily.jdbc.datasource.MultiDataSourceFactory;
import org.dily.log.Log;
import org.dily.log.LoggerFactory;

/**
 * 功 能 描 述:<br>
 * 事务管理器，支持多数据源
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-1下午05:22:58
 * <br>项 目 信 息:dily:org.dily.transaction.TransactionManager.java
 */
public class TransactionManager {
	
	private final static Log logger = LoggerFactory.getLogger();
	
	private final static ThreadLocal<Map<String,Transaction>> transactionThreadLocal = new ThreadLocal<Map<String,Transaction>>();
	
	private final static ThreadLocal<String> masterTransactionThreadLocal = new ThreadLocal<String>();//嵌套事务主事务线程
	
	public static boolean isThisThread(){
		return transactionThreadLocal.get() != null ? true : false;
	}
	
	/**
	 * 设置成全局主线程
	 */
	public static void setGlobalMasterTransaction(){
		if(masterTransactionThreadLocal.get()==null){
			StackTraceElement[] ste = new Throwable().getStackTrace();
			masterTransactionThreadLocal.set(ste[ste.length-1].getClassName());//放入的时候，需要放入调用栈最外层调用者
			return;
		}
		logger.error("该事务线程已经被声明为嵌套事务主事务，不能重复声明主事务！");
	}
	
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
		}else{
			logger.warn("当前事务线程为空!");
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
				} catch (Throwable e) {
					logger.error(e);
				}
			}
		}else{
			logger.warn("当前事务线程为空!");
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
				} catch (Throwable e) {
					logger.error(e);
				}
			}
		}else{
			logger.warn("当前事务线程为空!");
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
					Transaction transaction = new Transaction(dataSourceName);
					transactionMap.put(dataSourceName, transaction);
					transactionThreadLocal.set(transactionMap);
					logger.debug("事务线程："+transactionMap.hashCode()+"已经建立!");
					logger.debug("数据源:"+dataSourceName+" 事务:"+transaction.hashCode()+"已经开启!");
				}
			} catch (Throwable e) {
				logger.error(e);
			}
		}
	}
	
	/**
	 * 结束本次事务
	 * @throws SQLException
	 */
	public static void end() {
		String threadClassName = masterTransactionThreadLocal.get();
		if(threadClassName!=null){
			StackTraceElement[] ste = new Throwable().getStackTrace();
			if(!ste[1].getClassName().equals(threadClassName)){//判断时，需要获取比对调用栈数组第一个调用者
				logger.warn("被声明为嵌套事务的线程中调用事务结束方法，由于该事务线程不是主事务，不能end整个事务！");
				return;
			}else{
				masterTransactionThreadLocal.remove();
				logger.debug("被声明为嵌套事务的线程中调用事务结束方法，该线程为主事务，成功end整个事务！");
			}
		}
		Map<String,Transaction> transactionMap = transactionThreadLocal.get();
		if(transactionMap!=null){
			for(Entry<String,Transaction> entry : transactionMap.entrySet()){
				Transaction transaction = entry.getValue();
				try {
					if(transaction.isException()){
						transaction.rollback();
					}else{
						transaction.commit();
					}
				} catch (Throwable e) {
					logger.error(e);
				}finally{
					try {
						transaction.over();//如果是默认数据源，内部连接池来管理Connection的关闭，无需手动关闭.
						logger.debug("数据源:"+entry.getKey()+" 事务:"+transaction.hashCode()+"已经结束!");
					} catch (SQLException e) {
						logger.error(e);
					}
				}
			}
			logger.debug("事务线程："+transactionMap.hashCode()+"已经销毁!");//如果放到最下面，对象实例也不存在!
			transactionMap.clear();
			transactionThreadLocal.remove();
		}else{
			logger.warn("当前事务线程为空!");
		}
	}
	
}
