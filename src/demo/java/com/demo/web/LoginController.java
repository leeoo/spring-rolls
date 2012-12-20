package com.demo.web;

import java.util.Map;

import org.dily.mvc.ModelAndView;
import org.dily.mvc.annotation.Controller;
import org.dily.mvc.annotation.MappingMethod;

@Controller("/")
public class LoginController extends BaseController{

	@MappingMethod
	public ModelAndView login(ModelAndView mv){
		Map<String,Object> map = mv.getMap("login");
		String username = (String)map.get("username");
		String password = (String)map.get("password");
		System.out.println(username+"||"+password);
		return mv.redirect(getRedirect("/system/index"));
	}
	
	@MappingMethod
	public ModelAndView logout(ModelAndView mv){
		mv.getSession().invalidate();
		return mv.redirect("/index.jsp");
	}
	
}
