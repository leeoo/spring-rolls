package com.wisdom.example.service.remoting.impl;

import java.util.Collection;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wisdom.example.service.remoting.TestRemotingService;
/**
 * 功能描述：测试远程调用接口实现类
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-11-4</b>
 * <br>创建时间：<b>上午10:17:26</b>
 * <br>文件结构：<b>spring:com.wisdom.example.service.remoting.impl/TestRemotingServiceImpl.java</b>
 */
public class TestRemotingServiceImpl implements TestRemotingService{
	private Logger logger=LoggerFactory.getLogger(getClass());
	private static Collection<String> testDatas=new LinkedList<String>();

	public void createTestData(String test) {
		logger.info("远程创建测试数据<{}>",test);
		testDatas.add(test);
	}

	public Collection<String> getAllTestData() {
		logger.info("远程获取所有测试数据<{}>",testDatas);
		return testDatas;
	}

	public void removeTestData(String test) {
		logger.info("远程删除测试数据<{}>",test);
		testDatas.remove(test);
	}
	
}
