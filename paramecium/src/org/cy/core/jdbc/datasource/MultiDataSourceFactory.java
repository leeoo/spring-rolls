package org.cy.core.jdbc.datasource;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

import org.cy.core.commons.PropertiesUitls;
import org.cy.core.log.Log;
import org.cy.core.log.LoggerFactory;
/**
 * 功 能 描 述:<br>
 * 多数据源管理
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-6下午01:22:37
 * <br>项 目 信 息:paramecium:org.cy.core.jdbc.datasource.MultiDataSourceFactory.java
 */
public class MultiDataSourceFactory {
	
	private final static Log logger = LoggerFactory.getLogger();
	private final static ConcurrentMap<String,DataSource> multiDataSource = new ConcurrentHashMap<String, DataSource>();
	
	static{
		Map<String,Map<String,String>> map = PropertiesUitls.getByType("/database.properties");
		for(String key : map.keySet()){
			Map<String,String> propery = map.get(key);
			String dataSourceClass = propery.get("dataSourceClass");
			propery.remove("dataSourceClass");//移除，对实例化数据源无用
			DataSource dataSource = null;
			try {
				dataSource = (DataSource) Class.forName(dataSourceClass).newInstance();
				for(Entry<String, String> entry : propery.entrySet()){
					String fieldName = entry.getKey();
					Field filed = DataSource.class.getField(fieldName);
					filed.setAccessible(true);
					if(filed.getType().equals(Integer.TYPE)){
						filed.set(dataSource,Integer.valueOf(entry.getValue()));
					}else if(filed.getType().equals(Boolean.TRUE)){
						filed.set(dataSource,Boolean.valueOf(entry.getValue()));
					}else{
						filed.set(dataSource,entry.getValue());
					}
					
				}
				multiDataSource.put(key, dataSource);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
			}
		}
	}
	
	public DataSource getDataSource(){
		return multiDataSource.values().iterator().next();
	}
	
	public DataSource getDataSource(String dataSourceName){
		return multiDataSource.get(dataSourceName);
	}
	
}
