package org.paramecium.validation;

import java.util.Collection;

/**
 * 功能描述(Description):<br><b>
 * 验证工具
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-8-22下午09:46:43</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.validation.ValidationUtils.java</b>
 */
public class ValidationUtils {
	
	/**
	 * 验证同时获得错误信息列表，如果没有错误，返回null
	 * @param bean
	 * @return
	 */
	public static Collection<String> getErrorMessages(Object bean){
		return null;
	}
	
	/**
	 * 直接验证，并抛出自定义验证异常
	 * @param bean
	 * @throws ValidationException
	 */
	public static void validation(Object bean) throws ValidationException{
		
	}
	
	
	
}
