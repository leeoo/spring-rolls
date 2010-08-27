package com.wisdom.example.web.example.security;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wisdom.core.security.domain.Role;
import com.wisdom.core.utils.CollectionUtils;
import com.wisdom.core.utils.Page;
import com.wisdom.example.service.security.SecurityService;
/**
 * 功能描述：角色管理
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-26</b>
 * <br>创建时间：<b>下午02:05:42</b>
 * <br>文件结构：<b>spring:com.wisdom.example.web.example.security/RoleController.java</b>
 */
@Controller
@RequestMapping("/example/role")
public class RoleController {
	@Resource
	private SecurityService securityService;
	
	@RequestMapping("/list/{no}")
	public String list(@PathVariable int no,HttpServletRequest request,Page page){
		page.setPageSize(5);
		page.setPageNo(no);
		request.setAttribute("page",securityService.getAllRoles(page));
		return "/example/role/list";
	}
	
	@RequestMapping("/input/{id}")
	public String input(@PathVariable Long id,HttpServletRequest request) throws Exception{
		if(id!=null){
			Role role=securityService.getRole(id);
			role.setResources(securityService.getResourcesByRoleId(id));
			request.setAttribute("role",role);
		}
		request.setAttribute("resources", securityService.getAllResources());
		return "/example/role/input";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String save(Role role,Long[] resourceIds) throws Exception{
		role.setName(role.getName().toUpperCase());
		if(role.getId()!=null){
			securityService.updateRole(role,CollectionUtils.arrayToList(resourceIds));
		}else{
			securityService.saveRole(role,CollectionUtils.arrayToList(resourceIds));
		}
		return "redirect:/example/role/list/1.htm";
	}
	
	@RequestMapping("/delete/{no}-{id}")
	public String delete(@PathVariable int no,@PathVariable Long id) throws Exception{
		if(id!=null){
			securityService.deleteRole(id);
		}
		return "redirect:/example/role/list/"+no+".htm";
	}
	
	
}
