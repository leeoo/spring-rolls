package com.demo.web;

import java.util.Collection;
import java.util.HashSet;

import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.AuthorizationMenu;
import org.paramecium.security.Resource;
import org.paramecium.security.SecurityThread;
import org.paramecium.security.UserDetails;

@Controller(namespace="/")
public class LoginController {

	@MappingMethod
	public void login(ModelAndView mv){
		String username = (String) mv.getValue("username", String.class);
		UserDetails user = new UserDetails();
		user.setUsername(username);
		if(username.equals("admin")){
			user.setEnable(true);
			user.setResources(AuthorizationMenu.getAllAuthorizationMenu());
		}else if(username.equals("user")){
			user.setEnable(true);
			Collection<Resource> resources = new HashSet<Resource>();
			Resource resource = new Resource();
			resource.setFirstResource("loggerService");
			resource.setLastResource("getAll");
			resources.add(resource);
			user.setResources(resources);
		}else{
			user.setEnable(false);
		}
		SecurityThread.put(user,mv.getSession());
		mv.redirect("/logger/list.jhtml");
	}
	
	@MappingMethod
	public void logout(ModelAndView mv){
		SecurityThread.remove(mv.getSession());
		mv.redirect("/index.jsp");
	}
	
}
