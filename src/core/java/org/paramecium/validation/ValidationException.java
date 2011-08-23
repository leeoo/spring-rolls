package org.paramecium.validation;


/**
 * 功能描述(Description):<br><b>
 * 自定义验证错误异常
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-8-22下午09:46:26</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.validation.ValidationException.java</b>
 */
public class ValidationException extends RuntimeException{

	private static final long serialVersionUID = -918381558310080077L;
	
	public ValidationException(String message) {
   	 	super(message);
	}

}
