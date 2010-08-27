package com.wisdom.core.task;
/**
 * 功能描述：任务调度所需实现接口
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-11-2</b>
 * <br>创建时间：<b>上午11:22:49</b>
 * <br>文件结构：<b>spring:com.wisdom.core.task/TaskRunnable.java</b>
 */
public interface TaskRunnable{

	public void execute() throws Exception;
	
	/**
	 * 天为运行单位
	 */
	public static final int RUNUNIT_DAY = 0;
	/**
	 * 周为运行单位
	 */
	public static final int RUNUNIT_WEEK = 1;
	/**
	 * 月为运行单位
	 */
	public static final int RUNUNIT_MONTH = 3;
	/**
	 * 年为运行单位
	 */
	public static final int RUNUNIT_YEAR = 5;

	/**
	 * 小时为运行单位
	 */
	public static final int RUNUNIT_HOUR = 12;
	
	/**
	 * 状态为可用
	 */
	public static final boolean STATE_ENABLED = true;
	/**
	 * 状态为停用
	 */
	public static final boolean STATE_DISABLED = false;
	/**
	 * 运行成功
	 */
	public static final int RUNSTATE_SUCCESS = 1;
	/**
	 * 运行失败
	 */
	public static final int RUNSTATE_FAILURE = 0;
	/**
	 * 手动运行
	 */
	public static final int RUNTYPE_MANUAL = 1;
	/**
	 * 自动运行
	 */
	public static final int RUNTYPE_AUTO = 0;
	
}
