package com.wisdom.test.simple;

import java.util.Collection;
import java.util.Date;

import com.wisdom.core.test.SpringTestCase;
import com.wisdom.core.utils.DateUtils;
import com.wisdom.example.entity.search.News;
import com.wisdom.example.service.search.NewsService;

public class SimpleDaoTestCase extends SpringTestCase{
	@javax.annotation.Resource
	private NewsService newsService;
	
	public void testSave()throws Exception{
		News news=new News();
		news.setAuth("曹阳");
		news.setContext("哈哈哈，我是快乐的小玩家，真的很快乐。");
		news.setTitle("第一个测试");
		news.setEnabled(true);
		Date date=DateUtils.getCurrentDateTime();
		Date lastDate=DateUtils.addDays(date, 7);
		news.setDurability(lastDate);
		news.setPublishDate(date);
		newsService.saveNews(news);
	}
	
	public void testQueryText(){
		Collection<News> news=newsService.searchNews("曹阳");
		System.out.println(news.size());
		News news1=news.iterator().next();
		System.out.println(news1.getAuth()+news1.getTitle()+news1.getContext());
	}
}
