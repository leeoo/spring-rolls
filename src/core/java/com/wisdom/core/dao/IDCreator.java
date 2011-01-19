package com.wisdom.core.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 功能描述(Description):<br><b>
 * 自增主键构建器
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2010-5-13下午09:12:19</b>
 * <br>项目名称(Project Name): <b>wisdom.3.0RC2</b>
 * <br>包及类名(Package Class): <b>com.wisdom.core.dao.IDCreator.java</b>
 */
public class IDCreator {
	private static final Logger logger = LoggerFactory.getLogger(IDCreator.class);
	private static ConcurrentMap<String,Long> idMap=new ConcurrentHashMap<String,Long>();
	private static ConcurrentMap<String,Long> maxIdMap=new ConcurrentHashMap<String,Long>();
	public static long idCacheMaxValue = 255;
	public static String businessCode;
	public static DataSource dataSource;

	/**
	 * 获取新主键ID值
	 * @param idName
	 * @return 新主键值
	 */
	public synchronized static long getNextId(String idName){
		validationidName(idName);
		validationNull(dataSource);
		synchronized (idName) {
			Long currentId=idMap.get(idName);
			Long maxId=maxIdMap.get(idName);
			if(currentId==null||maxId==null||currentId.longValue()>=maxId.longValue()){
				logger.info(idName+"ID名称的自增ID值达到饱和或刚刚构建，正在重新取新值.");
				new IDCreator().init(idName);
				currentId=idMap.get(idName);
			}
			currentId = Long.valueOf(currentId.longValue() + 1);
			logger.info(idName+"ID名称产生一个自增ID值"+currentId);
			idMap.put(idName, Long.valueOf(currentId));
			return currentId.longValue();
		}
	}
	
	/**
	 * 获取新主键IDS值数组
	 * @param idName
	 * @return 新主键值数组
	 */
	public synchronized static long[] getNextIds(String idName,int idCount){
		Assert.isTrue(0>=idCount, "获得ID数组不能小于1");
		long[] ids = new long[idCount];
		for(int id = 0 ; id < idCount ; id++){
			ids[id] = getNextId(idName);
		}
		return ids;
	}

	/**
	 * 获取当前主键值
	 * @param idName
	 * @return 当前主键值
	 */
	public synchronized static long getCurrentId(String idName){
		validationidName(idName);
		synchronized (idName) {
			Long currentId=idMap.get(idName);
			if(currentId==null){
				logger.info(idName+"ID名称没有加入ID主键生成器");
				return -1;
			}else{
				logger.info(idName+"ID名称当前自增ID值为"+currentId);
			}
			return currentId.longValue();
		}
	}
	
	private static void validationidName(String idName){
		Assert.notNull(idName,"ID名称不能为空!");
		Assert.hasText(idName,"ID名称不能为空!");
	}

	private static void validationNull(Object obj){
		Assert.notNull(obj,obj.getClass().getSimpleName()+"实例不能为NULL!");
	}
	
	
	private void init(String idName){
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e1) {
		}
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e1) {
		}
		ResultSet rs = null;
		try {
			rs = statement.executeQuery("SELECT COUNT(0) ct FROM t_system_id WHERE table_name='"+idName+"'");
		} catch (SQLException e1) {
		}
		int count = 0;
		try {
			if(rs.next()) {    
				count = rs.getInt("ct");
				rs.close();
			}
		} catch (SQLException e1) {
		}
		if(count<1){//一个新表刚刚使用该方法
			try {
				statement.execute("INSERT INTO t_system_id(table_name,id_value) VALUES('"+idName+"',"+idCacheMaxValue+")");
				connection.commit();
			} catch (Exception e) {
				logger.error("当为ID名称"+idName+"的构建新ID时，出现错误!{}",e.getMessage());
			}
			idMap.put(idName, Long.valueOf(0));
			maxIdMap.put(idName, Long.valueOf(idCacheMaxValue));
		}else{
			try {
				ResultSet rs2 = statement.executeQuery("SELECT id_value iv FROM t_system_id WHERE table_name='"+idName+"'");
				long  currentCount = 0;
				try {
					if(rs2.next()) {    
						currentCount = rs2.getInt("iv");
						rs2.close();
					}
				} catch (SQLException e1) {
				}
				long maxID=currentCount+idCacheMaxValue;
				statement.execute("UPDATE t_system_id SET id_value="+maxID+" WHERE table_name='"+idName+"'");
				connection.commit();
				maxIdMap.put(idName, Long.valueOf(maxID));
				idMap.put(idName, Long.valueOf(currentCount));
			} catch (Exception e) {
				logger.error("当修改ID名称"+idName+"时，出现错误!{}",e.getMessage());
			}
		}
		if(statement!=null){
			try {
				statement.close();
			} catch (SQLException e) {
			}
		}
		if(connection!=null){
			try {
				connection.close();
			} catch (SQLException e) {
			}
		}
	}
	
	
	public void setIdCacheMaxValue(long idCacheMaxValue) {
		IDCreator.idCacheMaxValue = idCacheMaxValue;
	}
	
	public void setBusinessCode(String businessCode){
		IDCreator.businessCode = businessCode;
	}

	public void setDataSource(DataSource dataSource) {
		IDCreator.dataSource = dataSource;
	}
	
}
