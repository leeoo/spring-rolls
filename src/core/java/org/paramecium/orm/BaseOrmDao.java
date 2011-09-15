package org.paramecium.orm;

import java.io.Serializable;
import java.util.Collection;

import org.paramecium.jdbc.dialect.Page;

/**
 * 功 能 描 述:<br>
 * ORM映射DAO通用接口
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-9-15下午03:44:56
 * <br>项 目 信 息:paramecium:org.paramecium.orm.BaseOrmDao.java
 */
public interface BaseOrmDao<T , PK extends Serializable> {

	/**
	 * 插入新实体，自动判断是否生成自增数据，如果有则返回。
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public Number insert(T bean) throws Exception;
	
	/**
	 * 批量插入
	 * @param bean
	 * @throws Exception
	 */
	public void insert(Collection<T> beans) throws Exception;
	
	/**
	 * 修改实体
	 * @param bean
	 * @throws Exception
	 */
	public void update(T bean) throws Exception;
	
	/**
	 * 只修改实体非空字段的数据
	 * @param bean
	 * @throws Exception
	 */
	public void updateNotNull(T bean) throws Exception;
	
	/**
	 * 根据单一主键删除实体
	 * @param primaryKey
	 * @throws Exception
	 */
	public void delete(PK primaryKey)throws Exception;
	
	/**
	 * 根据动态条件删除实体，联合主键可以使用该方法进行删除实体
	 * @param whereBean
	 * @throws Exception
	 */
	public void delete(T whereBean)throws Exception;
	
	/**
	 * 根据单一主键获得实体信息
	 * @param primaryKey
	 * @return
	 */
	public T select(PK primaryKey);
	
	/**
	 * 获得分页信息
	 * @param page
	 * @return
	 */
	public Page select(Page page);
	
	/**
	 * 根据动态查询条件获得分页信息
	 * @param page
	 * @param whereBean
	 * @return
	 */
	public Page select(Page page,T whereBean);
	
	/**
	 * 根据动态条件获得所有相符的实体集合
	 * @param whereBean
	 * @return
	 */
	public Collection<T> select(T whereBean);
	
}
