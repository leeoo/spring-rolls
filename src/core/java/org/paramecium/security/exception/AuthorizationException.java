package org.paramecium.security.exception;
/**
 * 功能描述(Description):<br><b>
 * 授权异常
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-24上午09:34:14</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.security.exception.AuthorizationException.java</b>
 */
public final class AuthorizationException extends SecurityException{

	private static final long serialVersionUID = 2705429570579315374L;
	
	public AuthorizationException(String message) {
   	 	super(message);
	}

}
