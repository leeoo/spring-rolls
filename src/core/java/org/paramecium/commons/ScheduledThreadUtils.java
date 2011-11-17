package org.paramecium.commons;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
/**
 * 功 能 描 述:<br>
 * 多线程多任务周期性定时运行工具
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-11-9上午09:09:56
 * <br>项 目 信 息:paramecium:org.paramecium.commons.ScheduledThreadUtils.java
 */
public abstract class ScheduledThreadUtils {
	
	private static final Log logger = LoggerFactory.getLogger(); 
	
	/**
	 * 定时运行
	 * @param delay间隔时长
	 * @param timeUnit运行周期
	 * @param isStartRun是否启动时马上开始运行
	 * @param runnables任务列表
	 */
	public static void start(long delay,TimeUnit timeUnit,boolean isStartRun,Collection<Runnable> runnables){
		final ScheduledExecutorService scheduler= Executors.newScheduledThreadPool(runnables.size());
		logger.info("初始化线程池数量为:"+runnables.size());
		long start = isStartRun?0L:delay;
		for(Runnable runnable:runnables){
			try{
				scheduler.scheduleWithFixedDelay(runnable, start, delay, timeUnit);
				logger.info("正在执行中hashCode:"+runnable.hashCode());
			}catch (Exception e) {
				logger.error("线程执行失败hashCode:"+runnable.hashCode());
				logger.error(e);
			}
		}
		//scheduler.shutdown();
	}
	
	/**
	 * 定时运行
	 * @param delay间隔时长
	 * @param timeUnit运行周期
	 * @param isStartRun是否启动时马上开始运行
	 * @param runnables任务列表
	 */
	public static void start(long delay,TimeUnit timeUnit,boolean isStartRun,Runnable runnable){
		Collection<Runnable> rs= new LinkedList<Runnable>();
		rs.add(runnable);
		start(delay, timeUnit,isStartRun,rs);
	}
	
	/**
	 * 定时运行
	 * @param delay间隔时长
	 * @param timeUnit运行周期
	 * @param isStartRun是否启动时马上开始运行
	 * @param runnables任务列表
	 */
	public static void start(long delay,TimeUnit timeUnit,boolean isStartRun,Runnable... runnables){
		Collection<Runnable> rs= new LinkedList<Runnable>();
		for(Runnable runnable:runnables){
			rs.add(runnable);
		}
		start(delay, timeUnit,isStartRun,rs);
	}
	
}
