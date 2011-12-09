package com.demo.web.system;

import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.log.Log;
import org.paramecium.log.LogConfig;
import org.paramecium.log.LoggerFactory;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.demo.web.BaseController;

@Security
@ShowLabel("系统日志配置")
@Controller("/system/config/log")
public class LogConfigController extends BaseController{
	
	private final static Log logger = LoggerFactory.getLogger();
	
	@ShowLabel("首页界面")
	@MappingMethod
	public ModelAndView list(ModelAndView mv){
		mv.addValue("levels", LoggerFactory.getLoggerLevel());
		mv.addValue("isConsole", LogConfig.isConsole);
		mv.addValue("isFile", LogConfig.isFile);
		mv.addValue("isDb", LogConfig.isDb);
		mv.addValue("consoleLoggerLevel", LogConfig.consoleLoggerLevel);
		mv.addValue("fileLoggerLevel", LogConfig.fileLoggerLevel);
		mv.addValue("dbLoggerLevel", LogConfig.dbLoggerLevel);
		mv.addValue("loggerFileName", LogConfig.loggerFileName);
		mv.addValue("loggerFileMax", LogConfig.loggerFileMax);
		mv.addValue("sqlIsFormat", LogConfig.sqlIsFormat);
		mv.addValue("jdbcLogCollector", LogConfig.jdbcLogCollector);
		mv.addValue("beanLogCollector", LogConfig.beanLogCollector);
		mv.addValue("webLogCollector", LogConfig.webLogCollector);
		mv.addValue("logLength", LogConfig.logLength);
		return mv.forward(getPage("/log/list.jsp"));
	}
	
	/**
	 * 需要持久化需另行开发
	 * @param mv
	 * @return
	 */
	@ShowLabel("保存配置")
	@MappingMethod
	public ModelAndView save(ModelAndView mv){
		try{
			LogConfig.isConsole = mv.getValue("isConsole", boolean.class);
			LogConfig.isFile = mv.getValue("isFile", boolean.class);
			LogConfig.isDb = mv.getValue("isDb", boolean.class);
			LogConfig.consoleLoggerLevel = mv.getValue("consoleLoggerLevel", int.class);
			LogConfig.fileLoggerLevel = mv.getValue("fileLoggerLevel", int.class);
			LogConfig.dbLoggerLevel = mv.getValue("dbLoggerLevel", int.class);
			LogConfig.loggerFileName = mv.getValue("loggerFileName", String.class);
			LogConfig.loggerFileMax = mv.getValue("loggerFileMax", int.class);
			LogConfig.sqlIsFormat = mv.getValue("sqlIsFormat", boolean.class);
			LogConfig.jdbcLogCollector = mv.getValue("jdbcLogCollector", boolean.class);
			LogConfig.beanLogCollector = mv.getValue("beanLogCollector", boolean.class);
			LogConfig.webLogCollector = mv.getValue("webLogCollector", boolean.class);
			LogConfig.logLength = mv.getValue("logLength", int.class);
			mv.setSuccessMessage("保存成功!");
		}catch (Exception e) {
			logger.error(e);
			mv.setErrorMessage("保存失败!");
		}
		return mv.redirect(getRedirect("/system/config/log/list"));
	}

}
