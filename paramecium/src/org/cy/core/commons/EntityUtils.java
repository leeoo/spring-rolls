package org.cy.core.commons;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
		String tableName = "t"+BeanUitls.getDbFieldName(bean.getClass().getSimpleName()).toLowerCase();
		try{
			Entity entity = bean.getClass().getAnnotation(Entity.class);
			if(entity!=null&&entity.tableName()!=null){
				tableName = entity.tableName();
			}
		}catch (Exception e) {}
		return tableName;
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
					}else{
						columns.add(BeanUitls.getDbFieldName(field.getName()));
						propertys.add(mark.concat(field.getName()));
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
	
	public static Set<PrimaryKey> getPrimaryKey(Class<?> entityClass){
		Set<PrimaryKey> pks = new HashSet<PrimaryKey>();
		for (Class<?> superClass = entityClass; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				try {
					PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
					if(primaryKey!=null){
						pks.add(primaryKey);
					}
				} catch (Exception e) {
				}
			}
		}
		return pks;
	}
	
}
