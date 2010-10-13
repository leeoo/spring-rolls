package com.wisdom.example.web.example.security;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wisdom.core.security.domain.User;
import com.wisdom.core.utils.CollectionUtils;
import com.wisdom.core.utils.Page;
import com.wisdom.example.commons.ValidationUtils;
import com.wisdom.example.service.security.SecurityService;
/**
 * 功能描述：账户管理
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-26</b>
 * <br>创建时间：<b>下午02:05:25</b>
 * <br>文件结构：<b>spring:com.wisdom.example.web.example.security/UserController.java</b>
 */
@Controller
@RequestMapping("/example/user")
public class UserController {
	@Resource
	private SecurityService securityService;
	
	@RequestMapping("/list/{no}")
	public String list(@PathVariable int no,HttpServletRequest request,Page page){
		page.setPageSize(5);
		page.setPageNo(no);
		request.setAttribute("page",securityService.getAllUsers(page));
		return "/example/user/list";
	}
	
	@RequestMapping("/input/{id}")
	public String input(@PathVariable Long id,HttpServletRequest request) throws Exception{
		if(id!=null){
			User user=securityService.getUser(id);
			user.setRoles(securityService.getRolesByUserId(id));
			request.setAttribute("user",user);
		}
		request.setAttribute("roles", securityService.getAllRoles());
		return "/example/user/input";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String save(User user,Long[] roleIds,HttpServletRequest request) throws Exception{
		List<String> errors=ValidationUtils.validator(user);
		if(errors!=null){
			request.setAttribute("errors", errors);
			return "/errors/error";
		}
		if(user.getId()!=null){
			securityService.updateUser(user,CollectionUtils.arrayToList(roleIds));
		}else{
			securityService.saveUser(user,CollectionUtils.arrayToList(roleIds));
		}
		return "redirect:/example/user/list/1.htm";
	}
	
	@RequestMapping("/delete/{no}-{id}")
	public String delete(@PathVariable int no,@PathVariable Long id) throws Exception{
		if(id!=null){
			securityService.deleteUser(id);
		}
		return "redirect:/example/user/list/"+no+".htm";
	}
	
	@RequestMapping("/disabled/{no}-{id}")
	public String disabled(@PathVariable int no,@PathVariable Long id) throws Exception{
		if(id!=null){
			securityService.updateUserDisabled(id);
		}
		return "redirect:/example/user/list/"+no+".htm";
	}

	@RequestMapping("/enabled/{no}-{id}")
	public String enabled(@PathVariable int no,@PathVariable Long id) throws Exception{
		if(id!=null){
			securityService.updateUserEnabled(id);
		}
		return "redirect:/example/user/list/"+no+".htm";
	}
	
	@RequestMapping("/{id}/change_password")
	public String changePassword(@PathVariable Long id,HttpServletRequest request) throws Exception{
		if(id!=null){
			User user=securityService.getUser(id);
			request.setAttribute("user",user);
		}
		return "/example/user/changepwd";
	}

	@RequestMapping(value="/change_password",method=RequestMethod.POST)
	public String changePassword(User user) throws Exception{
		if(user!=null&&user.getPassword()!=null&&user.getId()!=null){
			securityService.updateUserPassword(user);
		}
		return "redirect:/example/user/input/"+user.getId()+".htm";
	}

}
