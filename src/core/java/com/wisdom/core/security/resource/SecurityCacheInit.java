package com.wisdom.core.security.resource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wisdom.core.security.domain.Resource;
import com.wisdom.core.security.service.UserService;

/**
 * 功能描述：
 * 初始化权限缓存
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-25</b>
 * <br>创建时间：<b>上午11:53:09</b>
 * <br>文件结构：<b>spring:com.wisdom.core.security.resource/SecurityCacheInit.java</b>
 */
public class SecurityCacheInit {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private UserService userService;
	
	public void init(){
		logger.info("正在初始化权限缓存");
		List<Resource> res=userService.getAllResources();
		for(Resource r:res){
			SecurityResourceCache.putCache(r);
		}
		logger.info("权限缓存初始化结束");
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
