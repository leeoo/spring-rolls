package org.paramecium.thread;
/**
 * 线程状态
 * @author caoyang
 */
public enum ThreadStatus {
	/**
	 * 活动中
	 */
	ACTIVE,
	/**
	 * 闲置中
	 */
	IDLE,
	
	/**
	 * 死掉了
	 */
	ERROR,
	/**
	 * 死掉了
	 */
	DEAD,
	
}
