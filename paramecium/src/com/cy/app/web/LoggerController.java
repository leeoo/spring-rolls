package com.cy.app.web;

import org.cy.core.ioc.annotation.AutoInject;
import org.cy.core.mvc.ModelAndView;
import org.cy.core.mvc.annotation.Controller;
import org.cy.core.mvc.annotation.MappingMethod;

import com.cy.app.service.LoggerService;

@Controller(namespace="/logger")
public class LoggerController {
	
	@AutoInject
	private LoggerService loggerService;
	
	@MappingMethod
	public void index(ModelAndView mv){
		mv.forward("/test.jsp");
	}
	
	@MappingMethod
	public void save(ModelAndView mv){
		mv.redirect("/test.jsp");
	}
	
}
