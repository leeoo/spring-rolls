package com.cy.app.web;

import org.cy.core.ioc.annotation.AutoInject;
import org.cy.core.jdbc.dialect.Page;
import org.cy.core.mvc.ModelAndView;
import org.cy.core.mvc.annotation.Controller;
import org.cy.core.mvc.annotation.MappingMethod;

import com.cy.app.service.LoggerService;

@Controller(namespace="/logger")
public class LoggerController {
	
	@AutoInject
	private LoggerService loggerService;
	
	@MappingMethod(url="/list")
	public void index(ModelAndView mv){
		Page page = new Page(5);
		page = loggerService.getAll(page);
		mv.addValue("page", page);
		mv.forward("/WEB-INF/logger/list.jsp");
	}
	
	@MappingMethod
	public void save(ModelAndView mv){
		mv.redirect("/WEB-INF/logger/list.jsp");
	}
	
	@MappingMethod
	public void delete(ModelAndView mv){
		mv.redirect("/WEB-INF/logger/list.jsp");
	}
	
}
