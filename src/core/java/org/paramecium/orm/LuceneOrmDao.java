package org.paramecium.orm;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.paramecium.cache.Cache;
import org.paramecium.cache.CacheManager;
import org.paramecium.commons.BeanUtils;
import org.paramecium.jdbc.dialect.BaseDialect;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.search.SearchIndexCreator;
/**
 * 功 能 描 述:<br>
 * 基于Lucene的数据库操作映射DAO，目的在于操作数据库的同时，为大文本字段建立索引，便于检索。
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-10-14下午03:08:49
 * <br>项 目 信 息:paramecium:org.paramecium.orm.LuceneOrmDao.java
 */
@SuppressWarnings("unchecked")
public class LuceneOrmDao <T , PK extends Serializable> {
	
	private final static Log logger = LoggerFactory.getLogger();
	
	private final static Cache cache = CacheManager.getDefaultCache("LUCENE_INDEX_PK",100);
	
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
	
	/**
	 * 新增操作，并创建索引
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public Number insert(T bean) throws Exception {
		Number value = genericOrmDao.insert(bean);
		if(value != null){
			BeanUtils.setFieldValue(bean, EntitySqlParser.getPkName(clazz), value);
		}
		SearchIndexCreator.createIndex(bean);
		cache.clear();
		return value;
	}
	
	/**
	 * 更新某项，且更新对应索引
	 * @param bean
	 * @throws Exception
	 */
	public void update(T bean) throws Exception {
		T oBean = genericOrmDao.select((PK)BeanUtils.getFieldValue(bean, EntitySqlParser.getPkName(clazz)));
		genericOrmDao.update(bean);
		SearchIndexCreator.removeIndex(oBean);
		SearchIndexCreator.createIndex(bean);
		cache.clear();
	}
	
	/**
	 * 删除某项，且删除对应索引
	 * @param pk
	 * @throws Exception
	 */
	public void delete(PK pk) throws Exception {
		T oBean = genericOrmDao.select(pk);
		SearchIndexCreator.removeIndex(oBean);
		cache.clear();
	}
	
	/**
	 * 获得某信息详情
	 * @param pk
	 * @return
	 */
	public T select(PK pk){
		return genericOrmDao.select(pk);
	}
	
	/**
	 * 分页
	 * @param page
	 * @return
	 */
	public Page select(Page page){
		return genericOrmDao.select(page);
	}
	
	/**
	 * 通过关键字搜索，如果关键字为空，返回第一页，且为空。
	 * @param page
	 * @param text
	 * @return
	 */
	public Page select(Page page,String text){
		if(text == null || text.isEmpty()){
			page.setResult(null);
			page.setPageNo(1);
			return page;
		}
		String sql = EntitySqlParser.getSelectSqlByPk(clazz);
		int last = sql.lastIndexOf("=");
		int where = sql.toLowerCase().lastIndexOf("where");
		String countSql = BaseDialect.getCountSql(sql.substring(0, where));
		sql = sql.substring(0, last).concat(" IN( ");
		long count = 0;
		if (page.isAutoCount()) {
			count = (Long)genericOrmDao.getGenericJdbcDao().queryUniqueColumnValueByArray(countSql);
			page.setTotalCount((int)count);
		}
		List<PK> pks = getPKList(text,(int)count);
		Object[] arrayParams = new Serializable[page.getPageSize()];
		if (pks!=null && !pks.isEmpty() && page.isFirstSetted() && page.isPageSizeSetted()) {
			try{
				for(int i = 0;i<page.getPageSize();i++){
					sql = sql.concat("?,");
					arrayParams[i] = pks.get(page.getFirst()+i);
				}
			}catch (Throwable e) {
				logger.warn(e);
				page.setResult(null);
				return page;
			}
		}else{
			page.setResult(null);
			return page;
		}
		sql = sql.substring(0, sql.length()-1).concat(" )");
		Collection<?> list = genericOrmDao.getGenericJdbcDao().queryByArray(sql, clazz, arrayParams);
		page.setResult(list);
		return page;
	}
	
	/**
	 * 从缓存中获得关键字搜索的主键或唯一键，如果分页较多，缓存作用较大，如果只是本页
	 * @param text
	 * @param count
	 * @return
	 */
	private List<PK> getPKList(String text,int count){
		List<PK> pks = (List<PK>)cache.get(clazz.getName()+"#"+text);
		if(pks == null){
			Collection<String> keywords = SearchIndexCreator.searchKeyword(clazz, text,count);
			List<Serializable> list = new LinkedList<Serializable>();
			Class type = EntitySqlParser.getPkType(clazz);
			for(String keyword : keywords){
				if (String.class.equals(type)){
					list.add(keyword);
				}else if (int.class.equals(type) || Integer.class.equals(type)) {
					list.add(Integer.parseInt(keyword));
				}else if (long.class.equals(type) || Long.class.equals(type)) {
					list.add(Long.parseLong(keyword));
				}
			}
			cache.put(clazz.getName()+"#"+text, list);
			pks = (List<PK>)cache.get(clazz.getName()+"#"+text);
		}
		return pks;
	}
	
}
