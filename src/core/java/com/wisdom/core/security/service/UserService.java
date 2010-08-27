package com.wisdom.core.security.service;

import java.util.Collection;

import com.wisdom.core.security.domain.Resource;
import com.wisdom.core.security.domain.User;


/**
 * 功能描述：
 * 用户登录安全接口
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-8</b>
 * <br>创建时间：<b>下午07:16:10</b>
 * <br>文件结构：<b>spring:com.wisdom.core.security.service/UserService.java</b>
 */
public interface UserService{
	
	public User getUserByLoginName(String loginName);
	
	public Collection<Resource> getResourcesByUserName(String loginName);
	
	public Collection<Resource> getAllResources();
	
}
