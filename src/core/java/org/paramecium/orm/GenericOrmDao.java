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
import org.paramecium.orm.annotation.PrimaryKey;
import org.paramecium.orm.annotation.PrimaryKey.AUTO_GENERATE_MODE;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
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
		if(entity==null){
			throw new RuntimeException("实体没有声明标注@Entity并配置");
		}
		String tableName = entity.tableName();
		DBObject object = new BasicDBObject();
		long id = 0;
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				try {
					PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
					String fieldName = getFieldName(field);
					if(primaryKey!=null){
						if(primaryKey.autoGenerateMode() == AUTO_GENERATE_MODE.NATIVE){
							id = System.currentTimeMillis();
						}else{
							id = (Long)field.get(bean);
						}
						object.put(fieldName, id);
						continue;
					}
						object.put(fieldName, field.get(bean));
				} catch (Exception e) {
				}
			}
		}
		mongoDao.save(tableName, object);
		return id;
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
		if(entity==null){
			throw new RuntimeException("实体没有声明标注@Entity并配置");
		}
		String tableName = entity.tableName();
		List<DBObject> objects = new ArrayList<DBObject>();
		for(T bean : beans){
			DBObject object = new BasicDBObject();
			for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
				Field[] fields = superClass.getDeclaredFields();
				for(Field field : fields){
					field.setAccessible(true);
					try {
						String fieldName = getFieldName(field);
						object.put(fieldName, field.get(bean));
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
			updateMongo(bean,false);
			return;
		}
		String sql = EntitySqlBuilder.getUpdateSql(bean);
		genericJdbcDao.executeDMLByBean(sql, bean);
	}
	
	/**
	 * 只修改实体非空字段的数据
	 * @param bean
	 * @throws SQLException
	 */
	public void updateNotNull(T bean) throws SQLException {
		if(useNoSql){
			updateMongo(bean,true);
			return;
		}
		String sql = EntitySqlBuilder.getUpdateSqlNotNull(bean);
		genericJdbcDao.executeDMLByBean(sql, bean);
	}
	
	/**
	 * mongoDB修改数据
	 * @param bean
	 * @return
	 */
	private void updateMongo(T bean,boolean isNoNull){
		Entity entity = bean.getClass().getAnnotation(Entity.class);
		if(entity==null){
			throw new RuntimeException("实体没有声明标注@Entity并配置");
		}
		String tableName = entity.tableName();
		DBObject object = new BasicDBObject();
		DBObject where = new BasicDBObject();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				try {
					Object value = field.get(bean);
					if(value==null&&isNoNull){
						continue;
					}
					String fieldName = getFieldName(field);
					PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
					if(primaryKey!=null){
						where.put(fieldName, value);
					}
					object.put(fieldName, value);//无需判断，更新需要全部更新，不能局部更新
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
		if(entity==null){
			throw new RuntimeException("实体没有声明标注@Entity并配置");
		}
		String tableName = entity.tableName();
		DBObject where = new BasicDBObject();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				try {
					String fieldName = getFieldName(field);
					PrimaryKey primaryKey$ = field.getAnnotation(PrimaryKey.class);
					if(primaryKey$!=null){
						where.put(fieldName, primaryKey);
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
		if(entity==null){
			throw new RuntimeException("实体没有声明标注@Entity并配置");
		}
		for (Class<?> superClass = whereBean.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				try {
					if(field.get(whereBean)==null||field.get(whereBean).toString().isEmpty()){
						continue;
					}
					Column column = field.getAnnotation(Column.class);
					if(column==null){
						continue;
					}
					PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
					String comparison = column.comparison().trim();
					String fieldName = getFieldName(field);
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
					BasicDBObject query = (BasicDBObject) where.get(fieldName);
					if(column!=null&&(column.isDynamicWhere()||primaryKey!=null)){
						if(query!=null){
							query.append(comparison, field.get(whereBean));
							where.put(fieldName, query);
						}else{
							if(comparison.equals("=")){
								where.put(fieldName, field.get(whereBean));
							}else{
								where.put(fieldName, new BasicDBObject(comparison, field.get(whereBean)));
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
		if(entity==null){
			throw new RuntimeException("实体没有声明标注@Entity并配置");
		}
		String tableName = entity.tableName();
		DBObject where = new BasicDBObject();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				try {
					PrimaryKey primaryKey$ = field.getAnnotation(PrimaryKey.class);
					String fieldName = getFieldName(field);
					if(primaryKey$!=null){
						where.put(fieldName, primaryKey);
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
	private Page selectMongo(Page page){
		Entity entity = clazz.getAnnotation(Entity.class);
		if(entity==null){
			throw new RuntimeException("实体没有声明标注@Entity并配置");
		}
		String tableName = entity.tableName();
		long count = mongoDao.count(tableName);
		page.setTotalCount((int)count);
		DBCursor dbCursor = null;
		if(!entity.orderBy().isEmpty()){
			int sort = 1;
			String filed = entity.orderBy().toLowerCase().trim();
			if(filed.indexOf("desc")>0){
				filed = entity.orderBy().substring(0,filed.indexOf("desc")).trim();
				sort = 0;
			}else if(filed.indexOf("asc")>0){
				filed = entity.orderBy().substring(0,filed.indexOf("asc")).trim();
			}
			dbCursor = mongoDao.find(tableName, page.getPageSize(), page.getFirst(),new BasicDBObject(filed,sort));
		}else{
			dbCursor = mongoDao.find(tableName, page.getPageSize(), page.getFirst());
		}
		page.setResult(buildCollection(dbCursor));
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
	private Page selectMongo(Page page,T whereBean){
		Entity entity = clazz.getAnnotation(Entity.class);
		if(entity==null){
			throw new RuntimeException("实体没有声明标注@Entity并配置");
		}
		String tableName = entity.tableName();
		DBObject where = buildWhere(whereBean);
		long count = mongoDao.count(tableName,where);
		page.setTotalCount((int)count);
		DBCursor dbCursor = null;
		if(!entity.orderBy().isEmpty()){
			int sort = 1;
			String filed = entity.orderBy().toLowerCase().trim();
			if(filed.indexOf("desc")>0){
				filed = entity.orderBy().substring(0,filed.indexOf("desc")).trim();
				sort = 0;
			}else if(filed.indexOf("asc")>0){
				filed = entity.orderBy().substring(0,filed.indexOf("asc")).trim();
			}
			
			dbCursor = mongoDao.find(tableName , where ,new BasicDBObject(filed,sort), page.getPageSize(), page.getFirst());
		}else{
			dbCursor = mongoDao.find(tableName, where , page.getPageSize(), page.getFirst());
		}
		page.setResult(buildCollection(dbCursor));
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
	public Collection<T> selectMongo(T whereBean){
		Entity entity = clazz.getAnnotation(Entity.class);
		if(entity==null){
			throw new RuntimeException("实体没有声明标注@Entity并配置");
		}
		String tableName = entity.tableName();
		DBObject where = buildWhere(whereBean);
		DBCursor dbCursor = null;
		if(!entity.orderBy().isEmpty()){
			int sort = 1;
			String filed = entity.orderBy().toLowerCase().trim();
			if(filed.indexOf("desc")>0){
				filed = entity.orderBy().substring(0,filed.indexOf("desc")).trim();
				sort = 0;
			}else if(filed.indexOf("asc")>0){
				filed = entity.orderBy().substring(0,filed.indexOf("asc")).trim();
			}
			dbCursor = mongoDao.find(tableName , where ,new BasicDBObject(filed,sort));
		}else{
			dbCursor = mongoDao.find(tableName , where);
		}
		return buildCollection(dbCursor);
	}

	public void setUseNoSql(boolean useNoSql) {
		this.useNoSql = useNoSql;
	}

	private static String getFieldName(Field field){
		field.setAccessible(true);
		Column column = field.getAnnotation(Column.class);
		if(column!=null&&!column.fieldName().isEmpty()){
			return column.fieldName();
		}else{
			return BeanUitls.getDbFieldName(field.getName());
		}
	}
	
	@SuppressWarnings("unchecked")
	private Collection<T> buildCollection(DBCursor dbCursor){
		if(dbCursor==null||dbCursor.itcount()<1){
			return null;
		}
		Collection<T> beans = new ArrayList<T>();
		for(Iterator<DBObject> it = dbCursor.iterator();it.hasNext();){
			DBObject object = it.next();
			beans.add((T) BeanUitls.map2Bean(clazz, object.toMap(), true));
		}
		return beans;
	}
	
}
