package org.paramecium.thread;

import java.util.Observable;
/**
 * 线程监控
 * @author 曹阳
 *
 */
public abstract class RunnableMonitor extends Observable implements Runnable{
	/**
	 * 获得当前线程状态
	 * @return
	 */
	abstract public ThreadStatus getThreadStatus();
	
	/**
	 * 停止当前线程
	 */
	abstract public void shutdown();
	
	/**
	 * 获得线程实例的hashcode
	 */
	abstract public int getHashCode();
}
