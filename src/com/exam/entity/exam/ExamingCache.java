package com.exam.entity.exam;

import java.util.Collection;

import org.paramecium.cache.Cache;
import org.paramecium.cache.CacheManager;

/**
 * 考试现场缓存
 * @author caoyang
 */
public class ExamingCache {
	
	@SuppressWarnings("unchecked")
	private static Cache<Integer, ExamSession> examingCache = (Cache<Integer, ExamSession>) CacheManager.getDefaultCache("EXAMING", 100);
	
	public static void removeExamSession(Integer id){
		if(id!=null){
			examingCache.remove(id);
		}
	}
	
	public static void addExamSession(ExamSession examSession){
		if(examSession==null){
			return;
		}
		if(!isExist(examSession.getId())){
			examingCache.put(examSession.getId(), examSession);
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
