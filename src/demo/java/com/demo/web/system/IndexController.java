package com.demo.web.system;

import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;

@Controller(namespace="/system")
public class IndexController {

	@MappingMethod
	public void index(ModelAndView mv){
		mv.forward("/WEB-INF/demo/system/index.jsp");
	}
	
}
