package org.paramecium.security.exception;
/**
 * 功 能 描 述:<br>
 * 用户重复登录产生异常
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-22下午02:43:42
 * <br>项 目 信 息:paramecium:org.paramecium.security.exception.UserKickException.java
 */
public final class UserKickException extends SecurityException {

	private static final long serialVersionUID = 8572271952469494127L;
	
	public UserKickException(String message) {
   	 	super(message);
	}

}
