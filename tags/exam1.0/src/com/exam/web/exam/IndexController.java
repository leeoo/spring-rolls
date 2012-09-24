package com.exam.web.exam;

import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.SecurityThread;
import org.paramecium.security.annotation.Security;

import com.exam.web.BaseController;

@Security
@ShowLabel("考生首页")
@Controller("/exam")
public class IndexController extends BaseController{

	@ShowLabel("登录成功后友好界面")
	@MappingMethod
	public ModelAndView index(ModelAndView mv){
		mv.addValue("loginName", SecurityThread.get().getName());
		return mv.forward(getExamPage("/index.jsp"));
	}
	
}
