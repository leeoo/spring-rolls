package org.paramecium.orm;

import java.io.Serializable;

import org.paramecium.commons.BeanUtils;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.search.SearchIndexCreator;

public class LuceneOrmDao <T , PK extends Serializable>{
	
	private GenericOrmDao<T, PK> genericOrmDao;
	
	private Class<T> clazz;
	
	public LuceneOrmDao(Class<T> clazz){
		genericOrmDao = new GenericOrmDao<T, PK>(clazz);
		this.clazz = clazz;
	}
	
	/**
	 * 默认构造方法会自动加载事务线程
	 */
	public LuceneOrmDao(final String dataSourceName,Class<T> clazz){
		genericOrmDao = new GenericOrmDao<T, PK>(dataSourceName,clazz);
		this.clazz = clazz;
	}
	
	public void setValidation(boolean isValidation) {
		genericOrmDao.setValidation(isValidation);
	}
	
	public Number insert(T bean) throws Exception {
		Number value = genericOrmDao.insert(bean);
		if(value != null){
			BeanUtils.setFieldValue(bean, EntitySqlBuilder.getPkName(clazz), value);
		}
		SearchIndexCreator.createIndex(bean);
		return value;
	}
	
	@SuppressWarnings("unchecked")
	public void update(T bean) throws Exception {
		T oBean = genericOrmDao.select((PK)BeanUtils.getFieldValue(bean, EntitySqlBuilder.getPkName(clazz)));
		genericOrmDao.update(bean);
		SearchIndexCreator.removeIndex(oBean);
		SearchIndexCreator.createIndex(bean);
	}
	
	public void delete(PK pk) throws Exception {
		T oBean = genericOrmDao.select(pk);
		SearchIndexCreator.removeIndex(oBean);
	}
	
	public Page select(Page page,String text){
		return page;
	}
	
}
