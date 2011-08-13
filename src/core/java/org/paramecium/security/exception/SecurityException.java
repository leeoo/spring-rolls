package org.paramecium.security.exception;

/**
 * 功能描述(Description):<br><b>
 * 安全错误
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-8-13下午05:41:26</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.security.exception.SecurityException.java</b>
 */
public abstract class SecurityException extends RuntimeException{

	private static final long serialVersionUID = 1114271706622982841L;

	public SecurityException(String message) {
   	 	super(message);
	}
	
}
