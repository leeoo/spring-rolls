package org.paramecium.validation;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.validation.annotation.Chinese;
import org.paramecium.validation.annotation.Email;
import org.paramecium.validation.annotation.IDCard;
import org.paramecium.validation.annotation.IpV4;
import org.paramecium.validation.annotation.LoginCode;
import org.paramecium.validation.annotation.Mobile;
import org.paramecium.validation.annotation.Numeric;
import org.paramecium.validation.annotation.PostalCode;
import org.paramecium.validation.annotation.QQ;
import org.paramecium.validation.annotation.TelePhone;
import org.paramecium.validation.annotation.Url;
import org.paramecium.validation.annotation.Numeric.NUMBER_TYPE;
import org.paramecium.validation.annotation.base.Compare;
import org.paramecium.validation.annotation.base.DecimalSize;
import org.paramecium.validation.annotation.base.Length;
import org.paramecium.validation.annotation.base.NotNull;
import org.paramecium.validation.annotation.base.Pattern;
import org.paramecium.validation.annotation.base.Size;
import org.paramecium.validation.annotation.base.Compare.COMPARISON;

/**
 * 功能描述(Description):<br><b>
 * 验证器
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-8-22下午09:46:43</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.validation.Validator.java</b>
 */
public class Validator {
	
	private final static String SHOWLABEL = "\\{ShowLabel\\}";
	
	/**
	 * 验证同时获得错误信息列表，如果没有错误，返回null
	 * @param bean
	 * @return
	 */
	public static Collection<String> getErrorMessages(Object bean){
		Collection<String> messages = new LinkedList<String>();
		Map<String,Collection<Boolean>> chooseOne = new HashMap<String,Collection<Boolean>>();
		Class<?> clazz = bean.getClass();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				try {
					Object value = field.get(bean);
					ShowLabel showLabel = field.getAnnotation(ShowLabel.class);
					String show = field.getName();//获取错误消息中字段名称
					if(showLabel!=null){
						show = showLabel.name();
					}
					//获取相关验证标注
					NotNull notNull = field.getAnnotation(NotNull.class);
					Numeric numeric = field.getAnnotation(Numeric.class);
					Length length = field.getAnnotation(Length.class);
					Compare compare = field.getAnnotation(Compare.class);
					DecimalSize decimalSize = field.getAnnotation(DecimalSize.class);
					Pattern pattern = field.getAnnotation(Pattern.class);
					Size size = field.getAnnotation(Size.class);
					Chinese chinese = field.getAnnotation(Chinese.class);
					Email email = field.getAnnotation(Email.class);
					IDCard idCard = field.getAnnotation(IDCard.class);
					IpV4 ipV4 = field.getAnnotation(IpV4.class);
					LoginCode loginCode = field.getAnnotation(LoginCode.class);
					Mobile mobile = field.getAnnotation(Mobile.class);
					PostalCode postalCode = field.getAnnotation(PostalCode.class);
					QQ qq = field.getAnnotation(QQ.class);
					TelePhone telePhone = field.getAnnotation(TelePhone.class);
					Url url = field.getAnnotation(Url.class);
					//非空验证,最先验证，因为可能会去引起空指针
					if(notNull!=null){
						boolean isEmpty = notNull.empty();
						if((!isNotNull(value))||(isEmpty&&!isNotEmpty(value))){
							messages.add(notNull.message().replaceAll(SHOWLABEL, show));
						}
					}
					if(isNotNull(value)){
						
					}
				} catch (Exception e) {
				}
			}
		}
		return messages;
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
	public static boolean isLoginCode(String value){
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
	
	/**
	 * 自定义正则表达式验证
	 * @param value
	 * @return
	 */
	public static boolean regex(String regex,Object value){
		if(isNotEmpty(value)){
			return value.toString().matches(regex);
		}
		return false;
	}
	
	/**
	 * 验证空
	 * @param value
	 * @return
	 */
	public static boolean isNotNull(Object value){
		return value != null;
	}
	
	/**
	 * 验证空，至少保证一个不为空
	 * @param values
	 * @return
	 */
	public static boolean isNotNullChooseOne(Collection<?> values){
		if(values!=null&&!values.isEmpty()){
			for(Object value : values){
				if(isNotNull(value)){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 验证空字符
	 * @param value
	 * @return
	 */
	public static boolean isNotEmpty(Object value){
		if(isNotNull(value)){
			return !value.toString().trim().isEmpty();
		}
		return false;
	}

	/**
	 * 验证空字符,至少保证一个不为空字符
	 * @param values
	 * @return
	 */
	public static boolean isNotEmptyChooseOne(Collection<?> values){
		if(values!=null&&!values.isEmpty()){
			for(Object value : values){
				if(isNotEmpty(value)){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 验证是否为数值
	 * @param value
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static boolean isNumber(Object value,NUMBER_TYPE type){
		if(isNotEmpty(value)){
			if(type.NUMERIC == type){
				return value instanceof Number;
			}else if(type.INTEGER == type){
				return value instanceof Integer || value instanceof Long || value instanceof Short;
			}else if(type.NATURAL == type){
				return (value instanceof Integer || value instanceof Long || value instanceof Short) && (Integer)value >= 0;
			}
		}
		return false;
	}
	
	/**
	 * 比较小数
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean compareDecimalSize(double value ,double min,double max){
		return max <= value && value >= min;
	}

	/**
	 * 比较值
	 * @param value
	 * @param compareValue
	 * @param comparison
	 * @return
	 */
	public static boolean compare(Object value,Object compareValue,String comparison){
		if(isNotEmpty(value)&&isNotEmpty(compareValue)&&isNotEmpty(comparison)){
			if(value.getClass() != compareValue.getClass()){
				return false;
			}
			long v = 0;
			long cv = 0;
			if(value instanceof Date){
				v = ((Date)value).getTime();
				cv = ((Date)compareValue).getTime();
			}else if(value instanceof Number){
				v = Long.parseLong(value.toString());
				cv = Long.parseLong(compareValue.toString());
			}else{
				return false;
			}
			if(comparison.equals(COMPARISON.EQUAL)){
				return v == cv;
			}else if(comparison.equals(COMPARISON.LESS)){
				return v < cv;
			}else if(comparison.equals(COMPARISON.LESS_EQUAL)){
				return v <= cv;
			}else if(comparison.equals(COMPARISON.THAN)){
				return v > cv;
			}else if(comparison.equals(COMPARISON.THAN_EQUAL)){
				return v >= cv;
			}
		}
		return false;
	}
	
	/**
	 * 验证长度范围
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean checkLenth(Object value,int min,int max){
		if(isNotEmpty(value)){
			int l = value.toString().length();
			return l>=min && l<=max;
		}
		return false;
	}
	
	/**
	 * 比较整数
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean compareSize(int value ,int min,int max){
		return max <= value && value >= min;
	}
	
}
