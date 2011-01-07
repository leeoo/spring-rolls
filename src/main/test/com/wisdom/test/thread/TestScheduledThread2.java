package com.wisdom.test.thread;

import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

public class TestScheduledThread2 {
	private static AtomicLong al= new AtomicLong();
	@Test
	public void job() {
		while(1==1){
			System.out.println(al.incrementAndGet());
		}
	}
	
}