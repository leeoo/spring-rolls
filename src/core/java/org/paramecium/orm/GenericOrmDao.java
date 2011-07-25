package org.paramecium.orm;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.paramecium.commons.BeanUitls;
import org.paramecium.jdbc.GenericJdbcDao;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.nosql.mongodb.GenericMonogDBNativeDao;
import org.paramecium.orm.annotation.Column;
import org.paramecium.orm.annotation.Entity;
import org.paramecium.orm.annotation.NotUpdate;
import org.paramecium.orm.annotation.PrimaryKey;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
/**
 * 功 能 描 述:<br>
 * 通用ORM数据操作，功能类似hiberante
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-1下午02:37:54
 * <br>项 目 信 息:paramecium:org.paramecium.orm.GenericOrmDao.java
 */
public final class GenericOrmDao<T , PK extends Serializable>{
	
	private GenericJdbcDao genericJdbcDao;
	
	private GenericMonogDBNativeDao mongoDao; 
	
	private boolean useNoSql = false;
	
	private Class<T> clazz;
	
	/**
	 * 默认构造方法会自动加载事务线程
	 * 如果启用NoSql，将会自动使用MongoDB。
	 */
	public GenericOrmDao(final String dataSourceName,Class<T> clazz,boolean useNoSql){
		this.useNoSql = useNoSql;
		if(useNoSql){
			mongoDao = new GenericMonogDBNativeDao(dataSourceName);
		}else{
			genericJdbcDao = new GenericJdbcDao(dataSourceName);
		}
		this.clazz = clazz;
	}
	
	/**
	 * 默认构造方法会自动加载事务线程
	 */
	public GenericOrmDao(final String dataSourceName,Class<T> clazz){
		genericJdbcDao = new GenericJdbcDao(dataSourceName);
		this.clazz = clazz;
	}

	public GenericJdbcDao getGenericJdbcDao() {
		return genericJdbcDao;
	}

	public GenericMonogDBNativeDao getMongoDao() {
		return mongoDao;
	}
	
	/**
	 * 插入新实体，自动判断是否生成自增数据，如果有则返回。
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	public Number insert(T bean) throws SQLException {
		if(useNoSql){
			return insertMongo(bean);
		}
		String sql = EntitySqlBuilder.getInsertSql(bean);
		String isAuto = sql.substring(0, 1);
		if(isAuto.equals("A")){
			return genericJdbcDao.insertGetGeneratedKeyByBean(sql.substring(1, sql.length()), bean);
		}else if(isAuto.equals("M")){
			genericJdbcDao.executeDMLByBean(sql.substring(1, sql.length()), bean);
		}
		return null;
	}
	
	/**
	 * mongoDB保存数据
	 * @param bean
	 * @return
	 */
	private Number insertMongo(T bean){
		Entity entity = bean.getClass().getAnnotation(Entity.class);
		String tableName = entity.tableName();
		DBObject object = new BasicDBObject();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				try {
					if(entity!=null){
						Column column = field.getAnnotation(Column.class);
						if(column!=null&&!column.fieldName().isEmpty()){
							object.put(column.fieldName(), field.get(bean));
						}else if(column!=null&&column.fieldName().isEmpty()){
							object.put(BeanUitls.getDbFieldName(field.getName()), field.get(bean));
						}
					}
				} catch (Exception e) {
				}
			}
		}
		WriteResult result = mongoDao.save(tableName, object);
		return result.getN();
	}

	/**
	 * 批量插入
	 * @param bean
	 * @throws SQLException
	 */
	public void insert(Collection<T> beans) throws SQLException {
		if(useNoSql){
			insertMongo(beans);
			return;
		}
		String sql = EntitySqlBuilder.getInsertSql(beans.iterator().next());
		genericJdbcDao.executeBatchDMLByBeans(sql.substring(1, sql.length()), beans);
	}
	
	/**
	 * mongoDB批量保存数据
	 * @param bean
	 * @return
	 */
	private void insertMongo(Collection<T> beans){
		Entity entity = beans.iterator().next().getClass().getAnnotation(Entity.class);
		String tableName = entity.tableName();
		List<DBObject> objects = new ArrayList<DBObject>();
		for(T bean : beans){
			DBObject object = new BasicDBObject();
			for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
				Field[] fields = superClass.getDeclaredFields();
				for(Field field : fields){
					field.setAccessible(true);
					try {
						if(entity!=null){
							Column column = field.getAnnotation(Column.class);
							if(column!=null&&!column.fieldName().isEmpty()){
								object.put(column.fieldName(), field.get(bean));
							}else if(column!=null&&column.fieldName().isEmpty()){
								object.put(BeanUitls.getDbFieldName(field.getName()), field.get(bean));
							}
						}
					} catch (Exception e) {
					}
				}
			}
			objects.add(object);
		}
		mongoDao.insert(tableName, objects);
	}

	/**
	 * 修改实体
	 * @param bean
	 * @throws SQLException
	 */
	public void update(T bean) throws SQLException {
		if(useNoSql){
			updateMongo(bean);
			return;
		}
		String sql = EntitySqlBuilder.getUpdateSql(bean);
		genericJdbcDao.executeDMLByBean(sql, bean);
	}
	
	/**
	 * mongoDB修改数据
	 * @param bean
	 * @return
	 */
	private void updateMongo(T bean){
		Entity entity = bean.getClass().getAnnotation(Entity.class);
		String tableName = entity.tableName();
		DBObject object = new BasicDBObject();
		DBObject where = new BasicDBObject();
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
								where.put(field.getName(), field.get(bean));
							}else{
								where.put(BeanUitls.getDbFieldName(field.getName()),field.get(bean));
							}
						}else if(primaryKey==null){
							if(column!=null&&!column.fieldName().isEmpty()){
								object.put(column.fieldName(), field.get(bean));
							}else if(column!=null&&column.fieldName().isEmpty()){
								object.put(BeanUitls.getDbFieldName(field.getName()), field.get(bean));
							}
						}
					}
				} catch (Exception e) {
				}
			}
		}
		mongoDao.update(tableName, where , object);
	}
	
	/**
	 * 根据单一主键删除实体
	 * @param primaryKey
	 * @throws SQLException
	 */
	public void delete(PK primaryKey)throws SQLException {
		if(useNoSql){
			deleteMongo(primaryKey);
			return;
		}
		String sql = EntitySqlBuilder.getDeleteSql(clazz);
		genericJdbcDao.executeDMLByArray(sql, primaryKey);
	}
	
	/**
	 * mongoDB删除数据
	 * @param bean
	 * @return
	 */
	private void deleteMongo(PK primaryKey){
		Entity entity = clazz.getAnnotation(Entity.class);
		String tableName = entity.tableName();
		DBObject where = new BasicDBObject();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				try {
					if(entity!=null){
						Column column = field.getAnnotation(Column.class);
						PrimaryKey primaryKey$ = field.getAnnotation(PrimaryKey.class);
						if(primaryKey$!=null){
							if(column!=null&&!column.fieldName().isEmpty()){
								where.put(field.getName(), primaryKey);
							}else{
								where.put(BeanUitls.getDbFieldName(field.getName()),primaryKey);
							}
						}
					}
				} catch (Exception e) {
				}
			}
		}
		mongoDao.remove(tableName, where);
	}

	/**
	 * 根据动态条件删除实体，联合主键可以使用该方法进行删除实体
	 * @param whereBean
	 * @throws SQLException
	 */
	public void delete(T whereBean)throws SQLException {
		if(useNoSql){
			deleteMongo(whereBean);
			return;
		}
		String sql = EntitySqlBuilder.getDeleteSql(clazz);
		int start =sql.lastIndexOf(" WHERE ");
		sql = sql.substring(0, start);
		String where = EntitySqlBuilder.getDynamicWhereSql(whereBean);
		if(where!=null&&!where.isEmpty()){
			sql = sql.concat(" WHERE ").concat(where);
			genericJdbcDao.executeDMLByBean(sql, whereBean);
		}
	}
	
	/**
	 * 带条件mongoDB删除数据
	 * @param bean
	 * @return
	 */
	private void deleteMongo(T whereBean){
		Entity entity = clazz.getAnnotation(Entity.class);
		String tableName = entity.tableName();
		DBObject where = buildWhere(whereBean);
		mongoDao.remove(tableName, where);
	}
	
	/**
	 * 构建MongoDB的where条件
	 * @param whereBean
	 * @return
	 */
	private static DBObject buildWhere(Object whereBean){
		Entity entity = whereBean.getClass().getAnnotation(Entity.class);
		DBObject where = new BasicDBObject();
		for (Class<?> superClass = whereBean.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				try {
					if(field.get(whereBean)==null||field.get(whereBean).toString().isEmpty()){
						continue;
					}
					if(entity!=null){
						Column column = field.getAnnotation(Column.class);
						PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
						String comparison = column.comparison().trim();
						if(comparison.equals("<")){
							comparison = "$lt";
						}else if(comparison.equals("<=")){
							comparison = "$lte";
						}else if(comparison.equals(">")){
							comparison = "$gt";
						}else if(comparison.equals(">=")){
							comparison = "$gte";
						}else if(comparison.equals("LIKE")){
							continue;
						}
						BasicDBObject query = (BasicDBObject) where.get(field.getName());
						if(column!=null&&(column.isDynamicWhere()||primaryKey!=null)){
							String filedName = column.fieldName();
							if(filedName.isEmpty()){
								filedName = BeanUitls.getDbFieldName(field.getName());
							}
							if(query!=null){
								query.append(comparison, field.get(whereBean));
								where.put(filedName, query);
							}else{
								if(comparison.equals("=")){
									where.put(filedName, field.get(whereBean));
								}else{
									where.put(filedName, new BasicDBObject(comparison, field.get(whereBean)));
								}
							}
						}
					}
				} catch (Exception e) {
				}
			}
		}
		return where;
	}
	
	/**
	 * 根据单一主键获得实体信息
	 * @param primaryKey
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T select(PK primaryKey){
		if(useNoSql){
			return selectMongo(primaryKey);
		}
		String sql = EntitySqlBuilder.getSelectSqlByPk(clazz);
		return (T) genericJdbcDao.queryUniqueByArray(sql, clazz, primaryKey);
	}
	
	/**
	 * 根据单一主键获得实体信息
	 * @param primaryKey
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private T selectMongo(PK primaryKey){
		Entity entity = clazz.getAnnotation(Entity.class);
		String tableName = entity.tableName();
		DBObject where = new BasicDBObject();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				try {
					if(entity!=null){
						Column column = field.getAnnotation(Column.class);
						PrimaryKey primaryKey$ = field.getAnnotation(PrimaryKey.class);
						if(primaryKey$!=null){
							if(column!=null&&!column.fieldName().isEmpty()){
								where.put(field.getName(), primaryKey);
							}else{
								where.put(BeanUitls.getDbFieldName(field.getName()),primaryKey);
							}
						}
					}
				} catch (Exception e) {
				}
			}
		}
		DBObject object = mongoDao.findOne(tableName, where);
		return (T) BeanUitls.map2Bean(clazz, object.toMap(), true);
	}

	/**
	 * 获得分页信息
	 * @param page
	 * @return
	 */
	public Page select(Page page){
		if(useNoSql){
			return selectMongo(page);
		}
		String sql = EntitySqlBuilder.getSelectSqlByPk(clazz);
		int start =sql.lastIndexOf(" WHERE ");
		sql = sql.substring(0, start);
		Entity entity = clazz.getAnnotation(Entity.class);
		if(entity!=null&&!entity.orderBy().isEmpty()){
			sql = sql.concat(" ORDER BY "+entity.orderBy());
		}
		return genericJdbcDao.queryPageBeansByArray(sql, clazz, page);
	}
	
	/**
	 * 获得mongo分页信息
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Page selectMongo(Page page){
		Entity entity = clazz.getAnnotation(Entity.class);
		String tableName = entity.tableName();
		long count = mongoDao.count(tableName);
		page.setTotalCount((int)count);
		DBCursor dbCursor = null;
		if(entity!=null&&!entity.orderBy().isEmpty()){
			int sort = 1;
			String filed = entity.orderBy().toLowerCase().trim();
			if(entity.orderBy().toLowerCase().indexOf("desc")>0){
				filed = entity.orderBy().substring(0,entity.orderBy().toLowerCase().indexOf("desc")).trim();
				sort = 0;
			}else if(entity.orderBy().toLowerCase().indexOf("asc")>0){
				filed = entity.orderBy().substring(0,entity.orderBy().toLowerCase().indexOf("asc")).trim();
			}
			dbCursor = mongoDao.find(tableName, page.getFirst(), page.getPageSize(),new BasicDBObject(filed,sort));
		}else{
			dbCursor = mongoDao.find(tableName, page.getFirst(), page.getPageSize());
		}
		Collection<T> beans = new ArrayList<T>();
		for(Iterator<DBObject> it = dbCursor.iterator();it.hasNext();){
			DBObject object = it.next();
			beans.add((T) BeanUitls.map2Bean(clazz, object.toMap(), true));
		}
		page.setResult(beans);
		return page;
	}
	
	

	/**
	 * 根据动态查询条件获得分页信息
	 * @param page
	 * @param whereBean
	 * @return
	 */
	public Page select(Page page,T whereBean){
		if(useNoSql){
			return selectMongo(page,whereBean);
		}
		if(whereBean==null){
			return select(page);
		}
		String sql = EntitySqlBuilder.getSelectSqlByPk(clazz);
		int start =sql.lastIndexOf(" WHERE ");
		sql = sql.substring(0, start);
		String where = EntitySqlBuilder.getDynamicWhereSql(whereBean);
		if(where==null||where.isEmpty()){
			return select(page);
		}
		sql = sql.concat(" WHERE ").concat(where);
		Entity entity = clazz.getAnnotation(Entity.class);
		if(entity!=null&&!entity.orderBy().isEmpty()){
			sql = sql.concat(" ORDER BY "+entity.orderBy());
		}
		return genericJdbcDao.queryPageBeansByBean(sql, clazz, page,whereBean);
	}
	
	/**
	 * mongo根据动态查询条件获得分页信息
	 * @param page
	 * @param whereBean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Page selectMongo(Page page,T whereBean){
		Entity entity = clazz.getAnnotation(Entity.class);
		String tableName = entity.tableName();
		DBObject where = buildWhere(whereBean);
		long count = mongoDao.count(tableName,where);
		page.setTotalCount((int)count);
		DBCursor dbCursor = null;
		if(entity!=null&&!entity.orderBy().isEmpty()){
			int sort = 1;
			String filed = entity.orderBy().toLowerCase().trim();
			if(entity.orderBy().toLowerCase().indexOf("desc")>0){
				filed = entity.orderBy().substring(0,entity.orderBy().toLowerCase().indexOf("desc")).trim();
				sort = 0;
			}else if(entity.orderBy().toLowerCase().indexOf("asc")>0){
				filed = entity.orderBy().substring(0,entity.orderBy().toLowerCase().indexOf("asc")).trim();
			}
			dbCursor = mongoDao.find(tableName , where ,new BasicDBObject(filed,sort), page.getFirst(), page.getPageSize());
		}else{
			dbCursor = mongoDao.find(tableName, where , page.getFirst(), page.getPageSize());
		}
		Collection<T> beans = new ArrayList<T>();
		for(Iterator<DBObject> it = dbCursor.iterator();it.hasNext();){
			DBObject object = it.next();
			beans.add((T) BeanUitls.map2Bean(clazz, object.toMap(), true));
		}
		page.setResult(beans);
		return page;
	}
	
	/**
	 * 根据动态条件获得所有相符的实体集合
	 * @param whereBean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Collection<T> select(T whereBean){
		if(useNoSql){
			return selectMongo(whereBean);
		}
		if(whereBean==null){
			return null;
		}
		String sql = EntitySqlBuilder.getSelectSqlByPk(clazz);
		int start =sql.lastIndexOf(" WHERE ");
		sql = sql.substring(0, start);
		String where = EntitySqlBuilder.getDynamicWhereSql(whereBean);
		if(where==null||where.isEmpty()){
			return null;
		}
		sql = sql.concat(" WHERE ").concat(where);
		Entity entity = clazz.getAnnotation(Entity.class);
		if(entity!=null&&!entity.orderBy().isEmpty()){
			sql = sql.concat(" ORDER BY "+entity.orderBy());
		}
		return (Collection<T>) genericJdbcDao.queryByBean(sql, clazz, whereBean);
	}
	
	/**
	 * mongo根据动态条件获得所有相符的实体集合
	 * @param whereBean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Collection<T> selectMongo(T whereBean){
		Entity entity = clazz.getAnnotation(Entity.class);
		String tableName = entity.tableName();
		DBObject where = buildWhere(whereBean);
		DBCursor dbCursor = null;
		if(entity!=null&&!entity.orderBy().isEmpty()){
			int sort = 1;
			String filed = entity.orderBy().toLowerCase().trim();
			if(entity.orderBy().toLowerCase().indexOf("desc")>0){
				filed = entity.orderBy().substring(0,entity.orderBy().toLowerCase().indexOf("desc")).trim();
				sort = 0;
			}else if(entity.orderBy().toLowerCase().indexOf("asc")>0){
				filed = entity.orderBy().substring(0,entity.orderBy().toLowerCase().indexOf("asc")).trim();
			}
			dbCursor = mongoDao.find(tableName , where ,new BasicDBObject(filed,sort));
		}else{
			dbCursor = mongoDao.find(tableName , where);
		}
		Collection<T> beans = new ArrayList<T>();
		for(Iterator<DBObject> it = dbCursor.iterator();it.hasNext();){
			DBObject object = it.next();
			beans.add((T) BeanUitls.map2Bean(clazz, object.toMap(), true));
		}
		return beans;
	}

	public void setUseNoSql(boolean useNoSql) {
		this.useNoSql = useNoSql;
	}

}
