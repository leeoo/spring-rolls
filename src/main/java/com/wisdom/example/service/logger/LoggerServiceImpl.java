package com.wisdom.example.service.logger;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisdom.core.logger.LoggerInterceptor;
import com.wisdom.core.logger.LoggerService;
import com.wisdom.core.utils.Page;
import com.wisdom.example.dao.JdbcGenericSupportDao;
import com.wisdom.example.dao.logger.LoggerDao;

/**
 * <b>业务说明</b>:<br>
 * 日志操作业务类
 * <br><b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: 2009-12-3<br>
 * <b>建立时间</b>: 上午11:43:50<br>
 * <b>项目名称</b>: spring<br>
 * <b>包及类名</b>: com.wisdom.core.logger.serviceLoggerService.java<br>
 */
@Service
@Transactional
public class LoggerServiceImpl extends JdbcGenericSupportDao implements LoggerService,LoggerDao{
	
	/**
	 * 批量保存日志信息
	 * @param loggers
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void saveLoggers(Collection<String> loggers) throws Exception{
		Collection<Map> datas=new LinkedList<Map>();
		Map data=null;
		Date date=null;
		for(String log:loggers){
			String[] logs=log.split(LoggerInterceptor.SEPARATOR);
			if(logs!=null&&logs.length==2){
				data=new HashMap();
				date=new Date(Long.valueOf((logs[0])));
				data.put("logDate",date);
				data.put("logInfo", logs[1]);
				datas.add(data);
			}
		}
		if(!datas.isEmpty()){
			jdbcDao.executeBatchByMaps(SQL_INSERT_LOGGER, datas);
		}
	}
	
	/**
	 * 删除日志信息
	 * @param id
	 * @throws Exception
	 */
	public void deleteLogger(Long id)throws Exception{
		jdbcDao.executeArray(SQL_DELETE_LOGGER, id);
	}
	
	/**
	 * 获得所有分页的日志信息
	 * @param page
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page getAllLoggers(Page page){
		return jdbcDao.findPageListMap(SQL_SELECT_ALL_LOGGER, page);
	}
	
	
}
