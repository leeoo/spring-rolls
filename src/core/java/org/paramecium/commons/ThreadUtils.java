package org.paramecium.commons;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.thread.RunnableMonitor;
import org.paramecium.thread.ThreadListener;

/**
 * 线程工具类
 * @author caoyang
 */
public abstract class ThreadUtils {
	private final static Log logger = LoggerFactory.getLogger();
	private static int count = 0;
	private final static ExecutorService execute = Executors.newFixedThreadPool(32);
	private final static Collection<RunnableMonitor> startedThreads = new LinkedList<RunnableMonitor>();
	private final static Collection<RunnableMonitor> shutdownThreads = new LinkedList<RunnableMonitor>();

	/**
	 * 加入一个线程，并监控
	 * @param runnable
	 * @param name
	 */
	public static void add(RunnableMonitor runnable,String name) {
		if(count<32){
			if(isNotExist(runnable)){
				runnable.addObserver(new ThreadListener());
			}
			execute.execute(runnable);
			logger.info("["+name+"]线程被放入线程池!");
			count++;
			startedThreads.add(runnable);
			return;
		}
		logger.warn("ExecutorService线程池超过峰值32，,可能会影响整体性能，请重新优化设计您的线程!");
	}
	
	/**
	 * 加入一个线程，并监控
	 * @param runnable
	 */
	public static void add(RunnableMonitor runnable) {
		add(runnable,runnable.getClass().getName());
	}
	
	/**
	 * 获得已经启动过的线程
	 * @return
	 */
	public static Collection<RunnableMonitor> getStartedThreads(){
		return startedThreads;
	}
	
	/**
	 * 获得已经停止的线程
	 * @return
	 */
	public static Collection<RunnableMonitor> getShutdownThreads(){
		return shutdownThreads;
	}
	
	/**
	 * 重新启动某线程
	 * @param hashcode
	 */
	public static void restart(int hashcode){
		RunnableMonitor shutdownThread = null;
		for(RunnableMonitor thread : shutdownThreads){
			if(thread.hashCode()==hashcode){
				add(thread);
				shutdownThread = thread;
			}
		}
		if(shutdownThread!=null){//防止java.util.ConcurrentModificationException
			shutdownThreads.remove(shutdownThread);
		}
	}
	
	/**
	 * 停止某线程
	 * @param hashcode
	 */
	public static void shutdown(int hashcode) {
		RunnableMonitor startedThread = null;
		for(RunnableMonitor thread : startedThreads){
			if(thread.hashCode()==hashcode){
				thread.shutdown();
				shutdownThreads.add(thread);
				startedThread = thread;
			}
		}
		if(startedThread!=null){//防止java.util.ConcurrentModificationException
			startedThreads.remove(startedThread);
		}
	}
	
	/**
	 * 停止所有线程
	 */
	public static void shutdownAll() {
		if(execute.isTerminated()){
			logger.info("所有线程已经执行完毕!");
		}else{
			logger.warn("线程池中含有未执行完毕的线程!");
		}
		for(RunnableMonitor thread : startedThreads){
			thread.shutdown();
			shutdownThreads.add(thread);
		}
		startedThreads.clear();
		if(!execute.isShutdown()){
			execute.shutdownNow();
			logger.info("线程池被强制停止!");
		}
	}

	/**
	 * 判断是否在与线程监听器中
	 * @param runnable
	 * @return
	 */
	private static boolean isNotExist(RunnableMonitor runnable){
		boolean isExist = true;
		for(RunnableMonitor thread : startedThreads){
			if(thread.hashCode() == runnable.hashCode()){
				return false;
			}
		}
		for(RunnableMonitor thread : shutdownThreads){
			if(thread.hashCode() == runnable.hashCode()){
				return false;
			}
		}
		return isExist;
	}

}
