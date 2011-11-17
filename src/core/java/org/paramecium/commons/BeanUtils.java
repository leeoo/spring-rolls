package org.paramecium.commons;

import java.lang.reflect.Field;
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
	
	/**
	 * 设置字段值
	 * @param field
	 * @param value
	 * @param bean
	 */
	public static void setFieldValue(Field field,String value,Object bean){
		try{
			if(value==null||bean==null){
				return;
			}
			Class<?> clazz = field.getType();
			if (String.class.equals(clazz)){
				field.set(bean,value);
			}else if (int.class.equals(clazz) || Integer.class.equals(clazz)) {
				field.set(bean,Integer.parseInt(value));
			}else if (java.util.Date.class.equals(clazz)) {
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
		}else if (java.util.Date.class.equals(clazz)) {
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
					map.put(field.getName(),field.get(bean));
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
						field.set(bean, map.get(name));
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
				strb.append("_");
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
	 * 根据属性获得值
	 * @param name
	 * @param bean
	 * @return
	 */
	public static Object getFieldValue(Object bean,String name){
		try {
			for (Class<?> superClass = bean.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
				Field[] fields = superClass.getDeclaredFields();
				for(Field field : fields){
					field.setAccessible(true);
					try {
						if(field.getName().equals(name)){
							return field.get(bean);
						}
					} catch (Exception e) {
						logger.warn(e);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * 设置属性值在bean中
	 * @param bean
	 * @param name
	 * @param value
	 */
	public static void setFieldValue(Object bean,String name,Object value){
		try {
			for (Class<?> superClass = bean.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
				Field[] fields = superClass.getDeclaredFields();
				for(Field field : fields){
					field.setAccessible(true);
					try {
						if(field.getName().equals(name)){
							field.set(bean, value);
							return;
						}
					} catch (Exception e) {
						logger.warn(e);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
}
