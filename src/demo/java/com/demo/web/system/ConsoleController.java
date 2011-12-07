package com.demo.web.system;

import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.demo.web.BaseController;

@Security
@ShowLabel("控制台")
@Controller("/system/console")
public class ConsoleController extends BaseController{

	@ShowLabel("首页界面")
	@MappingMethod
	public ModelAndView index(ModelAndView mv){
		return mv.forward(getPage("/console/index.jsp"));
	}
	
}
