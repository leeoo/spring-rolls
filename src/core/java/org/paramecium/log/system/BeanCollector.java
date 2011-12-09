package org.paramecium.log.system;

import java.lang.reflect.Method;
import java.util.Collection;

import org.paramecium.cache.Cache;
import org.paramecium.cache.CacheManager;
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
public class BeanCollector<STR extends Object> implements Collector<STR>{
	
	private final static Log logger$ = LoggerFactory.getLogger();
	public static String[] notLogBeans;
	
	private final static String undefin = "(无描述)";

	@SuppressWarnings("unchecked")
	private final static Cache<String,String> beanLogCache = CacheManager.getDefaultCache("CACHE_BEAN_LOG");

	public synchronized Collection<String> getAll() {
		if(!LogConfig.beanLogCollector){
			return null;
		}
		Collection<String> logs = beanLogCache.getKeys();
		beanLogCache.clear();
		logger$.debug("BEAN log cache is claer!");
		return logs;
	}
	
	public static String getLog(Class<?> clazz,Method method){
		if(LogConfig.beanLogCollector){
			ShowLabel showLabel = clazz.getAnnotation(ShowLabel.class);;
			ShowLabel showLabel2 = method.getAnnotation(ShowLabel.class);;
			String cnClazz = undefin;
			if(showLabel!=null){
				cnClazz = "(".concat(showLabel.value()).concat(")");
			}
			String cnMethod = undefin;
			if(showLabel2!=null){
				cnMethod = "(".concat(showLabel2.value()).concat(")");
			}
			StringBuffer sb = new StringBuffer();
			sb.append("Class:").append(clazz.getName()).append(cnClazz).append(" Method:").append(method.getName()).append(cnMethod);
			return sb.toString();
		}
		return null;
	}

	public synchronized void put(STR log) {
		if(LogConfig.beanLogCollector&&log!=null){
			if(notLogBeans!=null&&notLogBeans.length>0){
				for(String bean : notLogBeans){
					if(log.toString().toLowerCase().indexOf(bean)>0){
						return;
					}
				}
			}
			StringBuffer logger = new StringBuffer();
			logger.append(DateUtils.getCurrentDateTimeStr()).append("|");
			UserDetails<?> user = SecurityThread.getUserNotException();
			String username = "匿名用户";
			if(user!=null){
				username = user.getUsername();
			}
			logger.append(username).append("|");
			logger.append(log);
			if(CollectorFactory.logCollector!=null){
				CollectorFactory.logCollector.putBeanLog(LogInfoUtils.getLog(logger.toString()));
			}else{
				beanLogCache.put(LogInfoUtils.getLog(logger.toString()), null);
			}
			logger$.debug(logger.toString());
		}
	}

}
