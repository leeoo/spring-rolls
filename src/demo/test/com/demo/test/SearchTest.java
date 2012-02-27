package com.demo.test;

import java.util.Collection;

import org.paramecium.search.SearchIndexCreator;
/**
 * 功 能 描 述:<br>
 * 测试搜索引擎
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-11-23下午03:01:29
 * <br>项 目 信 息:paramecium:com.demo.test.SearchTest.java
 */
public class SearchTest {
	
	public static void main(String[] args) {
		SearchEntity test = new SearchEntity();
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
		SearchIndexCreator.createIndex(test);
		Collection<String> list = SearchIndexCreator.searchKeyword(SearchEntity.class, "北京青年");
		for(String o : list){
			System.out.println(o);
		}
	}
	
}
