package com.demo.web;

import org.paramecium.commons.SecurityUitls;
import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.AuthorizationMenu;
import org.paramecium.security.SecurityConfig;
import org.paramecium.security.UserDetails;

import com.demo.entity.system.User;
import com.demo.service.system.UserService;

@Controller("/")
public class LoginController extends BaseController{

	@AutoInject
	private UserService userService;
	
	@MappingMethod
	public ModelAndView login(ModelAndView mv){
		String username = mv.getValue("username", String.class);
		String password = mv.getValue("password", String.class);
		if(password==null||username==null||username.isEmpty()||password.isEmpty()){
			return mv.redirect(SecurityConfig.loginExceptionPage);
		}
		UserDetails userDetails = new UserDetails();
		userDetails.setUsername(username);
		userDetails.setAddress(mv.getRequest().getRemoteAddr());
		if(username.equals("admin")&&password.equals("admin")){
			userDetails.setEnable(true);
			userDetails.setName("系统固化管理员");
			userDetails.setResources(AuthorizationMenu.getAllAuthorizationMenu());
		}else{
			User user = userService.getUser(username);
			if(user==null||!user.getPassword().equals(password)){
				return mv.redirect(SecurityConfig.loginExceptionPage);
			}
			userDetails.setEnable(user.isEnabled());
			userDetails.setName(user.getName());
			userDetails.setResources(userService.getUserAuth(username));
		}
		SecurityUitls.login(userDetails,mv.getRequest());
		return mv.redirect(getServletExt("/system/index"));
	}
	
	@MappingMethod
	public ModelAndView logout(ModelAndView mv){
		mv.getSession().invalidate();
		return mv.redirect("/index.jsp");
	}
	
}
