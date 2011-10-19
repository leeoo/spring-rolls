package com.demo.service;

import org.paramecium.security.IpAddressVoter;

/**
 * 功 能 描 述:<br>
 * 优先启动
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-10-18下午02:23:20
 * <br>项 目 信 息:paramecium:com.demo.service.PriorityService.java
 */
public class PriorityService {
	
	public void init(){
		IpAddressVoter.setInclude(true);
		IpAddressVoter.put("127.0.0.1");
		System.out.println("应用层初始化类已经执行...");
	}
	
}
