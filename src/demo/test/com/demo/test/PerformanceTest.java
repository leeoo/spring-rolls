package com.demo.test;

import java.util.UUID;

import org.paramecium.commons.DateUtils;
import org.paramecium.ioc.ApplicationContext;

import com.demo.service.system.TableInitService;

/**
 * 功 能 描 述:<br>
 * 性能测试
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-11-23下午02:58:35
 * <br>项 目 信 息:paramecium:com.demo.test.PerformanceTest.java
 */
public class PerformanceTest {
	
	public static void main(String[] args) throws Exception {
		TableInitService.createTables();
		for(int i =0;i <4500;i++){
			new TestRunner().start();
		}
	}
	
	static class TestRunner extends Thread {
		
		PerformanceService performanceService =  (PerformanceService) ApplicationContext.getNotSecurityBean("performanceService");
		
		public void save(){
			Performance performance = new Performance();
			while(true){
				performance.setName(UUID.randomUUID().toString());
				performance.setDate(DateUtils.getCurrentDateTime());
				try {
					performanceService.save(performance);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		public void run() {
			save();
		}
		
	}

}
