package com.wisdom.core.logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wisdom.core.security.domain.User;
import com.wisdom.core.security.resource.SecurityUtils;
import com.wisdom.core.utils.DateUtils;
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
	
	private static Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);
	
	public static final String SEPARATOR="##";
	
	public static int maxCacheValue=150;
	
	private LoggerThreadService loggerThreadService;
	
	/**
	 * 拦截请求信息
	 */
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		String url=request.getRequestURL().toString();
		String[] keywords=getKeyword(url).split("/");
		if(!isCurrentLogger(keywords)){
			return super.preHandle(request, response, handler);
		}
		String ip=request.getRemoteAddr();
		String log=DateUtils.getCurrentDateTime().getTime()+SEPARATOR;
		User user=SecurityUtils.getCurrentUser();
		if(user!=null){
			log=log.concat("登录名:".concat(user.getUsername()).concat(" 姓名:".concat(user.getCnname())).concat(" IP:".concat(ip)).concat(" 访问了:".concat(url)));
		}else{
			log=log.concat("IP:".concat(ip).concat(" 访问了:".concat(url)));
		}
		LoggerCache.putCache(log);
		logger.info(log);
		if(LoggerCache.isFull()){
			ScheduledThreadUtils.start(loggerThreadService);
		}
		return super.preHandle(request, response, handler);
	}
	
	/**
	 * 判断当前关键字是不是被允许
	 * @param keywords关键字集
	 * @return 是否允许
	 */
	private static boolean isCurrentLogger(String... keywords){
		if(LoggerCache.currentLoggerLever.equals("!!")){
			return false;
		}else if(LoggerCache.currentLoggerLever.equals("**")){
			return true;
		}
		if(keywords!=null&&keywords.length>0){
			for(int i=4;i<keywords.length;i++){
				if(LoggerCache.currentLoggerLever.indexOf(keywords[i])!=-1){
					return true;
				}
			}
		}
		return false;
	}
	
	private static String getKeyword(String url){
		return url.replace(".htm", "");
	}
	
	public LoggerThreadService getLoggerThreadService() {
		return loggerThreadService;
	}

	public void setLoggerThreadService(LoggerThreadService loggerThreadService) {
		this.loggerThreadService = loggerThreadService;
	}

	public void setMaxCacheValue(int maxCacheValue) {
		LoggerInterceptor.maxCacheValue = maxCacheValue;
	}
	
}
