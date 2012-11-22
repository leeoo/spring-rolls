package com.demo.web.showcase;

import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.demo.service.showcase.NewsService;

@Security
@ShowLabel("搜索引擎")
@Controller("/showcase/search")
public class SearchController extends ShowCaseBaseController{
	
	@AutoInject
	private NewsService newsService;
	
	@ShowLabel("首页界面")
	@MappingMethod
	public void list(ModelAndView mv){
		int pageNo = mv.getValue("pageNo", int.class);
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(20);
		String text = mv.getValue("text", String.class);
		page = newsService.getAll(page,text);
		mv.addValue("page", page);
		if(text!=null&&!text.trim().isEmpty()){
			mv.addValue("text", text);
		}
		mv.forward(getPage("/search/list.jsp"));
	}
	
}
