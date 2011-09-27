package com.demo.web.system;

import org.paramecium.mvc.End;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.SecurityThread;

import com.demo.web.BaseController;

@Controller(namespace="/system")
public class IndexController extends BaseController{

	@MappingMethod
	public End index(ModelAndView mv){
		mv.addValue("loginName", SecurityThread.get().getName());
		return mv.forward(getPage("/index.jsp"));
	}
	
}
