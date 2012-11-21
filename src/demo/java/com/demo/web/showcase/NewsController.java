package com.demo.web.showcase;

import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.demo.entity.showcase.News;
import com.demo.service.showcase.NewsService;

@Security
@ShowLabel("演示-新闻")
@Controller("/showcase/news")
public class NewsController extends ShowCaseBaseController {
	
	@AutoInject
	private NewsService newsService;
	
	@ShowLabel("首页界面")
	@MappingMethod
	public void list(ModelAndView mv){
		mv.forward(getPage("/news/list.jsp"));
	}
	
	
	@ShowLabel("获取列表数据")
	@MappingMethod
	public void data(ModelAndView mv){
		int pageNo = mv.getValue("page", int.class);
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(20);
		page = newsService.getAll(page);
		String json = getJsonPageData(page);
		mv.printJSON(json);
	}
	
	@ShowLabel("新增及维护界面")
	@MappingMethod
	public ModelAndView input(ModelAndView mv){
		Integer id = mv.getValue("id",Integer.class);
		if(id!=null){
			News news = newsService.get(id);
			mv.addValue("news", news);
		}
		return mv.forward(getPage("/news/input.jsp"));
	}
	
	@ShowLabel("保存")
	@MappingMethod
	public ModelAndView save(ModelAndView mv){
		News news = mv.getBean("news",News.class);
		try {
			if(news.getId()==null){
				newsService.save(news);
			}else{
				newsService.update(news);
			}
			mv.setSuccessMessage("操作成功!");
		} catch (Exception e) {
			mv.addValue("news", news);
			mv.setErrorMessage(e.getMessage());
			return mv.forward(getPage("/news/input.jsp"));
		}
		return mv.redirect(getRedirect("/showcase/news/list"));
	}
	
	@ShowLabel("删除")
	@MappingMethod
	public void delete(ModelAndView mv){
		String idstr = mv.getValue("ids",String.class);
		try {
			if(idstr!=null){
				String[] ids = idstr.split(",");
				newsService.delete(ids);
			}
		} catch (Exception e) {
		}
	}
	
}
