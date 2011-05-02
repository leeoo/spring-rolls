package com.demo.web;

import java.sql.SQLException;

import org.paramecium.commons.JsonUitls;
import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;

import com.demo.entity.Logger;
import com.demo.service.LoggerService;

@Controller(namespace="/logger")
public class LoggerController {
	
	@AutoInject
	private LoggerService loggerService;
	
	@MappingMethod(url="/list")
	public void index(ModelAndView mv){
		/*Page page = (Page) mv.getBean("page",Page.class);
		LoggerWhere loggerWhere = (LoggerWhere) mv.getBean("logger",LoggerWhere.class);
		String old = loggerWhere.getInfo();
		if(loggerWhere.getInfo()!=null&&!loggerWhere.getInfo().isEmpty()){
			loggerWhere.setInfo("%"+old+"%");
		}
		page.setPageSize(5);
		page = loggerService.getAll(page,loggerWhere);
		loggerWhere.setInfo(old);
		mv.addValue("logger", loggerWhere);
		mv.addValue("page", page);*/
		mv.forward("/WEB-INF/demo/logger/list.jsp");
	}
	
	@MappingMethod
	public void data(ModelAndView mv){
		int pageNo = (Integer) mv.getValue("page", Integer.class);
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(20);
		page = loggerService.getAll(page);
		String json = JsonUitls.getBeansJson(page.getResult(),false);
		json = ("{\"total\":\""+page.getTotalCount()+"\",\"rows\":["+json+"]}");
		mv.printJSON(json);
	}
	
	@MappingMethod
	public void input(ModelAndView mv){
		Integer id = (Integer) mv.getValue("id",Integer.class);
		if(id!=null){
			Logger logger = loggerService.get(id);
			mv.addValue("logger", logger);
		}
		mv.forward("/WEB-INF/demo/logger/input.jsp");
	}
	
	@MappingMethod
	public void save(ModelAndView mv){
		Logger logger = (Logger) mv.getBean("logger",Logger.class);
		try {
			if(logger.getId()==null){
				loggerService.save(logger);
			}else{
				loggerService.update(logger);
			}
		} catch (SQLException e) {
			mv.addValue("logger", logger);
			mv.forward("/WEB-INF/demo/logger/input.jsp");
			return;
		}
		mv.redirect("/logger/list.jhtml");
	}
	
	@MappingMethod
	public void delete(ModelAndView mv){
		Integer id = (Integer) mv.getValue("id",Integer.class);
		Page page = (Page) mv.getBean("page",Page.class);
		try {
			if(id!=null){
				loggerService.delete(id);
			}
		} catch (SQLException e) {
		}
		mv.redirect("/logger/list.jhtml?page.pageNo="+page.getPageNo());
	}
	
}
