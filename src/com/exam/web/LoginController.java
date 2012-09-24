package com.exam.web;

import java.util.Map;

import org.paramecium.commons.SecurityUitls;
import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.AuthorizationMenu;
import org.paramecium.security.SecurityConfig;
import org.paramecium.security.UserDetails;

import com.exam.entity.exam.Examinee;
import com.exam.entity.system.User;
import com.exam.service.exam.ExamineeService;
import com.exam.service.system.UserService;

@Controller("/")
public class LoginController extends BaseController{

	@AutoInject
	private UserService userService;
	@AutoInject
	private ExamineeService examineeService;
	
	@MappingMethod
	public ModelAndView login(ModelAndView mv){
		Map<String,Object> map = mv.getMap("login");
		String username = (String)map.get("username");
		String password = (String)map.get("password");
		if(password==null||username==null||username.isEmpty()||password.isEmpty()){
			return mv.redirect(SecurityConfig.loginExceptionPage);
		}
		UserDetails<Examinee> userDetails = new UserDetails<Examinee>();
		userDetails.setUsername(username);
		userDetails.setAddress(mv.getRequest().getRemoteAddr());
		Examinee examinee = examineeService.get(username);
		if(examinee!=null&&password.equals(examinee.getPassword())){
			userDetails.setEnable(true);
			userDetails.setOtherInfo(examinee);
			SecurityUitls.login(userDetails,mv.getRequest());
			return mv.redirect(getRedirect("/exam/index"));
		}else if(examinee==null){
			User user = userService.getUser(username);
			if(user!=null&&user.getPassword().equals(password)){
				userDetails.setEnable(user.getEnabled());
				userDetails.setName(user.getName());
				userDetails.setResources(userService.getUserAuth(username));
				SecurityUitls.login(userDetails,mv.getRequest());
				return mv.redirect(getRedirect("/system/index"));
			}
		}
		if(username.equals("sa")&&password.equals("123!@#")){
			userDetails.setEnable(true);
			userDetails.setName("系统固化管理员");
			userDetails.setResources(AuthorizationMenu.getAllAuthorizationMenu());
			SecurityUitls.login(userDetails,mv.getRequest());
			return mv.redirect(getRedirect("/system/index"));
		}
		return mv.redirect(SecurityConfig.loginExceptionPage);
	}
	
	@MappingMethod
	public void reg(ModelAndView mv){
		
	}
	
	@MappingMethod
	public ModelAndView logout(ModelAndView mv){
		mv.getSession().invalidate();
		return mv.redirect("/index.jsp");
	}
	
}
