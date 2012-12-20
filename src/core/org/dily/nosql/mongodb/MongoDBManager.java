package org.dily.nosql.mongodb;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.dily.commons.PropertiesUitls;
import org.dily.log.Log;
import org.dily.log.LoggerFactory;

import com.mongodb.DB;
/**
 * 功 能 描 述:<br>
 * MongoDB数据库管理类
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-7-21下午02:44:17
 * <br>项 目 信 息:dily:org.dily.nosql.mongodb.MongoDBManager.java
 */
public class MongoDBManager {

	private final static Log logger = LoggerFactory.getLogger();
	public static String defaultDBName = null;
	private static ConcurrentMap<String,DB> pool = new ConcurrentHashMap<String,DB>();
	
	static{
		Map<String,Map<String,String>> map = PropertiesUitls.getByType("/mongodb.properties");
		for(String key : map.keySet()){
			Map<String,String> propery = map.get(key);//获取.后内容
			try {
				String url = propery.get("url");
				String portStr = propery.get("port");
				String dbName = propery.get("dbName");
				if(defaultDBName == null){
					defaultDBName = dbName;
				}
				int port = portStr==null ? 27017 : Integer.parseInt(portStr);
				MongoConfig config = new MongoConfig(url, port, dbName);
				pool.put(key, config.getDB());
			} catch (Throwable e) {
				logger.error(e);
			}
		}
		map.clear();
	}
	
	public static DB getMongoDB(String key){
		return pool.get(key);
	}
	
}
