package org.paramecium.log;

public abstract class LogInfoUtils {
	
	public static String getLog(String log){
		if(log.length()>LogConfig.logLength){
			return log.substring(0,LogConfig.logLength);
		}
		return log;
	}

}
