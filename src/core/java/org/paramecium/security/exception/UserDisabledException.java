package org.paramecium.security.exception;
/**
 * 功 能 描 述:<br>
 * 账号被冻结
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-22下午02:46:51
 * <br>项 目 信 息:paramecium:org.paramecium.security.exception.UserDisabledException.java
 */
public final class UserDisabledException extends SecurityException{

	private static final long serialVersionUID = 4932103474518535972L;
	
	public UserDisabledException(String message) {
   	 	super(message);
	}

}
