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
public class IpAddressVoter implements AccessDecisionVoter{

	private static Collection<String> ipAddressList = new ArrayList<String>();
	
	private static boolean ipAddressExclude = false;//是否按照排除方法
	
	public static void put(String ip){
		ipAddressList.add(ip);
	}
	
	public static void remove(String ip){
		ipAddressList.remove(ip);
	}

	public static void removeAll(){
		ipAddressList.clear();
	}
	
	public boolean isIpAddressExclude() {
		return ipAddressExclude;
	}

	public void setIpAddressExclude(boolean ipAddressExclude) {
		IpAddressVoter.ipAddressExclude = ipAddressExclude;
	}

	@Override
	public boolean supports(ConfigAttribute configAttribute) {
		return true;
	}

	@Override
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

	@Override
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
			return ACCESS_DENIED;//如果是IPV6则弃权
		}
		for(String ip:ipAddressList){
			if(currentIp.equals(ip)){
				return ipAddressExclude?ACCESS_DENIED:ACCESS_GRANTED;
			}else if(ip.indexOf("-")>-1){
				try{
					String[] ipBlocks = ip.split("\\.");
					String[] currentIpBlocks = currentIp.split("\\.");
					boolean isMeet = true;//是否符合IP段匹配
					for(int i =0;i<ipBlocks.length;i++){
						if(ipBlocks[i].indexOf("-")>-1){
							String[] ipNumbers = ipBlocks[i].split("\\-");
							int start = Integer.parseInt(ipNumbers[0]);
							int end = Integer.parseInt(ipNumbers[1]);
							int currentNumber = Integer.parseInt(currentIpBlocks[i]);
							if(currentNumber>end||currentNumber<start){
								isMeet = false;
								break;
							}
						}else{
							if(!currentIpBlocks[i].equals(ipBlocks[i])){
								isMeet = false;
								break;
							}
						}
					}
					if(ipAddressExclude){//true为只拦截IP列表中的IP地址;false为拦截IP列表之外的IP地址
						return isMeet?ACCESS_DENIED:ACCESS_GRANTED;
					}else{
						return isMeet?ACCESS_GRANTED:ACCESS_DENIED;
					}
				}catch (Exception e) {
					return ACCESS_DENIED;//登录ip验证发生意外错误则弃权
				}
			}
		}
		return ACCESS_DENIED;//登录ip验证发生意外错误则弃权
	}
	
}
