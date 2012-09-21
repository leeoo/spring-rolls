package com.exam.service.system;

import org.paramecium.ioc.annotation.Service;
import org.paramecium.security.IpAddressVoter;

/**
 * 功 能 描 述:<br>
 * IP地址过滤初始化
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2012-3-20下午02:38:46
 * <br>项 目 信 息:paramecium:com.demo.service.system.IpInitService.java
 */
@Service
public class IpInitService {
	
	public void init(){
		IpAddressVoter.setEnabled(false);//不要启动IP过滤
	}
	
}
