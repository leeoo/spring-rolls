package com.wisdom.core.dao;

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
	public static long idCacheMaxValue = 500;
	public static String businessCode;

	/**
	 * 获取新主键ID值
	 * @param idName
	 * @return 新主键值
	 */
	public synchronized static long getNextId(String idName,JdbcTemplate jdbcTemplate){
		validationidName(idName);
		validationNull(jdbcTemplate);
		synchronized (jdbcTemplate) {
			Long currentId=idMap.get(idName);
			Long maxId=maxIdMap.get(idName);
			if(currentId==null||maxId==null||currentId.longValue()>=maxId.longValue()){
				logger.info(idName+"ID名称的自增ID值达到饱和或刚刚构建，正在重新取新值.");
				new IDCreator().init(idName,jdbcTemplate);
				currentId=idMap.get(idName);
			}
			currentId = Long.valueOf(currentId.longValue() + 1);
			logger.info(idName+"ID名称产生一个自增ID值"+currentId);
			idMap.put(idName, Long.valueOf(currentId));
			return currentId.longValue();
		}
	}

	/**
	 * 获取新主键ID值
	 * @param idName
	 * @return 新主键值
	 */
	public synchronized static long getNextId(String idName,DataSource dataSource){
		validationidName(idName);
		validationNull(dataSource);
		synchronized (dataSource) {
			Long currentId=idMap.get(idName);
			Long maxId=maxIdMap.get(idName);
			if(currentId==null||maxId==null||currentId.longValue()>=maxId.longValue()){
				logger.info(idName+"ID名称的自增ID值达到饱和或刚刚构建，正在重新取新值.");
				new IDCreator().init(idName,dataSource);
				currentId=idMap.get(idName);
			}
			currentId = Long.valueOf(currentId.longValue() + 1);
			logger.info(idName+"ID名称产生一个自增ID值"+currentId);
			idMap.put(idName, Long.valueOf(currentId));
			return currentId.longValue();
		}
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
	
	private void init(String idName,DataSource dataSource){
		JdbcTemplate jdbcTemplate = GenericDaoFactory.getJdbcTemplate(dataSource);
		init(idName,jdbcTemplate);
	}
	
	private void init(String idName,JdbcTemplate jdbcTemplate){
		long isExist = jdbcTemplate.findLongByArray("SELECT COUNT(0) FROM t_system_id WHERE table_name=?", idName);
		if(isExist<1){//一个新表刚刚使用该方法
			try {
				jdbcTemplate.executeArray("INSERT INTO t_system_id(table_name,id_value) VALUES(?,?)", idName,idCacheMaxValue);
				jdbcTemplate.getConnection().commit();
			} catch (Exception e) {
				logger.error("当为ID名称"+idName+"的构建新ID时，出现错误!{}",e.getMessage());
			}
			idMap.put(idName, Long.valueOf(0));
			maxIdMap.put(idName, Long.valueOf(idCacheMaxValue));
		}else{
			try {
				long  currentCount = jdbcTemplate.findLongByArray("SELECT id_value FROM t_system_id WHERE table_name=?", idName);
				long maxID=currentCount+idCacheMaxValue;
				jdbcTemplate.executeArray("UPDATE t_system_id SET id_value=? WHERE table_name=?",maxID, idName);
				jdbcTemplate.getConnection().commit();
				maxIdMap.put(idName, Long.valueOf(maxID));
				idMap.put(idName, Long.valueOf(currentCount));
			} catch (Exception e) {
				logger.error("当修改ID名称"+idName+"时，出现错误!{}",e.getMessage());
			}
		}
	}
	
	
	public void setIdCacheMaxValue(long idCacheMaxValue) {
		IDCreator.idCacheMaxValue = idCacheMaxValue;
	}
	
	public void setBusinessCode(String businessCode){
		IDCreator.businessCode = businessCode;
	}
	
}
