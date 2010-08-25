package com.wisdom.example.service.task;

import org.springframework.stereotype.Component;

import com.wisdom.core.task.TaskRunnable;
@Component
public class TestTaskC implements TaskRunnable{

	public void execute() throws Exception {
		for(int i=1;i<42;i++){
			Thread.sleep(100);
			System.out.println("CCC :"+i);
		}
	}

}

