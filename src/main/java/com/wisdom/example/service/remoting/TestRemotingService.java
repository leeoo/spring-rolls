package com.wisdom.example.service.remoting;

import java.util.Collection;
/**
 * 功能描述：测试远程调用接口
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-11-4</b>
 * <br>创建时间：<b>上午10:17:10</b>
 * <br>文件结构：<b>spring:com.wisdom.example.service.remoting/TestRemotingService.java</b>
 */
public interface TestRemotingService {
	
	public Collection<String> getAllTestData();
	
	public void createTestData(String test);

	public void removeTestData(String test);
	
}
