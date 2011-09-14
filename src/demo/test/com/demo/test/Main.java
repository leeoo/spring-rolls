package com.demo.test;

import java.util.Collection;

import org.paramecium.search.SearchIndexCreator;

public class Main {
	
	public static void main(String[] args) {
		/*Test test = new Test();
		test.setName("1");
		test.setAddress("hello world,this is a monkey!");
		test.setIdCard("211233198202060616");
		test.setSize(20);
		Validator.validation(test);
		SearchIndexCreator.createIndex(test);*/
		Collection<Object> list = SearchIndexCreator.searchKeyword(Test.class, "hello");
		for(Object o : list){
			System.out.println(o);
		}
	}
	
}
