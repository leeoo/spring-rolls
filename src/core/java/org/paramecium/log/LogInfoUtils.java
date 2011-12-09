package org.paramecium.log;
/**
 * 功能描述(Description):<br><b>
 * 日志信息截断，如果超过数据库信息，容易引发异常，从而在某些收集器中出现死循环。
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-12-9下午06:15:23</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.log.LogInfoUtils.java</b>
 */
public abstract class LogInfoUtils {
	
	public static String cut(String log){
		if(log.length()>LogConfig.logLength){
			return log.substring(0,LogConfig.logLength);
		}
		return log;
	}

}
