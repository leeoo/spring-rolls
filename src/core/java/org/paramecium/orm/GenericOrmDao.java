package org.paramecium.orm;

import java.io.Serializable;
import java.util.Collection;

import org.paramecium.jdbc.GenericJdbcDao;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.orm.annotation.Entity;
import org.paramecium.validation.Validator;
/**
 * 功 能 描 述:<br>
 * 通用ORM数据操作，功能类似hiberante
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-1下午02:37:54
 * <br>项 目 信 息:paramecium:org.paramecium.orm.GenericOrmDao.java
 */
public class GenericOrmDao<T , PK extends Serializable> implements BaseOrmDao<T , PK>{
	
	private GenericJdbcDao genericJdbcDao;
	
	private Class<T> clazz;
	
	private boolean isValidation = false;//是否操作数据库时启用自动验证
	
	/**
	 * 设置操作数据库时是否启用自动验证,默认为false，即不启动。
	 * @param isValidation
	 */
	public void setValidation(boolean isValidation) {
		this.isValidation = isValidation;
	}
	
	public GenericOrmDao(Class<T> clazz){
		genericJdbcDao = new GenericJdbcDao();
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

	/**
	 * 插入新实体，自动判断是否生成自增数据，如果有则返回。
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public Number insert(T bean) throws Exception {
		if(isValidation){
			Validator.validation(bean);
		}
		String sql = EntitySqlParser.getInsertSql(bean);
		String isAuto = sql.substring(0, 1);
		if(isAuto.equals("A")){
			return genericJdbcDao.insertGetGeneratedKeyByBean(sql.substring(1, sql.length()), bean);
		}else if(isAuto.equals("M")){
			genericJdbcDao.executeDMLByBean(sql.substring(1, sql.length()), bean);
		}
		return null;
	}
	
	/**
	 * 批量插入
	 * @param bean
	 * @throws Exception
	 */
	public void insert(Collection<T> beans) throws Exception {
		if(isValidation){
			for(T bean : beans){
				Validator.validation(bean);
			}
		}
		String sql = EntitySqlParser.getInsertSql(beans.iterator().next());
		genericJdbcDao.executeBatchDMLByBeans(sql.substring(1, sql.length()), beans);
	}
	
	/**
	 * 修改实体
	 * @param bean
	 * @throws Exception
	 */
	public void update(T bean) throws Exception {
		if(isValidation){
			Validator.validationByUpdate(bean);
		}
		String sql = EntitySqlParser.getUpdateSql(bean);
		genericJdbcDao.executeDMLByBean(sql, bean);
	}
	
	/**
	 * 只修改实体非空字段的数据
	 * @param bean
	 * @throws Exception
	 */
	public void updateNotNull(T bean) throws Exception {
		String sql = EntitySqlParser.getUpdateSqlNotNull(bean);
		genericJdbcDao.executeDMLByBean(sql, bean);
	}
	
	/**
	 * 根据单一主键删除实体
	 * @param primaryKey
	 * @throws Exception
	 */
	public void delete(PK primaryKey)throws Exception {
		String sql = EntitySqlParser.getDeleteSql(clazz);
		genericJdbcDao.executeDMLByArray(sql, primaryKey);
	}
	
	/**
	 * 根据动态条件删除实体，联合主键可以使用该方法进行删除实体
	 * @param whereBean
	 * @throws Exception
	 */
	public void delete(T whereBean)throws Exception {
		String sql = EntitySqlParser.getDeleteSql(clazz);
		int start = sql.lastIndexOf(" WHERE ");
		sql = sql.substring(0, start);
		String where = EntitySqlParser.getDynamicWhereSql(whereBean);
		if(where!=null&&!where.isEmpty()){
			sql = sql.concat(" WHERE ").concat(where);
			genericJdbcDao.executeDMLByBean(sql, whereBean);
		}
	}
	
	/**
	 * 快速清除表信息，谨慎使用，无事务、日志。
	 * @throws Exception
	 */
	public void truncate()throws Exception {
		Entity entity = clazz.getAnnotation(Entity.class);
		EntitySqlParser.isEntity(entity);
		String tableName = entity.tableName();
		genericJdbcDao.executeDML("TRUNCATE TABLE ".concat(tableName));
	}
	
	/**
	 * 根据单一主键获得实体信息
	 * @param primaryKey
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T select(PK primaryKey){
		String sql = EntitySqlParser.getSelectSqlByPk(clazz);
		return (T) genericJdbcDao.queryUniqueByArray(sql, clazz, primaryKey);
	}
	
	/**
	 * 获得分页信息
	 * @param page
	 * @return
	 */
	public Page select(Page page){
		String sql = EntitySqlParser.getSelectSqlByPk(clazz);
		int start = sql.lastIndexOf(" WHERE ");
		sql = sql.substring(0, start);
		Entity entity = clazz.getAnnotation(Entity.class);
		if(entity!=null&&!entity.orderBy().isEmpty()){
			sql = sql.concat(" ORDER BY "+entity.orderBy());
		}
		return genericJdbcDao.queryPageBeansByArray(sql, clazz, page);
	}
	
	/**
	 * 根据动态查询条件获得分页信息
	 * @param page
	 * @param whereBean
	 * @return
	 */
	public Page select(Page page,T whereBean){
		if(whereBean==null){
			return select(page);
		}
		String sql = EntitySqlParser.getSelectSqlByPk(clazz);
		int start = sql.lastIndexOf(" WHERE ");
		sql = sql.substring(0, start);
		String where = EntitySqlParser.getDynamicWhereSql(whereBean);
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
	 * 根据动态条件获得所有相符的实体集合
	 * @param whereBean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Collection<T> select(T whereBean){
		if(whereBean==null){
			return null;
		}
		String sql = EntitySqlParser.getSelectSqlByPk(clazz);
		int start = sql.lastIndexOf(" WHERE ");
		sql = sql.substring(0, start);
		String where = EntitySqlParser.getDynamicWhereSql(whereBean);
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

}
