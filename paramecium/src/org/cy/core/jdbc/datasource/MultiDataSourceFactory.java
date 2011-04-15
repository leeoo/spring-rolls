package org.cy.core.jdbc.datasource;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

import org.cy.core.commons.PropertiesUitls;
import org.cy.core.jdbc.JdbcTemplateFactory;
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
			JdbcTemplateFactory.dbTypes.put(key, propery.get("dbType"));
			propery.remove("dataSourceClass");//移除，对实例化数据源无用
			propery.remove("dbType");//移除，对实例化数据源无用
			Object dataSource = null;
			try {
				Class<?> clazz = Class.forName(dataSourceClass);
				dataSource = clazz.newInstance();
				for(Entry<String, String> entry : propery.entrySet()){
					String fieldName = entry.getKey();
					for(Field field:clazz.getDeclaredFields()){
						if(fieldName.equalsIgnoreCase(field.getName())){
							field.setAccessible(true);
							if(field.getType().equals(Integer.TYPE)){
								field.set(dataSource,Integer.valueOf(entry.getValue()));
							}else if(field.getType().equals(Boolean.TRUE)){
								field.set(dataSource,Boolean.valueOf(entry.getValue()));
							}else{
								field.set(dataSource,entry.getValue());
							}
						}
					}
				}
				multiDataSource.put(key,(DataSource)dataSource);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
			}
		}
	}
	
	/**
	 * 获得多数据源默认的数据源，如果只有一个返回当前
	 * @return
	 */
	public static DataSource getDataSource(){
		return multiDataSource.values().iterator().next();
	}

	/**
	 * 根据指定数据源名称获得数据源
	 * @param dataSourceName
	 * @return
	 */
	public static DataSource getDataSource(String dataSourceName){
		return multiDataSource.get(dataSourceName);
	}
	
	/**
	 * 获得所有数据源名称
	 * @return
	 */
	public static Collection<String> getDataSourceNames(){
		return multiDataSource.keySet();
	}
	
}
