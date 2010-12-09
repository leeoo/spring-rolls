package com.wisdom.core.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.wisdom.core.annotation.NotMapping;
import com.wisdom.core.annotation.NotUpdate;
import com.wisdom.core.annotation.Unique;

/**
 * 反射的Utils函数集合.
 * 
 * 提供侵犯隐私的直接读取Field的能力.
 */
@SuppressWarnings("unchecked")
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils{

	protected static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

	static {
		DateConverter dc = new DateConverter();
		dc.setUseLocaleFormat(true);
		dc.setPatterns(new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" });
		ConvertUtils.register(dc, Date.class);
	}
	
	/**
	 * 构建simpleEntity实体的update部分语句
	 * @param bean
	 * @param pkName
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static String getBuildUpdateSql(Object bean,String pkName)throws IllegalAccessException,InvocationTargetException, NoSuchMethodException {
		String sql="";
		if (bean == null) {
			return null;
		}
		String pkPropertyName = pkName;
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(bean);
		for (int i = 0; i < descriptors.length; i++) {
			String propertyName = descriptors[i].getName();
			String fieldName = getDbFieldName(propertyName);
			if(fieldName.equals(pkName)){
				pkPropertyName = propertyName;
			}
			if (!propertyName.equalsIgnoreCase("class")&&!propertyName.equalsIgnoreCase("insertDate")&&!propertyName.equalsIgnoreCase("businessCode")&&PropertyUtils.getReadMethod(descriptors[i]) != null&&!propertyName.equals(pkPropertyName)&&!isNotMapping(bean.getClass(),propertyName)&&!isNotUpdate(bean.getClass(),propertyName)) {
				sql=sql.concat(fieldName).concat("=:").concat(propertyName).concat(",");
			}
		}
		String fieldNameName=getDbFieldName(pkName).toLowerCase();
		sql=sql.substring(0, sql.length()-1).concat(" WHERE ").concat(fieldNameName).concat("=:").concat(pkPropertyName);
		return sql;
	}

	/**
	 * 构建simpleEntity实体的update部分语句(非空属性值)
	 * @param bean
	 * @param pkName
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static String getBuildUpdateSqlNotNull(Object bean,String pkName)throws IllegalAccessException,InvocationTargetException, NoSuchMethodException {
		String sql="";
		if (bean == null) {
			return null;
		}
		String pkPropertyName = pkName;
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(bean);
		for (int i = 0; i < descriptors.length; i++) {
			String propertyName = descriptors[i].getName();
			String fieldName = getDbFieldName(propertyName);
			if(fieldName.equals(pkName)){
				pkPropertyName = propertyName;
			}
			if (!propertyName.equalsIgnoreCase("class")&&!propertyName.equalsIgnoreCase("insertDate")&&!propertyName.equalsIgnoreCase("businessCode")&&PropertyUtils.getReadMethod(descriptors[i]) != null&&!propertyName.equals(pkPropertyName)&&!isNotMapping(bean.getClass(),propertyName)&&!isNotUpdate(bean.getClass(),propertyName)) {
				Object propertyValue = getFieldValue(bean, propertyName);
				if(propertyValue!=null){
					sql=sql.concat(fieldName).concat("=:").concat(propertyName).concat(",");
				}
			}
		}
		String fieldNameName=getDbFieldName(pkName).toLowerCase();
		sql=sql.substring(0, sql.length()-1).concat(" WHERE ").concat(fieldNameName).concat("=:").concat(pkPropertyName);
		return sql;
	}
	
	/**
	 * 构建simpleEntity实体的insert部分语句
	 * @param bean
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static String getBuildInsertSql(Object bean)throws IllegalAccessException,InvocationTargetException, NoSuchMethodException {
		String sql1=" (";
		String sql2=" VALUES (";
		if (bean == null) {
			return null;
		}
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(bean);
		for (int i = 0; i < descriptors.length; i++) {
			String propertyName = descriptors[i].getName();
			String fieldName = getDbFieldName(propertyName);
			if (!propertyName.equalsIgnoreCase("class")&&PropertyUtils.getReadMethod(descriptors[i]) != null&&!isNotMapping(bean.getClass(),propertyName)) {
				sql1=sql1.concat(fieldName).concat(",");
				sql2=sql2.concat(":".concat(propertyName)).concat(",");
			}
		}
		return sql1.substring(0, sql1.length()-1).concat(")").concat(sql2.substring(0, sql2.length()-1).concat(")"));
	}
	
	/**
	 * 判断simpleEntity实体的某个属性是否为notMapping
	 * @param clazz实体类型
	 * @param propertyName
	 * @return
	 */
	public static boolean isNotMapping(Class clazz,String propertyName){
		NotMapping notMapping=null;
		try {
			notMapping = clazz.getDeclaredField(propertyName).getAnnotation(NotMapping.class);
		} catch (Exception e) {}
		if(notMapping!=null){
			return true;
		}
		return false;
	}

	/**
	 * 判断simpleEntity实体的某个属性是否为NoUpdate
	 * @param clazz实体类型
	 * @param propertyName
	 * @return
	 */
	public static boolean isNotUpdate(Class clazz,String propertyName){
		NotUpdate notUpdate=null;
		try {
			notUpdate = clazz.getDeclaredField(propertyName).getAnnotation(NotUpdate.class);
		} catch (Exception e) {}
		if(notUpdate!=null){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断simpleEntity实体的某个属性是否为unique
	 * @param clazz实体类型
	 * @param propertyName
	 * @return
	 */
	public static boolean isUnique(Class clazz,String propertyName){
		Unique unique=null;
		try {
			unique = clazz.getDeclaredField(propertyName).getAnnotation(Unique.class);
		} catch (Exception e) {}
		if(unique!=null){
			return true;
		}
		return false;
	}

	/**
	 * 判断simpleEntity实体的某个属性是否为NotNull
	 * @param clazz实体类型
	 * @param propertyName
	 * @return
	 */
	public static boolean isNotNull(Class clazz,String propertyName){
		NotNull notNull=null;
		try {
			notNull = clazz.getDeclaredField(propertyName).getAnnotation(NotNull.class);
		} catch (Exception e) {}
		if(notNull!=null){
			return true;
		}
		return false;
	}

	/**
	 * 判断simpleEntity实体的某个属性的最大长度
	 * @param descriptor
	 * @return
	 */
	public static int getMaxLength(Class clazz,String propertyName){
		Size size=null;
		try {
			size = clazz.getDeclaredField(propertyName).getAnnotation(Size.class);
		} catch (Exception e) {}
		if(size==null){
			return -1;
		}
		return size.max();
	}

	public static Map describeByDbField(Object bean,boolean isLower,boolean isLine) throws IllegalAccessException,
	InvocationTargetException, NoSuchMethodException {
		Map description = new HashMap();
		if (bean == null) {
			return description;
		}
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(bean);
		for (int i = 0; i < descriptors.length; i++) {
			String name = descriptors[i].getName();
			if (PropertyUtils.getReadMethod(descriptors[i]) != null) {
				String newName=null;
				if(isLower){
					if(isLine){
						newName=getDbFieldName(name).toLowerCase();
					}else{
						newName=name.toLowerCase();
					}
				}else{
					if(isLine){
						newName=getDbFieldName(name).toUpperCase();
					}else{
						newName=name.toUpperCase();
					}
				}
				description.put(newName, PropertyUtils.getProperty(bean,name));
			}
		}
		return (description);
	}

	public static String getDbFieldName(String name) {
		StringBuffer strb = new StringBuffer();
		for (int i = 0; i < name.length(); i++) {
			char ch = name.charAt(i);
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
	 * 循环向上转型,获取类的DeclaredField.
	 */
	public static Field getDeclaredField(Class clazz, String fieldName)
			throws NoSuchFieldException {
		Assert.notNull(clazz);
		Assert.hasText(fieldName);
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName()
				+ '.' + fieldName);
	}
	
	
	//=============

	/**
	 * 调用Getter方法.
	 */
	public static Object invokeGetterMethod(Object target, String propertyName) {
		String getterMethodName = "get" + StringUtils.capitalize(propertyName);
		return invokeMethod(target, getterMethodName, new Class[] {}, new Object[] {});
	}

	/**
	 * 调用Setter方法.使用value的Class来查找Setter方法.
	 */
	public static void invokeSetterMethod(Object target, String propertyName, Object value) {
		invokeSetterMethod(target, propertyName, value, null);
	}

	/**
	 * 调用Setter方法.
	 * 
	 * @param propertyType 用于查找Setter方法,为空时使用value的Class替代.
	 */
	public static void invokeSetterMethod(Object target, String propertyName, Object value, Class<?> propertyType) {
		Class<?> type = propertyType != null ? propertyType : value.getClass();
		String setterMethodName = "set" + StringUtils.capitalize(propertyName);
		invokeMethod(target, setterMethodName, new Class[] { type }, new Object[] { value });
	}

	/**
	 * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
	 */
	public static Object getFieldValue(final Object object, final String fieldName) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		}

		makeAccessible(field);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常{}", e.getMessage());
		}
		return result;
	}

	/**
	 * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
	 */
	public static void setFieldValue(final Object object, final String fieldName, final Object value) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		}

		makeAccessible(field);

		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}
	}
	
	public static void setFieldNumberValue(final Object object, final String fieldName, final Object value) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		}
		makeAccessible(field);
		try {
			Class clazz =field.getType();
			Number number = NumberUtils.createNumber(value.toString());
			if(Long.class.equals(clazz)){
				try{
					field.setLong(object, new Long(number.longValue()));
				}catch (IllegalArgumentException iar) {
					field.set(object, new Long(number.longValue()));
				}
			}else if(Integer.class.equals(clazz)){
				try{
					field.setInt(object, number.intValue());
				}catch (IllegalArgumentException iar) {
					field.set(object, new Integer(number.intValue()));
				}
			}else if(Short.class.equals(clazz)){
				try{
					field.setShort(object, new Short(number.shortValue()));
				}catch (IllegalArgumentException iar) {
					field.set(object, number.shortValue());
				}
			}else{
				field.set(object, value);
			}
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}
	}

	/**
	 * 直接调用对象方法, 无视private/protected修饰符.
	 */
	public static Object invokeMethod(final Object object, final String methodName, final Class<?>[] parameterTypes,
			final Object[] parameters) {
		Method method = getDeclaredMethod(object, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
		}

		method.setAccessible(true);

		try {
			return method.invoke(object, parameters);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredField.
	 * 
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	protected static Field getDeclaredField(final Object object, final String fieldName) {
		Assert.notNull(object, "object不能为空");
		Assert.hasText(fieldName, "fieldName");
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {//NOSONAR
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 强行设置Field可访问.
	 */
	protected static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredMethod.
	 * 
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	protected static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
		Assert.notNull(object, "object不能为空");

		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {//NOSONAR
				// Method不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型.
	 * 如无法找到, 返回Object.class.
	 * eg.
	 * public UserDao extends HibernateDao<User>
	 *
	 * @param clazz The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be determined
	 */
	public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射, 获得定义Class时声明的父类的泛型参数的类型.
	 * 如无法找到, 返回Object.class.
	 * 
	 * 如public UserDao extends HibernateDao<User,Long>
	 *
	 * @param clazz clazz The class to introspect
	 * @param index the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be determined
	 */
	public static Class getSuperClassGenricType(final Class clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}

	/**
	 * 提取集合中的对象的属性(通过getter函数), 组合成List.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 */
	public static List convertElementPropertyToList(final Collection collection, final String propertyName) {
		List list = new ArrayList();

		try {
			for (Object obj : collection) {
				list.add(PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}

		return list;
	}

	/**
	 * 提取集合中的对象的属性(通过getter函数), 组合成由分割符分隔的字符串.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 * @param separator 分隔符.
	 */
	public static String convertElementPropertyToString(final Collection collection, final String propertyName,
			final String separator) {
		List list = convertElementPropertyToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	/**
	 * 转换字符串到相应类型.
	 * 
	 * @param value 待转换的字符串
	 * @param toType 转换目标类型
	 */
	public static Object convertStringToObject(String value, Class<?> toType) {
		try {
			return ConvertUtils.convert(value, toType);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 将反射时的checked exception转换为unchecked exception.
	 */
	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException) {
			return new IllegalArgumentException("Reflection Exception.", e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}
	
}
