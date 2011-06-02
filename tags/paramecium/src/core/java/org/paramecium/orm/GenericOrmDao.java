package org.paramecium.orm;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;

import org.paramecium.jdbc.GenericJdbcDao;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.orm.annotation.Entity;
/**
 * 功 能 描 述:<br>
 * 通用ORM数据操作，功能类似hiberante
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-1下午02:37:54
 * <br>项 目 信 息:paramecium:org.paramecium.orm.GenericOrmDao.java
 */
public final class GenericOrmDao<T , PK extends Serializable>{
	
	private GenericJdbcDao genericJdbcDao;
	
	private Class<T> clazz;
	
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
	 * 插入新实体，自动生成序号，并返回。
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	public Number insert(T bean) throws SQLException {
		String sql = EntitySqlBuilder.getInsertSql(bean);
		return genericJdbcDao.insertGetGeneratedKeyByBean(sql, bean);
	}

	/**
	 * 插入新实体，需手动生成主键
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	public void insertManualPK(T bean) throws SQLException {
		String sql = EntitySqlBuilder.getInsertSql(bean);
		genericJdbcDao.executeDMLByBean(sql, bean);
	}

	/**
	 * 批量插入
	 * @param bean
	 * @throws SQLException
	 */
	public void insert(Collection<T> beans) throws SQLException {
		String sql = EntitySqlBuilder.getInsertSql(beans.iterator().next());
		genericJdbcDao.executeBatchDMLByBeans(sql, beans);
	}

	/**
	 * 修改实体
	 * @param bean
	 * @throws SQLException
	 */
	public void update(T bean) throws SQLException {
		String sql = EntitySqlBuilder.getUpdateSql(bean);
		genericJdbcDao.executeDMLByBean(sql, bean);
	}
	
	/**
	 * 根据单一主键删除实体
	 * @param primaryKey
	 * @throws SQLException
	 */
	public void delete(PK primaryKey)throws SQLException {
		String sql = EntitySqlBuilder.getDeleteSql(clazz);
		genericJdbcDao.executeDMLByArray(sql, primaryKey);
	}

	/**
	 * 根据动态条件删除实体，联合主键可以使用该方法进行删除实体
	 * @param whereBean
	 * @throws SQLException
	 */
	public void delete(T whereBean)throws SQLException {
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
	 * 根据单一主键获得实体信息
	 * @param primaryKey
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T select(PK primaryKey){
		String sql = EntitySqlBuilder.getSelectSqlByPk(clazz);
		return (T) genericJdbcDao.queryUniqueByArray(sql, clazz, primaryKey);
	}

	/**
	 * 获得分页信息
	 * @param page
	 * @return
	 */
	public Page select(Page page){
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
	 * 根据动态查询条件获得分页信息
	 * @param page
	 * @param whereBean
	 * @return
	 */
	public Page select(Page page,T whereBean){
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
	 * 根据动态条件获得所有相符的实体集合
	 * @param whereBean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Collection<T> select(T whereBean){
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

}
