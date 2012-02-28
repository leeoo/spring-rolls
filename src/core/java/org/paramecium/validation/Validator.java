package org.paramecium.validation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.paramecium.commons.BeanUtils;
import org.paramecium.ioc.ClassScanner;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.orm.annotation.NotUpdate;
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
import org.paramecium.validation.annotation.base.DecimalSize;
import org.paramecium.validation.annotation.base.Length;
import org.paramecium.validation.annotation.base.NotNull;
import org.paramecium.validation.annotation.base.Pattern;
import org.paramecium.validation.annotation.base.Size;

/**
 * 功能描述(Description):<br><b>
 * 验证器
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-8-22下午09:46:43</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.validation.Validator.java</b>
 */
public class Validator {
	
	private final static Log logger = LoggerFactory.getLogger();
	private final static String SHOWLABEL = "\\{ShowLabel\\}";
	private final static String MAX = "\\{max\\}";
	private final static String MIN = "\\{min\\}";
	
	/**
	 * 验证同时获得错误信息列表，如果没有错误，返回null
	 * @param bean
	 * @return
	 */
	public static Map<String,Collection<String>> getErrorMessages(Object bean,boolean isUpdate){
		Map<String,Collection<String>> messagesMap = new LinkedHashMap<String,Collection<String>>();
		Class<?> clazz = bean.getClass();
		String instanceName = ClassScanner.getInstanceName(clazz);
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				if(Modifier.isStatic(field.getModifiers())){
					continue;
				}
				field.setAccessible(true);
				try {
					if(isUpdate && field.getAnnotation(NotUpdate.class)!=null){
						continue;
					}
					Collection<String> messages = new LinkedList<String>();
					Object value = BeanUtils.getFieldValue(bean, field.getName(), field.getType());
					ShowLabel showLabel = field.getAnnotation(ShowLabel.class);
					String show = field.getName();//获取错误消息中字段名称
					if(showLabel!=null){
						show = showLabel.value();
					}
					//获取相关验证标注
					NotNull notNull = field.getAnnotation(NotNull.class);
					Numeric numeric = field.getAnnotation(Numeric.class);
					Size size = field.getAnnotation(Size.class);
					DecimalSize decimalSize = field.getAnnotation(DecimalSize.class);
					Length length = field.getAnnotation(Length.class);
					Pattern pattern = field.getAnnotation(Pattern.class);
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
						if(numeric!=null&&!isNumber(value, numeric.type())){
							messages.add(numeric.message().replaceAll(SHOWLABEL, show));
						}
						if(size!=null&&!compareSize(Integer.parseInt(value.toString()), size.min(), size.max())){
							messages.add(size.message().replaceAll(SHOWLABEL, show).replaceAll(MIN, ""+size.min()).replaceAll(MAX, ""+size.max()));
						}
						if(decimalSize!=null&&!compareDecimalSize(Double.parseDouble(value.toString()), decimalSize.min(), decimalSize.max())){
							messages.add(decimalSize.message().replaceAll(SHOWLABEL, show).replaceAll(MIN, ""+decimalSize.min()).replaceAll(MAX, ""+decimalSize.max()));
						}
						if(length!=null&&!checkLenth(value, length.min(), length.max())){
							messages.add(length.message().replaceAll(SHOWLABEL, show).replaceAll(MIN, ""+length.min()).replaceAll(MAX, ""+length.max()));
						}
						if(pattern!=null&&!regex(pattern.regex(), value)){
							messages.add(pattern.message().replaceAll(SHOWLABEL, show));
						}
						if(chinese!=null&&!isChinese(value.toString())){
							messages.add(chinese.message().replaceAll(SHOWLABEL, show));
						}
						if(email!=null&&!isEmail(value.toString())){
							messages.add(email.message());
						}
						if(idCard!=null&&!isIdCard(value.toString())){
							messages.add(idCard.message());
						}
						if(ipV4!=null&&!isIP(value.toString())){
							messages.add(ipV4.message());
						}
						if(loginCode!=null&&!isLoginCode(value.toString())){
							messages.add(loginCode.message().replaceAll(SHOWLABEL, show));
						}
						if(mobile!=null&&!isMobile(value.toString())){
							messages.add(mobile.message());
						}
						if(postalCode!=null&&!isPostalCode(value.toString())){
							messages.add(postalCode.message());
						}
						if(qq!=null&&!isQQ(value.toString())){
							messages.add(qq.message());
						}
						if(telePhone!=null&&!isTel(value.toString())){
							messages.add(telePhone.message());
						}
						if(url!=null&&!isUrl(value.toString())){
							messages.add(url.message());
						}
					}
					if(!messages.isEmpty()){
						messagesMap.put(instanceName+"."+field.getName(), messages);
					}
				} catch (Throwable e) {
					logger.error(e);
				}
			}
		}
		return messagesMap;
	}
	
	/**
	 * 直接验证，并抛出自定义验证异常
	 * @param bean
	 * @throws ValidationException
	 */
	public static void validation(Object bean) throws ValidationException{
		Map<String , Collection<String>> map = getErrorMessages(bean,false);
		if(map==null||map.isEmpty()){
			return;
		}
		StringBuffer sb = new StringBuffer("\r\n");
		for(Collection<String> messages : map.values()){
			for(String message : messages){
				sb.append(message).append("\r\n");
			}
		}
		throw new ValidationException(sb.toString());
	}
	
	/**
	 * 直接验证，并抛出自定义验证异常,在更新时候，有些字段无需再次修改或展示给客户，所有验证忽略那些带有@NotUpdate的字段。
	 * @param bean
	 * @throws ValidationException
	 */
	public static void validationByUpdate(Object bean) throws ValidationException{
		Map<String , Collection<String>> map = getErrorMessages(bean,true);
		if(map==null||map.isEmpty()){
			return;
		}
		StringBuffer sb = new StringBuffer("\r\n");
		for(Collection<String> messages : map.values()){
			for(String message : messages){
				sb.append(message).append("\r\n");
			}
		}
		throw new ValidationException(sb.toString());
	}
	
	/**
	 * 验证邮箱地址格式是否正确
	 * @param value
	 * @return
	 */
	private static boolean isEmail(String value){
		return value.matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
	}
	
	/**
	 * 验证座机电话号码是否正确
	 * @param value
	 * @return
	 */
	private static boolean isTel(String value){
		return value.matches("^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$");
	}
	
	/**
	 * 验证手机号码是否正确
	 * @param value
	 * @return
	 */
	private static boolean isMobile(String value){
		 return value.matches("^[1][3,5,8]+\\d{9}");
	}
	
	/**
	 * 验证中文是否正确
	 * @param value
	 * @return
	 */
	private static boolean isChinese(String value){
		 return value.matches("^[\u4e00-\u9fa5]+$");
	}
	
	/**
	 * 验证中文是否正确
	 * @param value
	 * @return
	 */
	private static boolean isUrl(String value){
		boolean y1 = value.matches("[http|https]+[://]+[0-9A-Za-z:/[-]_#[?][=][.]]*");
		boolean y2 = value.matches("[0-9A-Za-z:/[-]_#[?][=][.]]*");
		return y1||y2;
	}

	/**
	 * 验证登录名是否合法
	 * @param value
	 * @return
	 */
	private static boolean isLoginCode(String value){
		return value.matches("[a-zA-Z][a-zA-Z0-9_]{4,15}$");
	}

	/**
	 * 验证身份证
	 * @param value
	 * @return
	 */
	private static boolean isIdCard(String value){
		return value.matches("^\\d{15}(\\d{2}[A-Za-z0-9])?$");
	}
	
	/**
	 * 验证邮政编码
	 * @param value
	 * @return
	 */
	private static boolean isPostalCode(String value){
		return value.matches("[1-9]\\d{5}(?!\\d)");   
	}
	/**
	 * 验证QQ号码
	 * @param value
	 * @return
	 */
	private static boolean isQQ(String value){
		return value.matches("[1-9][0-9]{4,13}");
	}
	
	/**
	 * 验证邮政编码
	 * @param value
	 * @return
	 */
	private static boolean isIP(String value){
		return value.matches("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
	}
	
	/**
	 * 自定义正则表达式验证
	 * @param value
	 * @return
	 */
	private static boolean regex(String regex,Object value){
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
	private static boolean isNotNull(Object value){
		return value != null;
	}
	
	/**
	 * 验证空字符
	 * @param value
	 * @return
	 */
	private static boolean isNotEmpty(Object value){
		if(isNotNull(value)){
			return !value.toString().trim().isEmpty();
		}
		return false;
	}

	/**
	 * 验证是否为数值
	 * @param value
	 * @return
	 */
	@SuppressWarnings("static-access")
	private static boolean isNumber(Object value,NUMBER_TYPE type){
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
	private static boolean compareDecimalSize(double value ,double min,double max){
		return max >= value && value >= min;
	}

	
	/**
	 * 验证长度范围
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	private static boolean checkLenth(Object value,int min,int max){
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
	private static boolean compareSize(int value ,int min,int max){
		return max >= value && value >= min;
	}
	
}
