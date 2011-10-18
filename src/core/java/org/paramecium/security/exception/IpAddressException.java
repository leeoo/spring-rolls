package org.paramecium.security.exception;

/**
 * 功 能 描 述:<br>
 * IP地址错误
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-10-18上午09:00:59
 * <br>项 目 信 息:paramecium:org.paramecium.security.exception.IpAddressException.java
 */
public final class IpAddressException extends SecurityException{

	private static final long serialVersionUID = -4095495686454365202L;

	public IpAddressException(String message) {
   	 	super(message);
	}
	
}
