package com.wisdom.test.thread;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class TestScheduledThread {
	
	@Test
	public void job() {
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
		final Runnable beeper = new Runnable() {
			int count = 0;
			public void run() {
				while(count<100){
					System.out.println(new Date() + " AAAAA " + (++count));
				}
			}
		};
		final Runnable beeper2 = new Runnable() {
			int count = 0;
			public void run() {
				while(count<100){
					System.out.println(new Date() + " BBBBB " + (++count));
				}
			}
		};
		scheduler.schedule(beeper, 1, TimeUnit.SECONDS);
		scheduler.schedule(beeper2, 1, TimeUnit.SECONDS);
		scheduler.shutdown();
		System.out.println("执行完了");
	}
	
}