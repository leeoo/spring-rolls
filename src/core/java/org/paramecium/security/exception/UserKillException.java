package org.paramecium.security.exception;
/**
 * 功 能 描 述:<br>
 * 用户被手工踢出产生异常
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-22下午02:43:42
 * <br>项 目 信 息:paramecium:org.paramecium.security.exception.UserKillException.java
 */
public final class UserKillException extends SecurityException {

	private static final long serialVersionUID = -152456582315389124L;

	public UserKillException(String message) {
   	 	super(message);
	}

}
