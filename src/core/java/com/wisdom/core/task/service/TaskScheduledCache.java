package com.wisdom.core.task.service;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.dao.DataRetrievalFailureException;

import com.wisdom.core.task.domain.Task;
/**
 * 功能描述：任务调度缓存
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-11-2</b>
 * <br>创建时间：<b>上午11:32:13</b>
 * <br>文件结构：<b>spring:com.wisdom.core.task.service/TaskScheduledCache.java</b>
 */
public class TaskScheduledCache {
	private final static String TASK_CACHE_NAME="task_scheduled";
	private static Cache cache=CacheManager.getInstance().getCache(TASK_CACHE_NAME);
	
	public synchronized static void putCache(Task task){
		task.setDescription(null);
		Element element=new Element(task.getId(), task);
		cache.put(element);
	}  

	public synchronized static Task getCache(Long id){
		Element element = null;
		try {
			element = cache.get(id);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("TaskCache failure: " + cacheException.getMessage(), cacheException);
		}
		if (element == null) {
			return null;
		} else {
			return (Task) element.getValue();
		}
	}  

	public synchronized static void removeCache(Long id){
		cache.remove(id);
	}  

	public synchronized static void removeAllCache(){
		cache.removeAll();
		cache.clearStatistics();
		cache.flush();
	}  

	@SuppressWarnings("unchecked")
	public synchronized static Collection<Task> getAllCache(){
		Collection<Long> tasks;
		Collection<Task> tasklist = new ArrayList<Task>();
		try {
			tasks = cache.getKeys();
		} catch (IllegalStateException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (CacheException e) {
			throw new UnsupportedOperationException(e.getMessage(), e);
		}
		for (Long taskId:tasks) {
			Task task = getCache(taskId);
			tasklist.add(task);
		}
		return tasklist;
	}
	
}
