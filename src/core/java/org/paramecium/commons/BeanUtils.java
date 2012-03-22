package org.paramecium.commons;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
/**
 * 功 能 描 述:<br>
 * jdbc所涉及到的bean工具
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-3-30下午02:12:56
 * <br>项 目 信 息:paramecium:org.paramecium.commons.BeanUtils.java
 */
public abstract class BeanUtils {
	
	private final static Log logger = LoggerFactory.getLogger();
	private final static String GET = "get";
	private final static String IS = "is";
	private final static String SET = "set";
	
	/**
	 * 设置字段值
	 * @param field
	 * @param value
	 * @param bean
	 */
	public static void setFieldValue(Field field,String value,Object bean){
		try{
			if(value==null||bean==null||value.isEmpty()){
				return;
			}
			Class<?> clazz = field.getType();
			if (String.class.equals(clazz)){
				field.set(bean,value);
			}else if (int.class.equals(clazz) || Integer.class.equals(clazz)) {
				field.set(bean,Integer.parseInt(value));
			}else if (java.util.Date.class.equals(clazz) || java.sql.Date.class.equals(clazz)) {
				field.set(bean,DateUtils.parse(value));
			}else if (long.class.equals(clazz) || Long.class.equals(clazz)) {
				field.set(bean,Long.parseLong(value));
			}else if (boolean.class.equals(clazz) || Boolean.class.equals(clazz)) {
				field.set(bean,Boolean.parseBoolean(value));
			}else if (byte.class.equals(clazz) || Byte.class.equals(clazz)) {
				field.set(bean,Byte.parseByte(value));
			}else if (short.class.equals(clazz) || Short.class.equals(clazz)) {
				field.set(bean,Short.parseShort(value));
			}else if (float.class.equals(clazz) || Float.class.equals(clazz)) {
				field.set(bean,Float.parseFloat(value));
			}else if (double.class.equals(clazz) || Double.class.equals(clazz) || Number.class.equals(clazz)) {
				field.set(bean,Double.parseDouble(value));
			}else if (byte[].class.equals(clazz)) {
				field.set(bean,value.getBytes());
			}else if (char.class.equals(clazz) || Character.class.equals(clazz)) {
				field.set(bean,value.charAt(0));
			}else if (BigDecimal.class.equals(clazz)) {
				field.set(bean,new BigDecimal(value));
			}
		}catch (Exception e) {
			logger.error(e);
		}
	}
	
	/**
	 * 根据字符串获得值
	 * @param value
	 * @param clazz
	 * @return
	 */
	public static Object getValueByClass(String value,Class<?> clazz){
		if (String.class.equals(clazz)){
			return value;
		}else if (int.class.equals(clazz) || Integer.class.equals(clazz)) {
			return Integer.parseInt(value);
		}else if (java.util.Date.class.equals(clazz) || java.sql.Date.class.equals(clazz)) {
			return DateUtils.parse(value);
		}else if (long.class.equals(clazz) || Long.class.equals(clazz)) {
			return Long.parseLong(value);
		}else if (boolean.class.equals(clazz) || Boolean.class.equals(clazz)) {
			return Boolean.parseBoolean(value);
		}else if (byte.class.equals(clazz) || Byte.class.equals(clazz)) {
			return Byte.parseByte(value);
		}else if (short.class.equals(clazz) || Short.class.equals(clazz)) {
			return Short.parseShort(value);
		}else if (float.class.equals(clazz) || Float.class.equals(clazz)) {
			return Float.parseFloat(value);
		}else if (double.class.equals(clazz) || Double.class.equals(clazz) || Number.class.equals(clazz)) {
			return Double.parseDouble(value);
		}else if (byte[].class.equals(clazz)) {
			return value.getBytes();
		}else if (char.class.equals(clazz) || Character.class.equals(clazz)) {
			return value.charAt(0);
		}else if (BigDecimal.class.equals(clazz)) {
			return new BigDecimal(value);
		}
		return null;
	}
	
	/**
	 * bean实例转换为Map实例
	 * @param bean
	 * @return
	 */
	public static Map<String, Object> bean2Map(Object bean){
		Map<String, Object> map = new HashMap<String, Object>();
		Class<?> clazz = bean.getClass();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				if(Modifier.isStatic(field.getModifiers())){
					continue;
				}
				field.setAccessible(true);
				try {
					map.put(field.getName(),getFieldValue(bean, field.getName(), field.getType()));
				} catch (Exception e) {
					logger.warn(e);
				}
			}
		}
		return map;
	}
	
	/**
	 * Map实例转换为指定类型的bean实例
	 * @param clazz
	 * @param map
	 * @param isDBFormat
	 * @return
	 */
	public static Object map2Bean(Class<?> clazz,Map<String, Object> map,boolean isDBFormat){
		Object bean = null;
		try {
			bean = clazz.newInstance();
			for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
				Field[] fields = superClass.getDeclaredFields();
				for(Field field : fields){
					if(Modifier.isStatic(field.getModifiers())){
						continue;
					}
					field.setAccessible(true);
					try {
						String name = field.getName();
						if(isDBFormat){
							name = getDbFieldName(name);
						}
						setFieldValue(bean, name, map.get(name));
					} catch (Exception e) {
						logger.warn(e);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	 * 根据bean属性名获得对应数据库字段名
	 * @param propertyName
	 * @return
	 */
	public static String getDbFieldName(String propertyName) {
		StringBuffer strb = new StringBuffer();
		for (int i = 0; i < propertyName.length(); i++) {
			char ch = propertyName.charAt(i);
			if (Character.isUpperCase(ch)) {
				strb.append('_');
				strb.append(Character.toLowerCase(ch));
			} else {
				strb.append(ch);
			}
		}
		return strb.toString();
	}

	/**
	 * 根据数据库字段名获得对应bean属性名
	 * @param fieldName
	 * @return
	 */
	public static String getBeanPropertyName(String fieldName) {
		StringBuffer strb = new StringBuffer();
		boolean isLine = false;
		for (int i = 0; i < fieldName.length(); i++) {
			char ch = fieldName.charAt(i);
			if ('_'==ch||'$'==ch) {
				isLine = true;
			} else if(isLine){
				strb.append(Character.toUpperCase(ch));
				isLine = false;
			}else {
				strb.append(ch);
			}
		}
		return strb.toString();
	}
	
	/**
	 * 调用getter方法
	 * @param name
	 * @param bean
	 * @return
	 */
	public static Object getFieldValue(Object bean,String name){
		return getFieldValue(bean, name, GET);
	}
	
	/**
	 * 调用getter方法
	 * @param name
	 * @param bean
	 * @return
	 */
	public static Object getFieldValue(Object bean,String name,Class<?> fieldType){
		if(fieldType == null){
			return getFieldValue(bean, name, GET);
		}
		if(fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)){//根据属性类型判断对应的getter方法方式
			return getFieldValue(bean, name, IS);
		}
		return getFieldValue(bean, name, GET);
	}
	
	/**
	 * 调用getter方法
	 * @param bean
	 * @param name
	 * @param getterName普通的是get开头，boolean是is开头
	 * @return
	 */
	private static Object getFieldValue(Object bean,String name,String getterName){
		String getMethodName = null;
		try {
			if(bean==null||name==null||name.isEmpty()){
				return null;
			}
			if(getterName.equals(IS)){//判断是否是is方式的getter
				if(name.substring(0, 2).equals(IS)){//如果属性name已经是is开头，则对应getter方法无需在加is
					getMethodName = name;
				}else{
					getMethodName = IS.concat(name.substring(0, 1).toUpperCase()).concat(name.substring(1));
				}
			}else{//普通的getter方法
				getMethodName = GET.concat(name.substring(0, 1).toUpperCase()).concat(name.substring(1));
			}
			Method method = bean.getClass().getMethod(getMethodName);
			return method.invoke(bean);
		} catch (Exception e) {
			logger.warn(bean.getClass().toString().concat("中没有匹配到").concat(getMethodName).concat("方法!"));
		}
		return null;
	}
	
	/**
	 * 调用setter方法
	 * @param bean
	 * @param name
	 * @param value
	 */
	public static void setFieldValue(Object bean,String name,final Object value){
		String setMethodName = null;
		Method method = null;
		try {//获取普通数据库类型对应Entity每个属性的setter方法
			if(bean==null||name==null||name.isEmpty()||value==null){
				return;
			}
			Class<?> clazz = value.getClass();
			setMethodName = SET.concat(name.substring(0, 1).toUpperCase()).concat(name.substring(1));
			method = bean.getClass().getMethod(setMethodName,clazz);
		} catch (Exception e) {
			try {//获取特殊类型对应Entity每个属性，如果setter方法中的属性参数为基本类型如int、long等，需要将对应封装类型转为基本类型.
				Class<?> clazz = value.getClass();
				if(Integer.class.equals(clazz)) {
					clazz = int.class;
				}else if (Long.class.equals(clazz)) {
					clazz = long.class;
				}else if (Boolean.class.equals(clazz)) {
					clazz = boolean.class;
				}else if (Byte.class.equals(clazz)) {
					clazz = byte.class;
				}else if (Short.class.equals(clazz)) {
					clazz = short.class;
				}else if (Float.class.equals(clazz)) {
					clazz = float.class;
				}else if (Double.class.equals(clazz)) {
					clazz = double.class;
				}else if (Character.class.equals(clazz)) {
					clazz = char.class;
				}else if (java.math.BigInteger.class.equals(clazz)) {
					clazz = long.class;
				}else if (java.math.BigDecimal.class.equals(clazz)) {
					clazz = double.class;
				}else if (java.sql.Clob.class.equals(clazz)) {
					clazz = String.class;
				}else if (java.sql.NClob.class.equals(clazz)) {
					clazz = String.class;
				}else if (java.sql.Blob.class.equals(clazz)) {
					clazz = byte[].class;
				}else if (java.sql.Ref.class.equals(clazz)) {
					clazz = byte[].class;
				}else if (java.sql.Array.class.equals(clazz)) {
					clazz = Object[].class;
				}else if (java.sql.Struct.class.equals(clazz)) {
					clazz = Object[].class;
				}else if (java.sql.Date.class.equals(clazz)) {
					clazz = java.util.Date.class;
				}else if (java.sql.Time.class.equals(clazz)) {
					clazz = java.util.Date.class;
				}else if (java.sql.Timestamp.class.equals(clazz)) {
					clazz = java.util.Date.class;
				}else{
					clazz = Object.class;
				}
				if(setMethodName != null){
					setMethodName = SET.concat(name.substring(0, 1).toUpperCase()).concat(name.substring(1));
				}
				method = bean.getClass().getMethod(setMethodName,clazz);
			}catch (Exception e2) {
				logger.warn(bean.getClass().toString().concat("中没有匹配到").concat(setMethodName).concat("方法!"));
			}
		}
		if(method != null){
			try {
				method.invoke(bean,value);
			}catch (Exception e) {
				logger.warn(bean.getClass().toString().concat("中的").concat(setMethodName).concat("方法执行失败!"));
			}
		}
		
	}
	
}
