package com.demo.service.system;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.paramecium.ioc.annotation.Service;
import org.paramecium.jdbc.datasource.DefaultDataSource;
import org.paramecium.jdbc.datasource.MultiDataSourceFactory;

/**
 * 功 能 描 述:<br>
 * 数据源配置
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2012-3-20下午02:38:59
 * <br>项 目 信 息:paramecium:com.demo.service.system.DataSourceService.java
 */
@Service
public class DataSourceService {

	/**
	 * 获得所有数据源
	 * @return
	 */
	public Map<String,DataSource> getDataSources(){
		Map<String,DataSource> map = new LinkedHashMap<String, DataSource>();
		Collection<String> dataSourceNames = MultiDataSourceFactory.getDataSourceNames();
		for(String dsName : dataSourceNames){
			map.put(dsName, MultiDataSourceFactory.getDataSource(dsName));
		}
		return map;
	}
	
	/**
	 * 植入数据源修改信息
	 * @param dataSourceName
	 * @param map
	 */
	public void putDefaultDataSource(String dataSourceName,Map<String,Object> map){
		String url = (String)map.get("url");
		String username = (String)map.get("username");
		String password = (String)map.get("password");
		String poolMax = (String)map.get("poolMax");
		String poolBase = (String)map.get("poolBase");
		String connectLife = (String)map.get("connectLife");
		String poolThreadTime = (String)map.get("poolThreadTime");
		String loginTimeout = (String)map.get("loginTimeout");
		DefaultDataSource dataSource = (DefaultDataSource) MultiDataSourceFactory.getDataSource(dataSourceName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		if(poolMax != null && !poolMax.isEmpty()){
			dataSource.setPoolMax(Integer.parseInt(poolMax));
		}
		if(poolBase != null && !poolBase.isEmpty()){
			dataSource.setPoolBase(Integer.parseInt(poolBase));
		}
		if(connectLife != null && !connectLife.isEmpty()){
			dataSource.setConnectLife(Integer.parseInt(connectLife));
		}
		if(poolThreadTime != null && !poolThreadTime.isEmpty()){
			dataSource.setPoolThreadTime(Integer.parseInt(poolThreadTime));
		}
		if(loginTimeout != null && !loginTimeout.isEmpty()){
			dataSource.setLoginTimeout(Integer.parseInt(loginTimeout));
		}
		DefaultDataSource.clearPool(dataSourceName);
		MultiDataSourceFactory.putDataSource(dataSourceName, dataSource);
	}
	
}
