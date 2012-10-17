package com.exam.commons;

import org.paramecium.commons.EncodeUtils;

public class EncodeChanger {
	
	public static String getPassword(){
		long longTime = EncodeUtils.millisTime();
		int intTime = (int)(longTime/(1000*60*60*24));
		System.out.println(intTime);
		return EncodeUtils.encryptMD5(intTime+"").substring(5, 10).toUpperCase();
	}
	
	public static void main(String[] args) {
		System.out.println(getPassword());
	}
	
	public static String unicode2UnicodeEsc(String uniStr) {
		StringBuffer ret = new StringBuffer();
		if (uniStr == null) {
			return null;
		}
		int maxLoop = uniStr.length();
		for (int i = 0; i < maxLoop; i++) {
			char character = uniStr.charAt(i);
			ret.append("\\u");
			String hexStr = Integer.toHexString(character);
			int zeroCount = 4 - hexStr.length();
			for (int j = 0; j < zeroCount; j++) {
				ret.append('0');
			}
			ret.append(hexStr);
		}
		return ret.toString();
	}

}
