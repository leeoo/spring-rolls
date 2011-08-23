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
	
	/**
	 * 验证邮箱地址格式是否正确
	 * @param value
	 * @return
	 */
	public static boolean isEmail(String value){
		return value.matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
	}
	
	/**
	 * 验证座机电话号码是否正确
	 * @param value
	 * @return
	 */
	public static boolean isTel(String value){
		return value.matches("^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$");
	}
	
	/**
	 * 验证手机号码是否正确
	 * @param value
	 * @return
	 */
	public static boolean isMobile(String value){
		 return value.matches("^[1][3,5,8]+\\d{9}");
	}
	
	/**
	 * 验证中文是否正确
	 * @param value
	 * @return
	 */
	public static boolean isChinese(String value){
		 return value.matches("^[\u4e00-\u9fa5]+$");
	}
	
	/**
	 * 验证中文是否正确
	 * @param value
	 * @return
	 */
	public static boolean isUrl(String value){
		boolean y1 = value.matches("[http|https]+[://]+[0-9A-Za-z:/[-]_#[?][=][.]]*");
		boolean y2 = value.matches("[0-9A-Za-z:/[-]_#[?][=][.]]*");
		 return y1||y2;
	}

	/**
	 * 验证登录名是否合法
	 * @param value
	 * @return
	 */
	public static boolean isUsername(String value){
		return value.matches("[a-zA-Z][a-zA-Z0-9_]{4,15}$");
	}

	/**
	 * 验证身份证
	 * @param value
	 * @return
	 */
	public static boolean isIdCard(String value){
		return value.matches("^\\d{15}(\\d{2}[A-Za-z0-9])?$");
	}
	
	/**
	 * 验证邮政编码
	 * @param value
	 * @return
	 */
	public static boolean isPostalCode(String value){
		return value.matches("[1-9]\\d{5}(?!\\d)");   
	}
	/**
	 * 验证QQ号码
	 * @param value
	 * @return
	 */
	public static boolean isQQ(String value){
		return value.matches("[1-9][0-9]{4,13}");
	}
	
	/**
	 * 验证邮政编码
	 * @param value
	 * @return
	 */
	public static boolean isIP(String value){
		return value.matches("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
	}
	
	public static void main(String[] args) {
		System.out.println(isUrl("wwwbaoccomsfsd"));
	}
	
	
	
}
