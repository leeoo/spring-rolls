package com.wisdom.example.web.example;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.wisdom.core.security.OnlineUserCache;
import com.wisdom.core.security.domain.User;
import com.wisdom.core.utils.DateUtils;
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

	private int diffDate(java.util.Date beginDate, java.util.Date endDate) {
    	 return (int) ((getMillis(beginDate) - getMillis(endDate)) / 1000);
    }
	
    private long getMillis(java.util.Date date) {
	    java.util.Calendar c = java.util.Calendar.getInstance();
	    c.setTime(date);
	    return c.getTimeInMillis();
    }
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav=new ModelAndView();
		Date currentDate = DateUtils.getCurrentDateTime();
		Collection<User> onlineUsers = new LinkedList<User>();
		Collection<User> users = OnlineUserCache.getOnlineUsers();
		for(User user:users){
			user.setEnabled(true);
			Date loginDate = OnlineUserCache.get(user.getUsername()).getLastLoginDate();
			int m = diffDate(currentDate,loginDate);
			m = m / 60;
			if(m<=1){
				m = 1;
			}
			int h = 0;
			if(m>=60){
				h = m/60;
				m = m%60;
			}
			user.setOperatorName(h+"小时"+m+"分钟");
			user.setLastLoginDate(loginDate);
			user.setOperatorIp( OnlineUserCache.get(user.getUsername()).getOperatorIp());
			onlineUsers.add(user);
		}
		mav.addObject("onlineUsers", onlineUsers);
		return mav;
	} 

	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView index(ModelAndView mav) {
		mav=new ModelAndView("redirect:/example/index.htm");
		SystemMemoryInterceptor.enabled=!SystemMemoryInterceptor.enabled;
		return mav;
	} 
}
