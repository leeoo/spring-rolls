package org.paramecium.log.system;

import javax.servlet.http.HttpServletRequest;

import org.paramecium.commons.DateUtils;
import org.paramecium.log.Log;
import org.paramecium.log.LogConfig;
import org.paramecium.log.LogInfoUtils;
import org.paramecium.log.LoggerFactory;
import org.paramecium.security.SecurityThread;
import org.paramecium.security.UserDetails;

/**
 * 功 能 描 述:<br>
 * 记录WEB请求日志
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-9-15上午10:29:48
 * <br>项 目 信 息:paramecium:org.paramecium.log.system.WebCollector.java
 */
public class WebCollector<Request extends Object> implements Collector<Request>{
	
	private final static Log logger$ = LoggerFactory.getLogger();
	
	public synchronized void put(Request request) {
		if(LogConfig.webLogCollector){
			if(CollectorFactory.logCollector!=null){
				HttpServletRequest rq = (HttpServletRequest)request;
				StringBuffer logger = new StringBuffer();
				logger.append(DateUtils.getCurrentDateTimeStr()).append("|");
				logger.append(rq.getRemoteAddr()).append("|");
				UserDetails<?> user = SecurityThread.getUserNotException();
				String username = "匿名用户";
				if(user!=null){
					username = user.getUsername();
				}
				logger.append(username).append("|");
				logger.append(rq.getRequestURI());
				CollectorFactory.logCollector.putWebLog(LogInfoUtils.cut(logger.toString()));
				logger$.debug(logger.toString());
			}
		}
	}

}
