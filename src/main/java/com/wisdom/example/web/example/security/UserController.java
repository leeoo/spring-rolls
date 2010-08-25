package com.wisdom.example.web.example.security;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wisdom.core.security.domain.User;
import com.wisdom.core.security.service.UserService;
import com.wisdom.core.utils.CollectionUtils;
import com.wisdom.core.utils.Page;
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
	private UserService userService;
	
	@RequestMapping("/list/{no}")
	public String list(@PathVariable int no,HttpServletRequest request,Page page){
		page.setPageSize(5);
		page.setPageNo(no);
		request.setAttribute("page",userService.getAllUsers(page));
		return "/example/user/list";
	}
	
	@RequestMapping("/input/{id}")
	public String input(@PathVariable Long id,HttpServletRequest request) throws Exception{
		if(id!=null){
			User user=userService.getUser(id);
			user.setRoles(userService.getRolesByUserId(id));
			request.setAttribute("user",user);
		}
		request.setAttribute("roles", userService.getAllRoles());
		return "/example/user/input";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String save(User user,Long[] roleIds) throws Exception{
		if(user.getId()!=null){
			userService.updateUser(user,CollectionUtils.arrayToList(roleIds));
		}else{
			userService.saveUser(user,CollectionUtils.arrayToList(roleIds));
		}
		return "redirect:/example/user/list/1.htm";
	}
	
	@RequestMapping("/delete/{no}-{id}")
	public String delete(@PathVariable int no,@PathVariable Long id) throws Exception{
		if(id!=null){
			userService.deleteUser(id);
		}
		return "redirect:/example/user/list/"+no+".htm";
	}
	
	@RequestMapping("/disabled/{no}-{id}")
	public String disabled(@PathVariable int no,@PathVariable Long id) throws Exception{
		if(id!=null){
			userService.updateUserDisabled(id);
		}
		return "redirect:/example/user/list/"+no+".htm";
	}

	@RequestMapping("/enabled/{no}-{id}")
	public String enabled(@PathVariable int no,@PathVariable Long id) throws Exception{
		if(id!=null){
			userService.updateUserEnabled(id);
		}
		return "redirect:/example/user/list/"+no+".htm";
	}
	
	@RequestMapping("/{id}/change_password")
	public String changePassword(@PathVariable Long id,HttpServletRequest request) throws Exception{
		if(id!=null){
			User user=userService.getUser(id);
			request.setAttribute("user",user);
		}
		return "/example/user/changepwd";
	}

	@RequestMapping(value="/change_password",method=RequestMethod.POST)
	public String changePassword(User user) throws Exception{
		if(user!=null&&user.getPassword()!=null&&user.getId()!=null){
			userService.updateUserPassword(user);
		}
		return "redirect:/example/user/input/"+user.getId()+".htm";
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
