package com.exam.entity.exam;

import java.util.Collection;

import org.paramecium.cache.Cache;
import org.paramecium.cache.CacheManager;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;

/**
 * 考试现场缓存
 * @author caoyang
 */
public class ExamingCache {
	private final static Log logger = LoggerFactory.getLogger();
	@SuppressWarnings("unchecked")
	private static Cache<Integer, ExamSession> examingCache = (Cache<Integer, ExamSession>) CacheManager.getCacheByType("EXAMING", 100);
	
	
	public static void removeExamSession(Integer id){
		if(id!=null){
			ExamSession examSession = examingCache.get(id);
			if(examSession!=null){
				examingCache.remove(id);
				logger.info("考试为：<"+examSession.getId()+":"+examSession.getTitle()+">已经移除缓存！");
			}
		}
	}
	
	public static void addExamSession(ExamSession examSession){
		if(examSession==null){
			return;
		}
		if(!isExist(examSession.getId())){
			examingCache.put(examSession.getId(), examSession);
			logger.info("考试为：<"+examSession.getId()+":"+examSession.getTitle()+">已经加入缓存！");
		}
	}
	
	public static boolean isExist(Integer id){
		if(id==null){
			return true;//空就不要放了，就告诉你存在了
		}
		return examingCache.get(id)==null?false:true;
	}
	
	public static ExamSession getExamSession(Integer id){
		if(id==null){
			return null;
		}
		return examingCache.get(id);
	}
	
	public static Collection<ExamSession> getExamSessions(){
		return examingCache.getValues();
	}
	
}
