package org.cy.core.commons;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import org.cy.core.orm.annotation.Column;
import org.cy.core.orm.annotation.Entity;
import org.cy.core.orm.annotation.PrimaryKey;
import org.cy.core.orm.annotation.PrimaryKey.AUTO_GENERATE_MODE;

/**
 * 功能描述(Description):<br><b>
 * 实体操作工具
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-4上午09:42:49</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.cy.core.commons.EntityUtils.java</b>
 */
public abstract class EntityUtils {
	
	private static String getTableName(Object bean){
		try{
			Entity entity = bean.getClass().getAnnotation(Entity.class);
			if(entity!=null&&entity.tableName()!=null){
				return entity.tableName();
			}
		}catch (Exception e) {}
		return null;
	}
	
	public static String getInsertSql(Object bean){
		StringBuffer sb = new StringBuffer();
		String tableName = getTableName(bean);
		Entity entity = bean.getClass().getAnnotation(Entity.class);
		Collection<String> columns = new ArrayList<String>();
		Collection<String> propertys = new ArrayList<String>();
		Class<?> clazz = bean.getClass();
		String mark = ":";
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				try {
					if(entity!=null){
						Column column = field.getAnnotation(Column.class);
						PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
						if(primaryKey!=null&&primaryKey.autoGenerateMode()==AUTO_GENERATE_MODE.MANUAL){
							if(column!=null&&!column.fieldName().isEmpty()){
								columns.add(column.fieldName());
								propertys.add(mark.concat(field.getName()));
							}else{
								columns.add(BeanUitls.getDbFieldName(field.getName()));
								propertys.add(mark.concat(field.getName()));
							}
						}else if(primaryKey!=null&&primaryKey.autoGenerateMode()==AUTO_GENERATE_MODE.NATIVE_SEQUENCE){
							if(column!=null&&!column.fieldName().isEmpty()){
								columns.add(column.fieldName());
								propertys.add(primaryKey.sequenceName());
							}else{
								columns.add(BeanUitls.getDbFieldName(field.getName()));
								propertys.add(primaryKey.sequenceName());
							}
						}else if(primaryKey==null){
							if(column!=null&&!column.fieldName().isEmpty()){
								columns.add(column.fieldName());
								propertys.add(mark.concat(field.getName()));
							}else if(column!=null&&column.fieldName().isEmpty()){
								columns.add(BeanUitls.getDbFieldName(field.getName()));
								propertys.add(mark.concat(field.getName()));
							}
						}
					}
				} catch (Exception e) {
				}
			}
		}
		sb.append("INSERT INTO ").append(tableName).append("(");
		for(String column : columns){
			sb.append(column).append(",");
		}
		sb.delete(sb.length()-1, sb.length()).append(")");
		sb.append(" VALUES(");
		for(String propertyc : propertys){
			sb.append(propertyc).append(",");
		}
		sb.delete(sb.length()-1, sb.length()).append(")");
		return sb.toString();
	}
	
	public static String getUpdateSql(Object bean,boolean needNull){
		StringBuffer sb = new StringBuffer();
		String tableName = getTableName(bean);
		Entity entity = bean.getClass().getAnnotation(Entity.class);
		Collection<String> sets = new ArrayList<String>();
		Collection<String> wheres = new ArrayList<String>();
		Class<?> clazz = bean.getClass();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				try {
					if(!needNull&&field.get(bean)==null){
						continue;
					}
					if(entity!=null){
						Column column = field.getAnnotation(Column.class);
						PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
						if(primaryKey!=null){
							if(column!=null&&!column.fieldName().isEmpty()){
								wheres.add(column.fieldName()+"=:"+field.getName()+" AND ");
							}else{
								wheres.add(BeanUitls.getDbFieldName(field.getName())+"=:"+field.getName()+" AND ");
							}
						}else if(primaryKey==null){
							if(column!=null&&!column.fieldName().isEmpty()){
								sets.add(column.fieldName()+"=:"+field.getName()+",");
							}else if(column!=null&&column.fieldName().isEmpty()){
								sets.add(BeanUitls.getDbFieldName(field.getName())+"=:"+field.getName()+",");
							}
						}
					}
				} catch (Exception e) {
				}
			}
		}
		sb.append("UPDATE ").append(tableName).append(" SET ");
		for(String set : sets){
			sb.append(set);
		}
		sb.delete(sb.length()-1, sb.length());
		if(!wheres.isEmpty()){
			sb.append(" WHERE ");
			for(String where : wheres){
				sb.append(where);
			}
			sb.delete(sb.length()-5, sb.length());
		}else{
			return null;
		}
		return sb.toString();
	}
	
	public static String getDeleteSql(Class<?> clazz){
		StringBuffer sb = new StringBuffer();
		Entity entity = clazz.getAnnotation(Entity.class);
		String tableName = entity.tableName();
		Collection<String> wheres = new ArrayList<String>();
		root:for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				try {
					if(entity!=null){
						Column column = field.getAnnotation(Column.class);
						PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
						if(primaryKey!=null){
							if(column!=null&&!column.fieldName().isEmpty()){
								wheres.add(column.fieldName()+"=?");
							}else{
								wheres.add(BeanUitls.getDbFieldName(field.getName())+"=?");
							}
							break root;
						}
					}
				} catch (Exception e) {
				}
			}
		}
		sb.append("DELETE FROM ").append(tableName);
		if(!wheres.isEmpty()){
			sb.append(" WHERE ");
			for(String where : wheres){
				sb.append(where);
			}
		}else{
			return null;
		}
		return sb.toString();
	}
	
	public static String getDeleteSql(Object whereBean){
		StringBuffer sb = new StringBuffer();
		String tableName = getTableName(whereBean);
		Entity entity = whereBean.getClass().getAnnotation(Entity.class);
		Collection<String> wheres = new ArrayList<String>();
		Class<?> clazz = whereBean.getClass();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				try {
					if(field.get(whereBean)==null){
						continue;
					}
					if(entity!=null){
						Column column = field.getAnnotation(Column.class);
						if(column!=null&&!column.fieldName().isEmpty()){
							wheres.add(column.fieldName()+"=:"+field.getName()+" AND ");
						}else{
							wheres.add(BeanUitls.getDbFieldName(field.getName())+"=:"+field.getName()+" AND ");
						}
					}
				} catch (Exception e) {
				}
			}
		}
		sb.append("DELETE FROM ").append(tableName);
		if(!wheres.isEmpty()){
			sb.append(" WHERE ");
			for(String where : wheres){
				sb.append(where);
			}
			sb.delete(sb.length()-5, sb.length());
		}else{
			return null;
		}
		return sb.toString();
	}
	
	public static String getSelectSql(Class<?> clazz){
		StringBuffer sb = new StringBuffer();
		Entity entity = clazz.getAnnotation(Entity.class);
		String tableName = entity.tableName();
		Collection<String> columns = new ArrayList<String>();
		Collection<String> wheres = new ArrayList<String>();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				try {
					if(entity!=null){
						Column column = field.getAnnotation(Column.class);
						PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
						if(primaryKey!=null){
							if(column!=null&&!column.fieldName().isEmpty()){
								wheres.add(column.fieldName()+"=?");
							}else{
								wheres.add(BeanUitls.getDbFieldName(field.getName())+"=?");
							}
						}
						if(column!=null&&!column.fieldName().isEmpty()){
							columns.add(column.fieldName()+" "+field.getName());
						}else{
							columns.add(BeanUitls.getDbFieldName(field.getName()));
						}
					}
				} catch (Exception e) {
				}
			}
		}
		sb.append("SELECT ");
		if(!columns.isEmpty()){
			for(String column : columns){
				sb.append(column).append(",");
			}
		}
		sb.delete(sb.length()-1, sb.length());
		sb.append(" FROM ").append(tableName);
		if(!wheres.isEmpty()){
			sb.append(" WHERE ");
			for(String where : wheres){
				sb.append(where);
			}
		}else{
			return null;
		}
		return sb.toString();
	}
	
	public static String getSelectSql2(Class<?> clazz){
		StringBuffer sb = new StringBuffer();
		Entity entity = clazz.getAnnotation(Entity.class);
		String tableName = entity.tableName();
		Collection<String> columns = new ArrayList<String>();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				try {
					if(entity!=null){
						Column column = field.getAnnotation(Column.class);
						if(column!=null&&!column.fieldName().isEmpty()){
							columns.add(column.fieldName()+" "+field.getName());
						}else{
							columns.add(BeanUitls.getDbFieldName(field.getName()));
						}
					}
				} catch (Exception e) {
				}
			}
		}
		sb.append("SELECT ");
		if(!columns.isEmpty()){
			for(String column : columns){
				sb.append(column).append(",");
			}
		}
		sb.delete(sb.length()-1, sb.length());
		sb.append(" FROM ").append(tableName);
		return sb.toString();
	}
	
	public static String getWhereBean(Object whereBean){
		StringBuffer sb = new StringBuffer();
		Entity entity = whereBean.getClass().getAnnotation(Entity.class);
		Collection<String> wheres = new ArrayList<String>();
		Class<?> clazz = whereBean.getClass();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				try {
					if(field.get(whereBean)==null){
						continue;
					}
					if(entity!=null){
						Column column = field.getAnnotation(Column.class);
						if(column!=null&&!column.fieldName().isEmpty()){
							wheres.add(column.fieldName()+"=:"+field.getName()+" AND ");
						}else{
							wheres.add(BeanUitls.getDbFieldName(field.getName())+"=:"+field.getName()+" AND ");
						}
					}
				} catch (Exception e) {
				}
			}
		}
		if(!wheres.isEmpty()){
			sb.append(" WHERE ");
			for(String where : wheres){
				sb.append(where);
			}
			sb.delete(sb.length()-5, sb.length());
		}else{
			return null;
		}
		return sb.toString();
	}
	
}
