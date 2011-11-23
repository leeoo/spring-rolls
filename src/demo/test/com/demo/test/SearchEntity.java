package com.demo.test;

import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.search.annotation.Index;
import org.paramecium.search.annotation.KeyWord;
import org.paramecium.search.annotation.SortWord;
import org.paramecium.search.annotation.TextWord;
import org.paramecium.validation.annotation.base.Length;
import org.paramecium.validation.annotation.base.NotNull;
import org.paramecium.validation.annotation.base.Size;
/**
 * 功 能 描 述:<br>
 * 搜索引擎测试用实体
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-11-23下午03:03:56
 * <br>项 目 信 息:paramecium:com.demo.test.SearchEntity.java
 */
@Index("index_test")
public class SearchEntity {
	
	@NotNull(empty = true)
	@ShowLabel("姓名")
	@KeyWord
	private String name;

	@NotNull
	@Length(min=4,max=20)
	@ShowLabel("地址")
	@TextWord
	private String address;
	
	@Size(max=50,min=20)
	@ShowLabel("长度")
	@SortWord
	private int size;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
