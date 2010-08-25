package com.wisdom.example.service.task;

import org.springframework.stereotype.Component;

import com.wisdom.core.task.TaskRunnable;
@Component
public class TestTaskB implements TaskRunnable {

	public void execute()throws Exception {
		for(int i=1;i<54;i++){
			if(i==23){
				int a=0;
				a=a/a;
			}
			try {
				Thread.sleep(100);
				System.out.println("BBB :"+i);
			} catch (InterruptedException e) {
			}
		}
	}

}
