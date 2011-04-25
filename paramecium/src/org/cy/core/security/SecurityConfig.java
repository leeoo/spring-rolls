package org.cy.core.security;
/**
 * 功 能 描 述:<br>
 * 安全配置
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-25上午11:11:19
 * <br>项 目 信 息:paramecium:org.cy.core.security.SecurityConfig.java
 */
public class SecurityConfig {
	
	/**
	 * 是否开启IOC容器安全控制
	 */
	public static boolean iocSecurity = false;
	
	/**
	 * 是否启动session并发控制
	 */
	public static boolean sessionControl = false;
	
	/**
	 * 匿名用户错误页面
	 */
	public static String anonymousExceptionPage;

	/**
	 * 未授权错误页面
	 */
	public static String authorizationExceptionPage;

	/**
	 * 用户踢出错误页面
	 */
	public static String userKickExceptionPage;

	/**
	 * 用户失效错误页面
	 */
	public static String userDisabledExceptionPage;
	
}
