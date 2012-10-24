package org.paramecium.jdbc;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.paramecium.cache.Cache;
import org.paramecium.cache.CacheManager;
import org.paramecium.commons.PathUtils;
import org.paramecium.commons.PropertiesUitls;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;


/**
 * 序列生成器，模拟oracle序列，采用本地内存+文件存储方式
 * @author caoyang
 *
 */
public class SequenceGenerator {
	
	private final static Log logger = LoggerFactory.getLogger();
	public static int sequenceCacheMax = 255;
	private static final String sequenceFileName = "/sequence.db";
	@SuppressWarnings("unchecked")
	private static Cache<String, Long> sequencePool = (Cache<String, Long>) CacheManager.getCacheByType("SEQUENCE_CACHE", sequenceCacheMax);
	private static final ReentrantLock lock = new ReentrantLock();
	
	static{
		File file = new File(PathUtils.getClassFile(sequenceFileName));
		if(!file.exists()){
			try {
				if(!file.createNewFile()){
					logger.fatal("致命错误：序列数据文件无法创建！");
				}
			} catch (IOException e) {
				logger.fatal(e);
			}
		}
		Map<String,String> values = PropertiesUitls.get(sequenceFileName);
		for(String key : values.keySet()){
			key = key.toLowerCase();
			String value = values.get(key);
			long longValue = 0;
			if(value!=null){
				try{
					longValue = Long.parseLong(value);
				}catch (NumberFormatException e) {
					logger.error(e);
				}
			}
			sequencePool.put(key, longValue);
			reset(key);
		}
		values.clear();
	}
	
	/**
	 * 获得当前序列值
	 * @param dsName数据源名称
	 * @param tableName表名或序列名
	 * @return
	 */
	public static long currentSequence(String dsName,String tableName){
		lock.lock();
		try{
			return currentSequence(dsName.concat(tableName));
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 获得当前序列值
	 * @param tableName表名或序列名
	 * @return
	 */
	public static long currentSequence(String tableName){
		lock.lock();
		try{
			tableName = tableName.toLowerCase();
			Long value = sequencePool.get(tableName);
			return value==null?0l:value;
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 获得下一个序列
	 * @param dsName数据源名称
	 * @param tableName表名或序列名
	 * @return
	 */
	public static long nextSequence(String dsName,String tableName){
		lock.lock();
		try{
			return nextSequence(dsName.concat(tableName));
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 获得下一个序列
	 * @param tableName表名或序列名
	 * @return
	 */
	public static long nextSequence(String tableName){
		lock.lock();
		try{
			tableName = tableName.toLowerCase();
			Long value = sequencePool.get(tableName);
			if(value==null){//该表第一次使用序列
				value = 0l;
				value++;
				sequencePool.put(tableName, value);
				reset(tableName);
				logger.debug(tableName.concat(":序列产生下一个值为").concat(value.toString()));
				return value;
			}else if((value+1)%sequenceCacheMax==0){//如果达到峰值，为序列文件累加
				value++;
				sequencePool.put(tableName, value);
				reset(tableName);
				logger.debug(tableName.concat(":序列产生下一个值为").concat(value.toString()));
				return value;
			}
			value++;
			sequencePool.put(tableName, value);
			logger.debug(tableName.concat(":序列产生下一个值为").concat(value.toString()));
			return value;
		} finally {
			lock.unlock();
		}
		
	}
	
	private static void reset(String tableName){
		lock.lock();
		try{
			tableName = tableName.toLowerCase();
			Long value = sequencePool.get(tableName);
			value += sequenceCacheMax;
			PropertiesUitls.set(sequenceFileName, tableName, value.toString());
			logger.debug(tableName.concat(":更新序列成功！目前序列峰值为").concat(value.toString()));
		} finally {
			lock.unlock();
		}
	}
	
}
