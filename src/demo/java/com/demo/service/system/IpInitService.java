package com.demo.service.system;

import org.paramecium.ioc.annotation.Service;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
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
	
	private final static Log logger = LoggerFactory.getLogger();
	
	public void init(){
		IpAddressVoter.setInclude(true);
		IpAddressVoter.put("127.0.0.1");
		IpAddressVoter.put("192.168.71.0-255");
		logger.debug("IP地址安全过滤已启动");
	}

}
