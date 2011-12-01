package com.demo.web.system;

import org.paramecium.ioc.IocContextIndex;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.demo.web.BaseController;

/**
 * 功 能 描 述:<br>
 * 
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-12-1下午03:26:11
 * <br>项 目 信 息:paramecium:com.demo.web.system.HelpController.java
 */
@Security
@ShowLabel("帮助信息")
@Controller("/system/help")
public class HelpController extends BaseController{
	
	@ShowLabel("首页界面")
	@MappingMethod
	public ModelAndView list(ModelAndView mv){
		mv.addValue("controllers", IocContextIndex.getControllers());
		mv.addValue("services", IocContextIndex.getServices());
		return mv.forward(getPage("/help/list.jsp"));
	}
	
}
