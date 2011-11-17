package org.paramecium.security;

import java.util.Collection;
import java.util.HashSet;

import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;

import sun.net.util.IPAddressUtil;

/**
 * 功 能 描 述:<br>
 * ip地址控制
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-10-18上午08:48:22
 * <br>项 目 信 息:paramecium:org.paramecium.security.IpAddressVoter.java
 */
public class IpAddressVoter{

	private final static Log logger = LoggerFactory.getLogger();
	
	private static Collection<String> ipAddressList = new HashSet<String>();
	
	private static boolean include = true;
	
	public static void put(String ip){
		ipAddressList.add(ip);
	}
	
	public static void remove(String ip){
		ipAddressList.remove(ip);
	}

	public static void removeAll(){
		ipAddressList.clear();
	}
	
	public static Collection<String> getIpAddressList() {
		return ipAddressList;
	}

	public static void setIpAddressList(Collection<String> ipAddressList) {
		IpAddressVoter.ipAddressList = ipAddressList;
	}
	
	public static void setInclude(boolean include) {
		IpAddressVoter.include = include;
	}

	/**
	 * 对IPv4进行授权验证
	 * @param currentIp
	 * @return true为安全
	 */
	public static boolean voteIPV4(String currentIp){
		if(ipAddressList==null || ipAddressList.isEmpty()){
			return true;
		}
		if(IPAddressUtil.isIPv6LiteralAddress(currentIp)){
			return !include;//如果是IPV6
		}
		for(String ip:ipAddressList){
			if(currentIp.equals(ip)){
				return include;
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
						return include;
					}
				}catch (Throwable e) {
					logger.error(e);
					return !include;//登录ip验证发生意外错误则否定
				}
			}
		}
		return !include;//登录ip验证发生意外错误则否定
	}

}
