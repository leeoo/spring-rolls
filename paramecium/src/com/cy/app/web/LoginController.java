package com.cy.app.web;

import org.cy.core.mvc.ModelAndView;
import org.cy.core.mvc.annotation.Controller;
import org.cy.core.mvc.annotation.MappingMethod;
import org.cy.core.security.AuthorizationMenu;
import org.cy.core.security.SecurityThread;
import org.cy.core.security.UserDetails;

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
		}else{
			user.setEnable(false);
		}
		SecurityThread.put(user);
		mv.redirect("/logger/list.jhtml");
	}
	
	@MappingMethod
	public void logout(ModelAndView mv){
		SecurityThread.remove();
		mv.redirect("/index.jsp");
	}
	
}
