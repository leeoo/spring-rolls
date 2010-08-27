package com.wisdom.example.web.example.security;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wisdom.core.security.domain.Resource;
import com.wisdom.core.security.resource.SecurityResourceCache;
import com.wisdom.core.utils.Page;
import com.wisdom.example.service.security.SecurityService;
/**
 * 功能描述：资源权限管理
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-26</b>
 * <br>创建时间：<b>下午02:06:09</b>
 * <br>文件结构：<b>spring:com.wisdom.example.web.example.security/ResourceController.java</b>
 */
@Controller
@RequestMapping("/example/resource")
public class ResourceController {
	@javax.annotation.Resource
	private SecurityService securityService;
	
	@RequestMapping("/list/{no}")
	public String list(@PathVariable int no,HttpServletRequest request,Page page){
		page.setPageSize(5);
		page.setPageNo(no);
		request.setAttribute("page",securityService.getAllResources(page));
		return "/example/resource/list";
	}
	
	@RequestMapping("/input/{id}")
	public String input(@PathVariable Long id,HttpServletRequest request) throws Exception{
		if(id!=null){
			Resource resource=securityService.getResource(id);
			request.setAttribute("resource",resource);
		}
		return "/example/resource/input";
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String save(Resource resource,Long[] resourceIds) throws Exception{
		resource.setName(resource.getName().toUpperCase());
		if(resource.getId()!=null){
			securityService.updateResource(resource);
		}else{
			securityService.saveResource(resource);
		}
		return "redirect:/example/resource/list/1.htm";
	}
	
	@RequestMapping("/delete/{no}-{id}")
	public String delete(@PathVariable int no,@PathVariable Long id) throws Exception{
		if(id!=null){
			securityService.deleteResource(id);
		}
		return "redirect:/example/resource/list/"+no+".htm";
	}
	
	@RequestMapping("/{no}/system/reload")
	public String reload(@PathVariable int no) throws Exception{
		SecurityResourceCache.removeAllCache();
		Collection<Resource> res=securityService.getAllResources();
		for(Resource r:res){
			SecurityResourceCache.putCache(r);
		}
		return "redirect:/example/resource/list/"+no+".htm";
	}
	
}
