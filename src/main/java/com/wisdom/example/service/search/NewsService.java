package com.wisdom.example.service.search;

import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisdom.core.orm.SimpleOrmGenericDao;
import com.wisdom.core.utils.Page;
import com.wisdom.example.entity.search.News;
/**
 * 功能描述：搜索引擎展示用的新闻业务类
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-11-7</b>
 * <br>创建时间：<b>下午04:13:57</b>
 * <br>文件结构：<b>spring:com.wisdom.example.service.search.NewsService.java</b>
 */
@Service
@Transactional
public class NewsService{
	
	private SimpleOrmGenericDao<News, Long> ormDao;
	
	@javax.annotation.Resource
	public void setDataSource(DataSource dataSource) {
		ormDao = new SimpleOrmGenericDao<News, Long>(dataSource,News.class);
	}
	
	public void saveNews(News news) throws Exception{
		ormDao._save(news);
	}
	
	public void updateNews(News news)throws Exception{
		ormDao.update(news);
	}
	
	public void deleteNews(Long id) throws Exception{
		ormDao.delete(id);
	}
	
	@Transactional(readOnly=true)
	public News getNewsById(Long id){
		return ormDao.get(id);
	}
	
	@Transactional(readOnly=true)
	public Page getNews(Page page){
		return ormDao.getAll(page);
	}
	
	@Transactional(readOnly=true)
	public Page getNews(Page page,News news){
		return ormDao.getAllByFilter(page,news);
	}
	
	@Transactional(readOnly=true)
	public Collection<News> searchNews(String queryText){
		return ormDao.getAllByText(queryText);
	}
	
}
