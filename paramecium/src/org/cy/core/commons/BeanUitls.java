package org.cy.core.commons;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.cy.core.log.Log;
import org.cy.core.log.LoggerFactory;
/**
 * 功 能 描 述:<br>
 * jdbc所涉及到的bean工具
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-3-30下午02:12:56
 * <br>项 目 信 息:paramecium:org.cy.core.commons.BeanUitls.java
 */
public abstract class BeanUitls {
	
	private static Log logger = LoggerFactory.getLogger(BeanUitls.class);
	
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
				field.setAccessible(true);
				try {
					map.put(field.getName(),field.get(bean));
				} catch (Exception e) {
					logger.warn(e.fillInStackTrace());
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
					field.setAccessible(true);
					try {
						String name = field.getName();
						if(isDBFormat){
							name = getDbFieldName(name);
						}
						field.set(bean, map.get(name));
					} catch (Exception e) {
						logger.warn(e.fillInStackTrace());
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
	
}
