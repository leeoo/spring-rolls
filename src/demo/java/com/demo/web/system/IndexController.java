package com.demo.web.system;

import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.SecurityThread;

@Controller(namespace="/system")
public class IndexController {

	@MappingMethod
	public void index(ModelAndView mv){
		mv.addValue("loginName", SecurityThread.get().getName());
		mv.forward("/WEB-INF/demo/system/index.jsp");
	}
	
}
