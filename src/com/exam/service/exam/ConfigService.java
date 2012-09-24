package com.exam.service.exam;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.paramecium.ioc.annotation.Service;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.GenericJdbcDao;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.transaction.annotation.Transactional;

import com.exam.entity.exam.ConfigInfo;

@ShowLabel("系统配置业务类")
@Service
@Transactional
public class ConfigService {
	
	private GenericJdbcDao genericJdbcDao = new GenericJdbcDao();
	
	private final static Log logger = LoggerFactory.getLogger();
	
	public void updateConfig(Map<String,String> config){
		for(String key:config.keySet()){
			String value = config.get(key);
			if(value!=null){
				if(key.equalsIgnoreCase("themeName")){
					try {
						genericJdbcDao.executeDMLByArray("UPDATE t_config SET pro_value = ? WHERE pro_key = 'themeName'", value);
						ConfigInfo.themeName = value;
					} catch (SQLException e) {
						logger.error(e);
					}
				}else if(key.equalsIgnoreCase("title")){
					try {
						genericJdbcDao.executeDMLByArray("UPDATE t_config SET pro_value = ? WHERE pro_key = 'title'", value);
						ConfigInfo.title = value;
					} catch (SQLException e) {
						logger.error(e);
					}
				}else if(key.equalsIgnoreCase("examineeDays")){
					try {
						genericJdbcDao.executeDMLByArray("UPDATE t_config SET pro_value = ? WHERE pro_key = 'examineeDays'", value);
						ConfigInfo.examineeDays = Integer.parseInt(value);
					} catch (SQLException e) {
						logger.error(e);
					}
				}
			}
		}
	}
	
	public Map<String,String> getConfig(){
		Collection<Map<String, Object>> configs = genericJdbcDao.query("SELECT pro_key,pro_value FROM t_config");
		Map<String,String> config = new HashMap<String, String>();
		for(Map<String, Object> c : configs){
			String key = (String) c.get("pro_key");
			String value = (String) c.get("pro_value");
			if(key.equalsIgnoreCase("themeName")){
				ConfigInfo.themeName = value;
			}else if(key.equalsIgnoreCase("title")){
				ConfigInfo.title = value;
			}else if(key.equalsIgnoreCase("examineeDays")){
				ConfigInfo.examineeDays = Integer.parseInt(value);
			}
		}
		return config;
	}

}
