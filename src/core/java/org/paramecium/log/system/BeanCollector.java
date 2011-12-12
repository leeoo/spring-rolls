package org.paramecium.log.system;

import java.lang.reflect.Method;

import org.paramecium.commons.DateUtils;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.log.Log;
import org.paramecium.log.LogConfig;
import org.paramecium.log.LogInfoUtils;
import org.paramecium.log.LoggerFactory;
import org.paramecium.security.SecurityThread;
import org.paramecium.security.UserDetails;

/**
 * 功 能 描 述:<br>
 * Bean的调用过程日志
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-9-15上午11:02:26
 * <br>项 目 信 息:paramecium:org.paramecium.log.system.BeanCollector.java
 */
public class BeanCollector implements Collector{
	
	private final static Log logger$ = LoggerFactory.getLogger();
	public static String[] notLogBeans;

	public static String getLog(Class<?> clazz,Method method){
		if(LogConfig.beanLogCollector){
			ShowLabel showLabel = clazz.getAnnotation(ShowLabel.class);;
			ShowLabel showLabel2 = method.getAnnotation(ShowLabel.class);;
			String cnClazz = UNDEFIN;
			if(showLabel!=null){
				cnClazz = "(".concat(showLabel.value()).concat(")");
			}
			String cnMethod = UNDEFIN;
			if(showLabel2!=null){
				cnMethod = "(".concat(showLabel2.value()).concat(")");
			}
			StringBuffer sb = new StringBuffer();
			sb.append("Class:").append(clazz.getName()).append(cnClazz).append(" Method:").append(method.getName()).append(cnMethod);
			return sb.toString();
		}
		return null;
	}

	public synchronized void put(String log) {
		if(LogConfig.beanLogCollector&&log!=null){
			if(notLogBeans!=null&&notLogBeans.length>0){
				for(String bean : notLogBeans){
					if(log.toLowerCase().indexOf(bean)>0){
						return;
					}
				}
			}
			if(CollectorFactory.logCollector!=null){
				StringBuffer logger = new StringBuffer();
				logger.append(DateUtils.getCurrentDateTimeStr()).append('|');
				UserDetails<?> user = SecurityThread.getUserNotException();
				String username = ANONYMOUS;
				if(user!=null){
					username = user.getUsername();
				}
				logger.append(username).append('|');
				logger.append(log);
				CollectorFactory.logCollector.putBeanLog(LogInfoUtils.cut(logger.toString()));
				logger$.debug(logger.toString());
			}
		}
	}

}
