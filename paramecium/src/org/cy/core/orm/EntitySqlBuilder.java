package org.cy.core.orm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.cy.core.commons.BeanUitls;
import org.cy.core.orm.annotation.Column;
import org.cy.core.orm.annotation.Entity;
import org.cy.core.orm.annotation.NotUpdate;
import org.cy.core.orm.annotation.PrimaryKey;
import org.cy.core.orm.annotation.ReferenceColumn;
import org.cy.core.orm.annotation.VirtualColumn;
import org.cy.core.orm.annotation.PrimaryKey.AUTO_GENERATE_MODE;

public class EntitySqlBuilder {
	
	private final static ConcurrentMap<String, String> sqlCache = new ConcurrentHashMap<String, String>();
	
	public static String getInsertSql(Object bean){
		String key = bean.getClass().getName()+":insert";
		String sql = sqlCache.get(key);
		if(sql!=null){
			return sql;
		}
		Entity entity = bean.getClass().getAnnotation(Entity.class);
		String tableName = entity.tableName();
		StringBuffer sb = new StringBuffer();
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
		sqlCache.put(key, sb.toString());
		return sb.toString();
	}
	
	public static String getUpdateSql(Object bean){
		String key = bean.getClass().getName()+":update";
		String sql = sqlCache.get(key);
		if(sql!=null){
			return sql;
		}
		Entity entity = bean.getClass().getAnnotation(Entity.class);
		String tableName = entity.tableName();
		StringBuffer sb = new StringBuffer();
		Collection<String> sets = new ArrayList<String>();
		Collection<String> wheres = new ArrayList<String>();
		Class<?> clazz = bean.getClass();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				try {
					if(entity!=null){
						NotUpdate notUpdate = field.getAnnotation(NotUpdate.class);
						if(notUpdate!=null){
							continue;
						}
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
		sqlCache.put(key, sb.toString());
		return sb.toString();
	}
	
	public static String getDeleteSql(Class<?> clazz){
		String key = clazz.getName()+":delete";
		String sql = sqlCache.get(key);
		if(sql!=null){
			return sql;
		}
		Entity entity = clazz.getAnnotation(Entity.class);
		String tableName = entity.tableName();
		StringBuffer sb = new StringBuffer();
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
		sqlCache.put(key, sb.toString());
		return sb.toString();
	}
	
	public static String getSelectSqlByPk(Class<?> clazz){
		String key = clazz.getName()+":select";
		String sql = sqlCache.get(key);
		if(sql!=null){
			return sql;
		}
		Entity entity = clazz.getAnnotation(Entity.class);
		String tableName = entity.tableName();
		StringBuffer sb = new StringBuffer();
		Collection<String> columns = new ArrayList<String>();
		Collection<String> wheres = new ArrayList<String>();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				try {
					if(entity!=null){
						Column column = field.getAnnotation(Column.class);
						ReferenceColumn referenceColumn = field.getAnnotation(ReferenceColumn.class);
						PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
						if(primaryKey!=null&&wheres.isEmpty()){
							if(column!=null&&!column.fieldName().isEmpty()){
								wheres.add(column.fieldName()+"=?");
							}else{
								wheres.add(BeanUitls.getDbFieldName(field.getName())+"=?");
							}
						}
						if(column!=null&&!column.fieldName().isEmpty()){
							columns.add(column.fieldName()+" "+field.getName());
						}else if(column!=null&&column.fieldName().isEmpty()){
							columns.add(BeanUitls.getDbFieldName(field.getName()));
						}else if(referenceColumn!=null&&referenceColumn.subSelectSql()!=null&&!referenceColumn.subSelectSql().isEmpty()){
							columns.add(referenceColumn.subSelectSql());
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
		sqlCache.put(key, sb.toString());
		return sb.toString();
	}
	
	public static String getDynamicWhereSql(Object dynamicWhereBean){
		StringBuffer sb = new StringBuffer();
		Entity entity = dynamicWhereBean.getClass().getAnnotation(Entity.class);
		Collection<String> wheres = new ArrayList<String>();
		Class<?> clazz = dynamicWhereBean.getClass();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				try {
					if(field.get(dynamicWhereBean)==null){
						continue;
					}
					if(entity!=null){
						VirtualColumn virtualColumn = field.getAnnotation(VirtualColumn.class);
						Column column = field.getAnnotation(Column.class);
						PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
						if(primaryKey!=null){
							if(column!=null&&!column.fieldName().isEmpty()){
								wheres.add(column.logical()+column.fieldName()+column.comparison()+":"+field.getName());
							}else{
								wheres.add(VirtualColumn.DYNAMIC_WHERE_LOGIC.AND+BeanUitls.getDbFieldName(field.getName())+"=:"+field.getName());
							}
						}else if(virtualColumn!=null){
							wheres.add(virtualColumn.logical()+":"+field.getName()+virtualColumn.comparison()+virtualColumn.comparisonColumn());
						}else if(column!=null&&column.isDynamicWhere()){
							if(!column.fieldName().isEmpty()){
								wheres.add(column.logical()+column.fieldName()+column.comparison()+":"+field.getName());
							}else{
								wheres.add(column.logical()+BeanUitls.getDbFieldName(field.getName())+column.comparison()+":"+field.getName());
							}
						}
					}
				} catch (Exception e) {
				}
			}
		}
		for(String where : wheres){
			sb.append(where);
		}
		sb.delete(0, 4);
		return sb.toString();
	}

}
