package com.wisdom.test.remoting;

import java.util.Collection;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import com.wisdom.core.test.SpringTestCase;
import com.wisdom.example.service.remoting.TestRemotingService;

public class RemotingTestCase extends SpringTestCase {
	@Resource
	private TestRemotingService testRemotingService;

	@Test
	public void testCreate() {
		testRemotingService.createTestData("aaa");

		testRemotingService.createTestData("bbb");
		testRemotingService.createTestData("ccc");
		testRemotingService.createTestData("ddd");
		testRemotingService.createTestData("eee");
	}

	@Test
	public void testRemove() {
		testRemotingService.removeTestData("aaa");
		testRemotingService.removeTestData("ccc");
		testRemotingService.removeTestData("eee");
	}

	@Test
	public void testGetAll() {
		Collection<String> testDatas = testRemotingService.getAllTestData();
		Assert.assertNotNull(testDatas);
		Assert.assertTrue(testDatas.size() == 2);
	}

	public TestRemotingService getTestRemotingService() {
		return testRemotingService;
	}

	public void setTestRemotingService(TestRemotingService testRemotingService) {
		this.testRemotingService = testRemotingService;
	}

}
