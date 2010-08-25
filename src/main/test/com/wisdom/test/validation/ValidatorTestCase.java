package com.wisdom.test.validation;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.wisdom.core.task.domain.Task;
import com.wisdom.core.utils.DateUtils;
import com.wisdom.example.commons.ValidationUtils;

public class ValidatorTestCase {
	@Test
	public void testVld(){
		Task task=new Task();
		task.setTaskName("adsssssssssss");
		task.setInstanceName("1cdsd");
		Date date=DateUtils.getCurrentDateTime();
		date=DateUtils.addDays(date, 1);
		task.setNextRunTime(date);
		List<String> errors=ValidationUtils.validator(task);
		for(String error:errors){
			System.out.println(error);
		}
	}
}
