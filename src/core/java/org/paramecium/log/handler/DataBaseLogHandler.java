package org.paramecium.log.handler;

import org.paramecium.log.LogHandler;
import org.paramecium.log.LogInfoUtils;
/**
 * 功 能 描 述:<br>
 * 日志存入数据库
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-2下午04:08:06
 * <br>项 目 信 息:paramecium:org.paramecium.log.handler.DataBaseLogHandler.java
 */
public class DataBaseLogHandler implements LogHandler{
	
	private static LoggerDB loggerDB = null;
	
	public static void setDbInterface(String loggerDbInterface){
		if(loggerDbInterface==null||loggerDbInterface.isEmpty()||loggerDB!=null){
			return;
		}
		try{
			Class<?> clazz = Class.forName(loggerDbInterface);
			loggerDB = (LoggerDB) clazz.newInstance();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void log(String message, boolean isError) {
		if(isError){
			if(loggerDB!=null){
				loggerDB.saveErrorLogger(LogInfoUtils.cut(message));
			}
		}else{
			if(loggerDB!=null){
				loggerDB.saveLogger(LogInfoUtils.cut(message));
			}
		}
	}

}
