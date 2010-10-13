package com.wisdom.example.web.example.search;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.wisdom.core.utils.Page;
import com.wisdom.example.commons.ValidationUtils;
import com.wisdom.example.entity.search.News;
import com.wisdom.example.service.search.NewsService;

/**
 * 功能描述：搜索引擎用新闻模块
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-11-7</b>
 * <br>创建时间：<b>下午04:32:09</b>
 * <br>文件结构：<b>spring:com.wisdom.example.web.example.search/NewsController.java</b>
 */
@Controller
@RequestMapping("/example/search")
public class NewsController {
	
	@Resource
	private NewsService newsService;
	
	@RequestMapping("/list/{no}")
	public ModelAndView list(News news,@PathVariable int no,Page page){
		ModelAndView mav=new ModelAndView("/example/search/list");
		page.setPageSize(5);
		page.setPageNo(no);
		page=newsService.getNews(page,news);
		mav.addObject("page", page);
		mav.addObject("news", news);
		return mav;
	}
	
	@RequestMapping("/input/{id}")
	public ModelAndView input(@PathVariable Long id) throws Exception{
		ModelAndView mav=new ModelAndView("/example/search/input");
		if(id!=null){
			News news=newsService.getNewsById(id);
			mav.addObject("news", news);
		}
		return mav;
	}
	
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public ModelAndView save(@ModelAttribute(value="news")News news) throws Exception{
		ModelAndView mav=new ModelAndView();
		List<String> errors=ValidationUtils.validator(news);
		if(errors!=null){
			mav.addObject("errors",errors);
			mav.setViewName("/errors/error");
			return mav;
		}
		try{
			if(news.getId()!=null){
				newsService.updateNews(news);
			}else{
				newsService.saveNews(news);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		mav.setViewName("redirect:/example/search/list/1.htm");
		return mav;
	}
	
	@RequestMapping("/delete/{no}-{id}")
	public ModelAndView delete(@PathVariable int no,@PathVariable Long id) throws Exception{
		ModelAndView mav=new ModelAndView("redirect:/example/search/list/"+no+".htm");
		if(id!=null){
			newsService.deleteNews(id);
		}
		return mav;
	}

	@RequestMapping("/disabled/{no}-{id}")
	public ModelAndView disabled(@PathVariable int no,@PathVariable Long id) throws Exception{
		ModelAndView mav=new ModelAndView("redirect:/example/search/list/"+no+".htm");
		if(id!=null){
			News news=newsService.getNewsById(id);
			news.setEnabled(false);
			newsService.updateNews(news);
		}
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView search(String text) throws Exception{
		ModelAndView mav=new ModelAndView("/example/search/search");
		Collection<News> newses=newsService.searchNews(text);
		mav.addObject("newses", newses);
		mav.addObject("text", text);
		return mav;
	}

}
