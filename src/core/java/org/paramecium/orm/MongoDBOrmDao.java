package org.paramecium.orm;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.paramecium.commons.BeanUtils;
import org.paramecium.commons.EncodeUtils;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.nosql.mongodb.GenericMonogDBNativeDao;
import org.paramecium.orm.annotation.Column;
import org.paramecium.orm.annotation.Entity;
import org.paramecium.orm.annotation.PrimaryKey;
import org.paramecium.orm.annotation.PrimaryKey.AUTO_GENERATE_MODE;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
/**
 * 功能描述(Description):<br><b>
 * MongoDB数据库的ROM映射操作类
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-8-19下午10:02:22</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.orm.MongoDBOrmDao.java</b>
 */
public class MongoDBOrmDao <T , PK extends Serializable> implements BaseOrmDao<T , PK>{
	
	private final static Log logger = LoggerFactory.getLogger();
	
	private GenericMonogDBNativeDao mongoDao; 
	
	private Class<T> clazz;
	
	public MongoDBOrmDao(Class<T> clazz){
		mongoDao = new GenericMonogDBNativeDao();
		this.clazz = clazz;
	}
	
	/**
	 * 默认构造方法会自动加载事务线程
	 * 如果启用NoSql，将会自动使用MongoDB。
	 */
	public MongoDBOrmDao(final String dataSourceName,Class<T> clazz){
		mongoDao = new GenericMonogDBNativeDao(dataSourceName);
		this.clazz = clazz;
	}


	public GenericMonogDBNativeDao getMongoDao() {
		return mongoDao;
	}
	
	/**
	 * 插入新实体，自动判断是否生成自增数据，如果有则返回。
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public Number insert(T bean) throws Exception {
		Entity entity = bean.getClass().getAnnotation(Entity.class);
		EntitySqlParser.isEntity(entity);
		String tableName = entity.tableName();
		DBObject object = new BasicDBObject();
		long id = 0;
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				if(Modifier.isStatic(field.getModifiers())){
					continue;
				}
				field.setAccessible(true);
				try {
					PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
					String fieldName = getFieldName(field);
					if(primaryKey!=null){
						if(primaryKey.autoGenerateMode() == AUTO_GENERATE_MODE.NATIVE){
							id = EncodeUtils.millisTime();
						}else{
							id = (Long)BeanUtils.getFieldValue(bean, field.getName());
						}
						object.put(fieldName, id);
						continue;
					}
					object.put(fieldName, BeanUtils.getFieldValue(bean, field.getName(), field.getType()));
				} catch (Throwable e) {
					logger.error(e);
				}
			}
		}
		mongoDao.save(tableName, object);
		return id;
	}
	

	/**
	 * 批量插入
	 * @param bean
	 * @throws Exception
	 */
	public void insert(Collection<T> beans) throws Exception {
		Entity entity = beans.iterator().next().getClass().getAnnotation(Entity.class);
		EntitySqlParser.isEntity(entity);
		String tableName = entity.tableName();
		List<DBObject> objects = new ArrayList<DBObject>();
		for(T bean : beans){
			DBObject object = new BasicDBObject();
			for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
				Field[] fields = superClass.getDeclaredFields();
				for(Field field : fields){
					if(Modifier.isStatic(field.getModifiers())){
						continue;
					}
					field.setAccessible(true);
					try {
						String fieldName = getFieldName(field);
						object.put(fieldName, BeanUtils.getFieldValue(bean, field.getName(), field.getType()));
					} catch (Throwable e) {
						logger.error(e);
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
	 * @throws Exception
	 */
	public void update(T bean) throws Exception {
		updateMongo(bean,false);
	}
	
	/**
	 * 只修改实体非空字段的数据
	 * @param bean
	 * @throws Exception
	 */
	public void updateNotNull(T bean) throws Exception {
		updateMongo(bean,true);
	}
	
	/**
	 * mongoDB修改数据
	 * @param bean
	 * @return
	 */
	private void updateMongo(T bean,boolean isNoNull){
		Entity entity = bean.getClass().getAnnotation(Entity.class);
		EntitySqlParser.isEntity(entity);
		String tableName = entity.tableName();
		DBObject object = new BasicDBObject();
		DBObject where = new BasicDBObject();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				if(Modifier.isStatic(field.getModifiers())){
					continue;
				}
				field.setAccessible(true);
				try {
					Object value = BeanUtils.getFieldValue(bean, field.getName(), field.getType());
					if(value == null&&isNoNull){
						continue;
					}
					String fieldName = getFieldName(field);
					PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
					if(primaryKey!=null){
						where.put(fieldName, value);
					}
					object.put(fieldName, value);//无需判断，更新需要全部更新，不能局部更新
				} catch (Throwable e) {
					logger.error(e);
				}
			}
		}
		mongoDao.update(tableName, where , object);
	}
	
	/**
	 * 根据单一主键删除实体
	 * @param primaryKey
	 * @throws Exception
	 */
	public void delete(PK primaryKey)throws Exception {
		Entity entity = clazz.getAnnotation(Entity.class);
		EntitySqlParser.isEntity(entity);
		String tableName = entity.tableName();
		DBObject where = new BasicDBObject();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				if(Modifier.isStatic(field.getModifiers())){
					continue;
				}
				field.setAccessible(true);
				try {
					String fieldName = getFieldName(field);
					PrimaryKey primaryKey$ = field.getAnnotation(PrimaryKey.class);
					if(primaryKey$!=null){
						where.put(fieldName, primaryKey);
					}
				} catch (Throwable e) {
					logger.error(e);
				}
			}
		}
		mongoDao.remove(tableName, where);
	}
	
	/**
	 * 根据动态条件删除实体，联合主键可以使用该方法进行删除实体
	 * @param whereBean
	 * @throws Exception
	 */
	public void delete(T whereBean)throws Exception {
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
		EntitySqlParser.isEntity(entity);
		for (Class<?> superClass = whereBean.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				if(Modifier.isStatic(field.getModifiers())){
					continue;
				}
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
				} catch (Throwable e) {
					logger.error(e);
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
		Entity entity = clazz.getAnnotation(Entity.class);
		EntitySqlParser.isEntity(entity);
		String tableName = entity.tableName();
		DBObject where = new BasicDBObject();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				if(Modifier.isStatic(field.getModifiers())){
					continue;
				}
				field.setAccessible(true);
				try {
					PrimaryKey primaryKey$ = field.getAnnotation(PrimaryKey.class);
					String fieldName = getFieldName(field);
					if(primaryKey$!=null){
						where.put(fieldName, primaryKey);
					}
				} catch (Throwable e) {
					logger.error(e);
				}
			}
		}
		DBObject object = mongoDao.findOne(tableName, where);
		return (T) BeanUtils.map2Bean(clazz, object.toMap(), true);
	}
	
	/**
	 * 获得分页信息
	 * @param page
	 * @return
	 */
	public Page select(Page page){
		Entity entity = clazz.getAnnotation(Entity.class);
		EntitySqlParser.isEntity(entity);
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
		Entity entity = clazz.getAnnotation(Entity.class);
		EntitySqlParser.isEntity(entity);
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
	public Collection<T> select(T whereBean){
		Entity entity = clazz.getAnnotation(Entity.class);
		EntitySqlParser.isEntity(entity);
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
	
	private static String getFieldName(Field field){
		field.setAccessible(true);
		Column column = field.getAnnotation(Column.class);
		if(column!=null&&!column.value().isEmpty()){
			return column.value();
		}else{
			return BeanUtils.getDbFieldName(field.getName());
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
			beans.add((T) BeanUtils.map2Bean(clazz, object.toMap(), true));
		}
		return beans;
	}
	
}
