package com.demo.test;

import java.util.Collection;

import org.paramecium.search.SearchIndexCreator;

public class Main {
	
	public static void main(String[] args) {
		/*Test test = new Test();
		test.setName("2");
		test.setAddress("北京市青年大街!");
		test.setIdCard("211233198202060616");
		test.setSize(20);
		Validator.validation(test);
		SearchIndexCreator.createIndex(test);*/
		Collection<Object> list = SearchIndexCreator.searchKeyword(Test.class, "北京青年");
		for(Object o : list){
			System.out.println(o);
		}
	}
	
}
