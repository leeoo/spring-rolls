package org.paramecium.security.exception;
/**
 * 功能描述(Description):<br><b>
 * 匿名访问错误
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-24上午09:41:48</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.security.exception.AnonymousException.java</b>
 */
public final class AnonymousException extends SecurityException{

	private static final long serialVersionUID = 6492898999740339810L;

	public AnonymousException(String message) {
   	 	super(message);
	}
	
}
