package com.wisdom.core.logger;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wisdom.core.logger.domain.AbstractMatch;
import com.wisdom.core.logger.domain.Logger;
import com.wisdom.core.logger.domain.LoggerSomething;
import com.wisdom.core.logger.domain.LoggerSomewhere;
import com.wisdom.core.utils.ScheduledThreadUtils;
/**
 * <b>业务说明</b>:<br>
 *	日志拦截器 
 *<br><b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: 2009-12-3<br>
 * <b>建立时间</b>: 上午11:21:17<br>
 * <b>项目名称</b>: spring<br>
 * <b>包及类名</b>: com.wisdom.core.logger.LoggerInterceptor.java<br>
 *//*implements WebArgumentResolver*/ 
public class LoggerInterceptor extends HandlerInterceptorAdapter{
	public static boolean enabled;
	
	private LoggerThreadService loggerThreadService;
	
	/**
	 * 拦截请求信息
	 */
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		String url=request.getRequestURL().toString();
		if(!enabled){
			return super.preHandle(request, response, handler);
		}
		analysisUrl(url,request.getMethod());
		if(LoggerCache.isFull()){
			ScheduledThreadUtils.start(loggerThreadService);
		}
		return super.preHandle(request, response, handler);
	}
	
	/**
	 * 分析当前地址是否计入日志
	 * @param url地址
	 */
	private static void analysisUrl(String url,String method){
		Collection<AbstractMatch> matchs = MatchCache.getAll();
		int sign = 0;
		Logger logger = new Logger();
		logger.setUrl(url);
		for(AbstractMatch match : matchs){
			if(sign>1){
				break;
			}else if(!match.isEnabled()){
				break;
			}else if(url.indexOf(match.getKeyword())>-1){
				if(match instanceof LoggerSomewhere){
					logger.setSomewhere(match.getName());
					sign++;
				}else if(match instanceof LoggerSomething){
					logger.setSomething(match.getName());
					sign++;
				}
			}else if(match.getKeyword().equalsIgnoreCase(method)){
				logger.setSomething(match.getName());
				sign++;
			}
		}
		if(sign>1){
			LoggerCache.putCache(logger);
		}
	}
	
	public LoggerThreadService getLoggerThreadService() {
		return loggerThreadService;
	}

	public void setLoggerThreadService(LoggerThreadService loggerThreadService) {
		this.loggerThreadService = loggerThreadService;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		LoggerInterceptor.enabled = enabled;
	}

}
