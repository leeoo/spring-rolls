package com.demo.service.showcase;

import org.paramecium.ioc.annotation.Service;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.orm.LuceneOrmDao;
import org.paramecium.transaction.annotation.Transactional;

import com.demo.entity.showcase.News;

/**
 * 功 能 描 述:<br>
 * 展示搜索带搜索引擎功能的ORM
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2012-3-20下午02:40:09
 * <br>项 目 信 息:paramecium:com.demo.service.system.RoleService.java
 */
@Service
@Transactional
public class NewsService {

	private LuceneOrmDao<News, Integer> orm = new LuceneOrmDao<News, Integer>(News.class);
	
	public NewsService(){
		orm.setValidation(true);
	}
	
	public void save(News news) throws Exception{
		orm.insert(news);
	}
	
	public void update(News news) throws Exception{
		orm.update(news);
	}
	
	public void delete(String... ids) throws Exception{
		for(String id : ids){
			orm.delete(Integer.parseInt(id));
		}
	}
	
	public News get(Integer id){
		return orm.select(id);
	}
	
	public Page getAll(Page page){
		return orm.select(page);
	}
	
	public Page getAll(Page page,String text){
		return orm.select(page,text);
	}
	
}
