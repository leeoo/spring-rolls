package com.wisdom.example.web.example;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.wisdom.core.security.domain.User;
import com.wisdom.core.security.resource.SecurityUtils;
import com.wisdom.core.web.SystemMemoryInterceptor;
/**
 * 功能描述：演示首页
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-26</b>
 * <br>创建时间：<b>下午02:04:44</b>
 * <br>文件结构：<b>spring:com.wisdom.example.web.example/IndexController.java</b>
 */
@Controller
@RequestMapping("/example/index.htm")
public class IndexController {

	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav=new ModelAndView();
		User user = SecurityUtils.getCurrentUser();
		mav.addObject("username", user.getCnname());
		return mav;
	} 

	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView index(ModelAndView mav) {
		mav=new ModelAndView("redirect:/example/index.htm");
		SystemMemoryInterceptor.enabled=!SystemMemoryInterceptor.enabled;
		return mav;
	} 
}
