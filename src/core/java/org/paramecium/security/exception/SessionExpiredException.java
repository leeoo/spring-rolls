package org.paramecium.security.exception;
/**
 * 功 能 描 述:<br>
 * session过期异常
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-25下午01:00:44
 * <br>项 目 信 息:paramecium:org.paramecium.security.exception.SessionExpiredException.java
 */
public final class SessionExpiredException extends SecurityException{

	private static final long serialVersionUID = 6803062953080817385L;

	public SessionExpiredException(String message) {
   	 	super(message);
	}
	
}
