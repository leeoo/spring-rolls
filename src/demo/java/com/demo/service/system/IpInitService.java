package com.demo.service.system;

import org.paramecium.ioc.annotation.Service;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.security.IpAddressVoter;

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
