package org.paramecium.commons;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.orm.annotation.Column;
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
			}else if (java.util.Date.class.equals(clazz) || java.sql.Date.class.equals(clazz) || java.sql.Timestamp.class.equals(clazz) || java.sql.Time.class.equals(clazz)) {
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
		}else if (java.util.Date.class.equals(clazz) || java.sql.Date.class.equals(clazz) || java.sql.Timestamp.class.equals(clazz) || java.sql.Time.class.equals(clazz)) {
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
					Column column = field.getAnnotation(Column.class);
					String fieldName = field.getName();
					if(column!=null && !column.value().isEmpty()){
						fieldName = column.value();//设置数据库的值
					}
					map.put(fieldName,getFieldValue(bean, superClass, field.getName(), field.getType()));
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
	public static Object map2Bean(Class<?> clazz,Map<String, Object> map){
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
						Column column = field.getAnnotation(Column.class);
						Object value = null;
						if(column!=null && !column.value().isEmpty()){
							value = map.get(column.value());//设置数据库的值
						}
						if(value == null){
							 value = map.get(field.getName());
						}
						if(value == null){
							value = map.get(getDbFieldName(field.getName()));
						}
						setFieldValue(bean, superClass, field.getName(), value);
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
	public static Object getFieldValue(Object bean,Class<?> clazz,String name){
		return getFieldValue(bean, clazz, name, GET);
	}
	
	/**
	 * 调用getter方法
	 * @param name
	 * @param bean
	 * @return
	 */
	public static Object getFieldValue(Object bean,Class<?> clazz,String name,Class<?> fieldType){
		if(fieldType == null){
			return getFieldValue(bean, clazz, name, GET);
		}
		if(fieldType.equals(boolean.class)){//根据属性类型判断对应的getter方法方式
			Object value = getFieldValue(bean, clazz, name, IS);
			if(value==null){//如果真的没有按照getter规则命名，可以调用getter方法
				return getFieldValue(bean, clazz, name, GET);
			}
			return value;
		}
		return getFieldValue(bean, clazz, name, GET);
	}
	
	/**
	 * 调用getter方法
	 * @param bean
	 * @param name
	 * @param getterName普通的是get开头，boolean是is开头
	 * @return
	 */
	private static Object getFieldValue(Object bean,Class<?> clazz,String name,String getterName){
		String getMethodName = null;
		try {
			if(bean==null||name==null||name.isEmpty()||clazz==null){
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
			Method method = null;
			try{
				method = clazz.getMethod(getMethodName);
			} catch (NoSuchMethodException e) {//如果错误，有可能是出现了aName,bName等名称，eclipse等工具会将autoGetter变成getaName，以下为补漏.
				if(getterName.equals(IS)){//判断是否是is方式的getter
					getMethodName = IS.concat(name);
				}else{//普通的getter方法
					getMethodName = GET.concat(name);
				}
				method = clazz.getMethod(getMethodName);
			}
			return method.invoke(bean);
		} catch (Exception e) {
			logger.warn(clazz.toString().concat("中没有匹配到").concat(getMethodName).concat("方法!"));
		}
		return null;
	}
	
	/**
	 * 调用setter方法
	 * @param bean
	 * @param name
	 * @param value
	 */
	public static void setFieldValue(Object bean,Class<?> clazz,String name,final Object value){
		String setMethodName = null;
		Method method = null;
		try {//获取普通数据库类型对应Entity每个属性的setter方法
			if(bean==null||name==null||name.isEmpty()||value==null||clazz==null){
				return;
			}
			Class<?> fieldClazz = value.getClass();
			setMethodName = SET.concat(name.substring(0, 1).toUpperCase()).concat(name.substring(1));
			try{
				method = clazz.getMethod(setMethodName,fieldClazz);
			} catch (NoSuchMethodException e) {//如果错误，有可能是出现了aName,bName等名称，eclipse等工具会将autoSetter变成setaName，以下为补漏.
				setMethodName = SET.concat(name);
				method = clazz.getMethod(setMethodName,fieldClazz);
			}
		} catch (Exception e) {
			try {//获取特殊类型对应Entity每个属性，如果setter方法中的属性参数为基本类型如int、long等，需要将对应封装类型转为基本类型.
				Class<?> fieldClazz = value.getClass();
				if(Integer.class.equals(fieldClazz)||value instanceof Integer) {
					fieldClazz = int.class;
				}else if (Long.class.equals(fieldClazz)||value instanceof Long) {
					fieldClazz = long.class;
				}else if (Boolean.class.equals(fieldClazz)||value instanceof Boolean) {
					fieldClazz = boolean.class;
				}else if (Byte.class.equals(fieldClazz)||value instanceof Byte) {
					fieldClazz = byte.class;
				}else if (Short.class.equals(fieldClazz)||value instanceof Short) {
					fieldClazz = short.class;
				}else if (Float.class.equals(fieldClazz)||value instanceof Float) {
					fieldClazz = float.class;
				}else if (Double.class.equals(fieldClazz)||value instanceof Double) {
					fieldClazz = double.class;
				}else if (Character.class.equals(fieldClazz)||value instanceof Character) {
					fieldClazz = char.class;
				}else if (java.math.BigInteger.class.equals(fieldClazz)||value instanceof java.math.BigInteger) {
					fieldClazz = long.class;
				}else if (java.math.BigDecimal.class.equals(fieldClazz)||value instanceof java.math.BigDecimal) {
					fieldClazz = double.class;
				}else if (java.sql.Clob.class.equals(fieldClazz)||value instanceof java.sql.Clob) {
					fieldClazz = String.class;
				}else if (java.sql.NClob.class.equals(fieldClazz)||value instanceof java.sql.NClob) {
					fieldClazz = String.class;
				}else if (java.sql.Blob.class.equals(fieldClazz)||value instanceof java.sql.Blob) {
					fieldClazz = byte[].class;
				}else if (java.sql.Ref.class.equals(fieldClazz)||value instanceof java.sql.Ref) {
					fieldClazz = byte[].class;
				}else if (java.sql.Array.class.equals(fieldClazz)||value instanceof java.sql.Array) {
					fieldClazz = Object[].class;
				}else if (java.sql.Struct.class.equals(fieldClazz)||value instanceof java.sql.Struct) {
					fieldClazz = Object[].class;
				}else if (java.sql.Date.class.equals(fieldClazz)||value instanceof java.sql.Date) {
					fieldClazz = java.util.Date.class;
				}else if (java.sql.Time.class.equals(fieldClazz)||value instanceof java.sql.Time) {
					fieldClazz = java.util.Date.class;
				}else if (java.sql.Timestamp.class.equals(fieldClazz)||value instanceof java.sql.Timestamp) {
					fieldClazz = java.util.Date.class;
				}else{
					fieldClazz = Object.class;
				}
				if(setMethodName != null){
					setMethodName = SET.concat(name.substring(0, 1).toUpperCase()).concat(name.substring(1));
				}
				method = clazz.getMethod(setMethodName,fieldClazz);
			}catch (Exception e2) {
				logger.warn(clazz.toString().concat("中没有匹配到").concat(setMethodName).concat("方法!"));
			}
		}
		if(method != null){
			try {
				method.invoke(bean,value);
			}catch (Exception e) {
				logger.warn(clazz.toString().concat("中的").concat(setMethodName).concat("方法执行失败!"));
			}
		}
		
	}
	
}
