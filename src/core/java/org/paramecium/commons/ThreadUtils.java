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
			runnable.addObserver(new ThreadListener());
			System.out.println(runnable.hashCode()+"|=============================================================");
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
		for(RunnableMonitor thread : shutdownThreads){
			if(thread.hashCode()==hashcode){
				add(thread);
				shutdownThreads.remove(thread);
			}
		}
	}
	
	/**
	 * 停止某线程
	 * @param hashcode
	 */
	public static void shutdown(int hashcode) {
		for(RunnableMonitor thread : startedThreads){
			if(thread.hashCode()==hashcode){
				thread.shutdown();
				startedThreads.remove(thread);
				shutdownThreads.add(thread);
			}
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
			startedThreads.remove(thread);
			shutdownThreads.add(thread);
		}
		startedThreads.clear();
		if(!execute.isShutdown()){
			execute.shutdownNow();
			logger.info("线程池被强制停止!");
		}
	}


}
