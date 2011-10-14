package com.demo.test;

import java.util.Collection;

import org.paramecium.search.SearchIndexCreator;

public class Main {
	
	public static void main(String[] args) {
		/*Test test = new Test();
		test.setName("窦志");
		test.setAddress("北京市青年大街!");
		test.setSize(6);
		SearchIndexCreator.createIndex(test);
		test.setName("曹阳");
		test.setAddress("北京市青年大街!");
		test.setSize(5);
		SearchIndexCreator.createIndex(test);
		test.setName("张迪");
		test.setAddress("北京市青年大街!");
		test.setSize(3);
		SearchIndexCreator.createIndex(test);
		test.setName("谢雷");
		test.setAddress("北京市青年大街!");
		test.setSize(4);
		SearchIndexCreator.createIndex(test);*/
		Collection<String> list = SearchIndexCreator.searchKeyword(Test.class, "北京青年");
		for(String o : list){
			System.out.println(o);
		}
	}
	
}
