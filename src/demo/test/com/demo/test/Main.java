package com.demo.test;

import org.paramecium.search.SearchIndexCreator;
import org.paramecium.validation.Validator;

public class Main {
	
	public static void main(String[] args) {
		Test test = new Test();
		test.setName("a");
		test.setAddress("a234234");
		test.setIdCard("211233198202060616");
		test.setSize(20);
		Validator.validation(test);
		SearchIndexCreator.createIndex(test);
	}
	
}
