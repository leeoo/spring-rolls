package com.wisdom.core.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

import sun.net.util.IPAddressUtil;

import com.wisdom.core.security.resource.SecurityUtils;

/**
 * 功 能 描 述:<br>
 * ip地址控制
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2010-11-11上午11:16:10
 * <br>项 目 信 息:wisdom.3.0:com.wisdom.core.security.IpAddressVoter.java
 */
public class IpAddressVoter implements AccessDecisionVoter<Object>{

	private static Collection<String> ipAddressList = new ArrayList<String>();
	
	public static void put(String ip){
		ipAddressList.add(ip);
	}
	
	public static void remove(String ip){
		ipAddressList.remove(ip);
	}

	public static void removeAll(){
		ipAddressList.clear();
	}
	
	public boolean supports(ConfigAttribute configAttribute) {
		return true;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	private boolean enabled(){
		return !ipAddressList.isEmpty();
	}
	
	public Collection<String> getIpAddressList() {
		return ipAddressList;
	}

	public void setIpAddressList(Collection<String> ipAddressList) {
		IpAddressVoter.ipAddressList = ipAddressList;
	}

	public int vote(Authentication authentication, Object object,Collection<ConfigAttribute> configAttributes) {
		if(enabled()){
			String remoteIpAddress = SecurityUtils.getCurrentUserIp();
			if (remoteIpAddress != null && !remoteIpAddress.isEmpty()) {
				return vote(remoteIpAddress);
			}	
		}
		return ACCESS_DENIED;//弃权
	}
	
	private static int vote(String currentIp){
		if(IPAddressUtil.isIPv6LiteralAddress(currentIp)){
			return ACCESS_DENIED;//如果是IPV6则否定
		}
		for(String ip:ipAddressList){
			if(currentIp.equals(ip)){
				return ACCESS_GRANTED;
			}else if(ip.indexOf("-")>-1){
				try{
					String[] ipBlocks = ip.split("\\.");
					String[] currentIpBlocks = currentIp.split("\\.");
					int isMeet = 4;//是否符合IP段匹配
					for(int i =0;i<ipBlocks.length;i++){
						if(ipBlocks[i].indexOf("-")>-1){
							String[] ipNumbers = ipBlocks[i].split("\\-");
							int start = Integer.parseInt(ipNumbers[0]);
							int end = Integer.parseInt(ipNumbers[1]);
							int currentNumber = Integer.parseInt(currentIpBlocks[i]);
							if(currentNumber<=end||currentNumber>=start){
								isMeet = isMeet - 1;
							}
						}else{
							if(currentIpBlocks[i].equals(ipBlocks[i])){
								isMeet = isMeet - 1;
							}
						}
					}
					if(isMeet==0){
						return ACCESS_GRANTED;
					}
				}catch (Exception e) {
					return ACCESS_DENIED;//登录ip验证发生意外错误则否定
				}
			}
		}
		return ACCESS_DENIED;//登录ip验证发生意外错误则否定
	}
	
}
