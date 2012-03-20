package com.demo.web.system;

import org.paramecium.ioc.IocContextIndex;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.demo.web.BaseController;

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
