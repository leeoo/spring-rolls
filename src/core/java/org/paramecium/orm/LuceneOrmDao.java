package org.paramecium.orm;

import java.io.Serializable;

import org.paramecium.cache.Cache;
import org.paramecium.cache.CacheManager;
import org.paramecium.commons.BeanUtils;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.search.SearchIndexCreator;

@SuppressWarnings("unchecked")
public class LuceneOrmDao <T , PK extends Serializable> {
	
	private final static Cache<String,Serializable[]> cache = CacheManager.getDefaultCache("CACHE_INDEX_PK");
	
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
		String sql = EntitySqlBuilder.getSelectSqlByPk(clazz);
		int last = sql.toLowerCase().lastIndexOf("where");
		sql = sql.substring(0, last);
		PK[] pks = (PK[]) cache.get(clazz.getName()+"#"+text);
		if(pks == null){
			
		}
		return page;
	}
	
}
