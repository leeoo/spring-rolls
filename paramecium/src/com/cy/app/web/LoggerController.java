package com.cy.app.web;

import java.sql.SQLException;

import org.cy.core.ioc.annotation.AutoInject;
import org.cy.core.jdbc.dialect.Page;
import org.cy.core.mvc.ModelAndView;
import org.cy.core.mvc.annotation.Controller;
import org.cy.core.mvc.annotation.MappingMethod;

import com.cy.app.entity.Logger;
import com.cy.app.entity.LoggerWhere;
import com.cy.app.service.LoggerService;

@Controller(namespace="/logger")
public class LoggerController {
	
	@AutoInject
	private LoggerService loggerService;
	
	@MappingMethod(url="/list")
	public void index(ModelAndView mv){
		Page page = (Page) mv.getBean("page",Page.class);
		LoggerWhere loggerWhere = (LoggerWhere) mv.getBean("logger",LoggerWhere.class);
		if(loggerWhere!=null&&loggerWhere.getInfo()!=null&&!loggerWhere.getInfo().isEmpty()){
			loggerWhere.setInfo("%"+loggerWhere.getInfo()+"%");
		}
		page.setPageSize(5);
		page = loggerService.getAll(page,loggerWhere);
		mv.addValue("logger", loggerWhere);
		mv.addValue("page", page);
		mv.forward("/WEB-INF/logger/list.jsp");
	}
	
	@MappingMethod
	public void input(ModelAndView mv){
		Integer id = (Integer) mv.getValue("id",Integer.class);
		if(id!=null){
			Logger logger = loggerService.get(id);
			mv.addValue("logger", logger);
		}
		mv.forward("/WEB-INF/logger/input.jsp");
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
			mv.forward("/WEB-INF/logger/input.jsp");
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
