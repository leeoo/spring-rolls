package com.wisdom.example.service.task;

import org.springframework.stereotype.Component;

import com.wisdom.core.task.TaskRunnable;
@Component
public class TestTaskA implements TaskRunnable {

	public void execute()throws Exception {
		for(int i=1;i<23;i++){
			try {
				Thread.sleep(100);
				System.out.println("AAA :"+i);
			} catch (InterruptedException e) {
			}
		}
	}

}
