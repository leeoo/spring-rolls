package org.paramecium.jdbc.datasource;

/**
 * 功 能 描 述:<br>
 * 数据源模型状态
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2012-3-20上午11:21:33
 * <br>项 目 信 息:paramecium:org.paramecium.jdbc.datasource.DataSourceMode.java
 */
public enum DataSourceMode {
	/**
	 * 数据源构建时连接池已满
	 */
	FULL,
	/**
	 * 数据源构建时出现错误
	 */
	ERROR,
	/**
	 * 构建成功
	 */
	SUCCESS
}
