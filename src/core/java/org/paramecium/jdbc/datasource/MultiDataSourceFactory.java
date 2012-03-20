package org.paramecium.jdbc.datasource;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

import org.paramecium.commons.PropertiesUitls;
import org.paramecium.jdbc.JdbcTemplateFactory;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
/**
 * 功 能 描 述:<br>
 * 多数据源管理
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-6下午01:22:37
 * <br>项 目 信 息:paramecium:org.paramecium.jdbc.datasource.MultiDataSourceFactory.java
 */
public class MultiDataSourceFactory {
	
	private final static Log logger = LoggerFactory.getLogger();
	public static String defaultDataSourceName = null;
	private final static ConcurrentMap<String,DataSource> multiDataSource = new ConcurrentHashMap<String, DataSource>();
	
	static{
		Map<String,Map<String,String>> map = PropertiesUitls.getByType("/database.properties");
		for(String key : map.keySet()){
			Map<String,String> propery = map.get(key);//获取.后内容
			String dataSourceClass = propery.get("dataSourceClass");
			JdbcTemplateFactory.dbTypes.put(key, propery.get("dbType"));
			propery.remove("dataSourceClass");//移除，对实例化数据源无用
			propery.remove("dbType");//移除，对实例化数据源无用
			propery.put("ds", key);
			if(defaultDataSourceName == null){
				defaultDataSourceName= key;
			}
			Object dataSource = null;
			try {
				Class<?> clazz = Class.forName(dataSourceClass);
				dataSource = clazz.newInstance();
				for(Entry<String, String> entry : propery.entrySet()){
					String fieldName = entry.getKey();
					for(Field field:clazz.getDeclaredFields()){
						if(fieldName.equalsIgnoreCase(field.getName())){
							field.setAccessible(true);
							if(field.getType().equals(Integer.TYPE)||field.getType()==int.class){
								field.set(dataSource,Integer.valueOf(entry.getValue()));
							}else if(field.getType().equals(Boolean.TYPE)||field.getType()==boolean.class){
								field.set(dataSource,Boolean.valueOf(entry.getValue()));
							}else{
								field.set(dataSource,entry.getValue());
							}
						}
					}
				}
				multiDataSource.put(key,(DataSource)dataSource);
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}
	
	/**
	 * 根据指定数据源名称获得数据源,此处性能高，不过在高并发时会出现connection多操作情况，建议此处每次生成新的DataSource
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
	
	/**
	 * 慎用，不能随意使用
	 * @param dataSourceName
	 * @param dataSource
	 */
	public static void putDataSource(String dataSourceName,DataSource dataSource){
		multiDataSource.put(dataSourceName, dataSource);
	}
	
}
